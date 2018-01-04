package com.lycz.controller.user;

import com.lycz.controller.common.CommonMethods;
import com.lycz.controller.common.CommonResult;
import com.lycz.controller.common.ToolUtil;
import com.lycz.controller.common.annotation.NoSaveLog;
import com.lycz.controller.common.annotation.Privilege;
import com.lycz.model.Examinee;
import com.lycz.model.Examiner;
import com.lycz.model.SysLog;
import com.lycz.service.base.SysLogService;
import com.lycz.service.base.TokenService;
import com.lycz.service.user.ExamineeService;
import com.lycz.service.user.ExaminerService;
import net.sf.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("Login")
public class LoginController {

    private Logger log = LogManager.getLogger();

    @Autowired
    private ExamineeService examineeService;
    @Autowired
    private ExaminerService examinerService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private SysLogService sysLogService;

    /**
     * 登录
     *
     * @param type      登录类型（1-考官登录，2-考生登录，99-管理员登录）
     * @param loginName 用户名
     * @param loginPass 密码
     */
    @NoSaveLog
    @RequestMapping(value = "/exeLogin", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject exeLogin(@RequestParam("type") String type, @RequestParam("loginName") String loginName,
                               @RequestParam("loginPass") String loginPass) {
        CommonResult<JSONObject> result = new CommonResult<>();
        result.setData(JSONObject.fromObject("{}"));
        result.setStatus(400);

        Object tokenObj = null;

        switch (type) {
            case "1":
                Examiner examiner = examinerService.erLogin(loginName, loginPass);
                if (examiner == null) {
                    saveLoginLog(loginName, loginPass, "用户名或密码错误");
                    result.setMsg("用户名或密码错误");
                    return JSONObject.fromObject(result);
                }
                tokenObj = examiner;
                break;
            case "2":
                Examinee examinee = examineeService.eeLogin(loginName, loginPass);
                if (examinee == null) {
                    saveLoginLog(loginName, loginPass, "用户名或密码错误");
                    result.setMsg("用户名或密码错误");
                    return JSONObject.fromObject(result);
                }
                tokenObj = examinee;
                break;
            case "99":
                String serviceName = CommonMethods.getProperty("config/sysLg.properties", "sys_lg_na");
                String servicePass = CommonMethods.getProperty("config/sysLg.properties", "sys_lg_pw");
                if (serviceName.equals(loginName) && servicePass.equals(loginPass)) {
                    tokenObj = "sysLog";
                }
                break;
            default:
                result.setStatus(401);
                result.setMsg("非法登录");
                saveLoginLog(loginName, loginPass, "非法登录");
                return JSONObject.fromObject(result);
        }

        String token = tokenService.createToken(tokenObj);
        if (ToolUtil.isEmpty(token)) {
            saveLoginLog(loginName, loginPass, "登录失败");
            result.setMsg("登录失败");
            return JSONObject.fromObject(result);
        }

        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        result.setData(JSONObject.fromObject(tokenMap));
        result.setStatus(200);
        result.setMsg("登录成功");
        saveLoginLog(loginName, "不显示", "登录成功");
        return JSONObject.fromObject(result);
    }

    @NoSaveLog
    @RequestMapping(value = "/eeRegister", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject exeRegister(String registerType) {
        CommonResult<JSONObject> result = new CommonResult<>();
        result.setData(JSONObject.fromObject("{}"));
        result.setStatus(400);


        return JSONObject.fromObject(result);
    }

    private void saveLoginLog(String loginName, String loginPass, String desc) {
        SysLog logsEntity = new SysLog(UUID.randomUUID().toString(), desc, "0", "", new Date(),
                "用户名：" + loginName + "密码：" + loginPass, "Login/exeLogin");
        try {
            if (sysLogService.save(logsEntity) < 1) {
                log.error("登录日志保存失败:{}：{}", desc, logsEntity.getLogDescription());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("日志保存失败：{}:,用户名：{}密码：{}", "Login/exeLogin", loginName, loginPass);
        }
    }
}
