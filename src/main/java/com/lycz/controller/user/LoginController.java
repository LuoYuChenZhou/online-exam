package com.lycz.controller.user;

import com.lycz.configAndDesign.CommonResult;
import com.lycz.configAndDesign.ToolUtil;
import com.lycz.configAndDesign.annotation.NoSaveLog;
import com.lycz.configAndDesign.annotation.Privilege;
import com.lycz.model.Examinee;
import com.lycz.model.Examiner;
import com.lycz.model.SysLog;
import com.lycz.service.base.SysLogService;
import com.lycz.service.base.SysMsgService;
import com.lycz.service.base.TokenService;
import com.lycz.service.user.ErEeService;
import com.lycz.service.user.ExamineeService;
import com.lycz.service.user.ExaminerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("Login")
@Api(value = "Login", description = "登录相关api")
public class LoginController {

    private Logger log = LogManager.getLogger();

    @Resource
    private ExamineeService examineeService;
    @Resource
    private ExaminerService examinerService;
    @Resource
    private TokenService tokenService;
    @Resource
    private SysLogService sysLogService;
    @Resource
    private SysMsgService sysMsgService;
    @Resource
    private ErEeService erEeService;

    /**
     * 登录
     */
    @NoSaveLog
    @RequestMapping(value = "/exeLogin", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "登录", notes = "" +
            "入参说明：<br/>" +
            "type:登录类型（1-考官登录，2-考生登录，99-管理员登录）<br/>" +
            "loginName:用户名<br/>" +
            "loginPass:密码<br/>" +
            "出参说明：<br/>" +
            "")
    public JSONObject exeLogin(@RequestParam("type") String type,
                               @RequestParam("loginName") String loginName,
                               @RequestParam("loginPass") String loginPass) {
        CommonResult<JSONObject> result = new CommonResult<>();
        result.setData(JSONObject.fromObject("{}"));
        result.setStatus(400);

        Object tokenObj = null;

        switch (type) {
            case "1":
                Examiner examiner = examinerService.erLogin(loginName, loginPass);
                if (examiner == null) {
                    saveLoginLog(loginName, loginPass, type, "用户名或密码错误");
                    result.setMsg("用户名或密码错误");
                    return JSONObject.fromObject(result);
                }
                tokenObj = examiner;
                break;
            case "2":
                Examinee examinee = examineeService.eeLogin(loginName, loginPass);
                if (examinee == null) {
                    saveLoginLog(loginName, loginPass, type, "用户名或密码错误");
                    result.setMsg("用户名或密码错误");
                    return JSONObject.fromObject(result);
                }
                tokenObj = examinee;
                break;
            case "99":
                String serviceName = ToolUtil.getProperty("config/sysLg.properties", "sys_lg_na");
                String servicePass = ToolUtil.getProperty("config/sysLg.properties", "sys_lg_pw");
                if (Objects.equals(serviceName, loginName) && Objects.equals(servicePass, loginPass)) {
                    tokenObj = "sysLog";
                }
                break;
            default:
                result.setStatus(401);
                result.setMsg("非法登录");
                saveLoginLog(loginName, loginPass, type, "非法登录");
                return JSONObject.fromObject(result);
        }

        String token = tokenService.createToken(tokenObj);
        if (ToolUtil.isEmpty(token)) {
            saveLoginLog(loginName, loginPass, type, "登录失败");
            result.setMsg("登录失败");
            return JSONObject.fromObject(result);
        }
        saveLoginLog(loginName, "不显示", type, "登录成功");

        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("token", token);

        // 考生登录获取所属考官列表
        if (Objects.equals("2", type)) {
            List<String> erIdList = erEeService.getErIdListByEeId(((Examinee) tokenObj).getId());
            if (ToolUtil.isNotEmpty(erIdList)) {
                tokenMap.put("erIdList", erIdList);
            }
            tokenMap.put("userId", ((Examinee) tokenObj).getId());
        }

        result.setData(JSONObject.fromObject(tokenMap));
        result.setStatus(200);
        result.setMsg("登录成功");
        return JSONObject.fromObject(result);
    }

    /**
     * 考生注册
     */
    @NoSaveLog
    @RequestMapping(value = "/eeRegister", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "考生注册", notes = "" +
            "入参说明：<br/>" +
            "出参说明：<br/>" +
            "")
    public JSONObject eeRegister(@Valid Examinee examinee, BindingResult bindingResult, @RequestParam("password") String password) throws Exception {
        CommonResult<JSONObject> result = new CommonResult<>();
        result.setData(JSONObject.fromObject("{}"));
        result.setStatus(400);

        if (bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            result.setMsg(allErrors.get(allErrors.size() - 1).getDefaultMessage());
            return JSONObject.fromObject(result);
        }

        examinee.setId(UUID.randomUUID().toString());
        examinee.setRegTime(new Date());
        examinee.setStatus("1");
        examinee.setLoginPwd(password);

        if (examineeService.userNameIsExist(examinee.getLoginName())) {
            result.setMsg("用户名被占用");
            result.setStatus(301);
            return JSONObject.fromObject(result);
        }

        if (examineeService.insertSelective(examinee) < 1) {
            result.setMsg("注册失败");
            log.error("考生注册发生错误");
        } else {
            sysMsgService.addMsg(examinee.getId(), examinee.getId(), "注册成功", "MT_RE");
            Examinee examinee1 = examineeService.eeLogin(examinee.getLoginName(), examinee.getLoginPwd());
            String token = tokenService.createToken(examinee1);
            Map<String, String> tokenMap = new HashMap<>();
            tokenMap.put("token", token);

            result.setData(JSONObject.fromObject(tokenMap));
            result.setMsg("注册成功");
            result.setStatus(201);

        }

        return JSONObject.fromObject(result);
    }

    /**
     * 考官注册
     */
    @NoSaveLog
    @RequestMapping(value = "/erRegister", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "考官注册", notes = "" +
            "入参说明：<br/>" +
            "字段太多了， 不想写" +
            "出参说明：<br/>" +
            "")
    public JSONObject erRegister(@Valid Examiner examiner, BindingResult bindingResult, @RequestParam("password") String password) throws Exception {
        CommonResult<JSONObject> result = new CommonResult<>();
        result.setData(JSONObject.fromObject("{}"));
        result.setStatus(400);

        if (bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            result.setMsg(allErrors.get(allErrors.size() - 1).getDefaultMessage());
            return JSONObject.fromObject(result);
        }

        examiner.setId(UUID.randomUUID().toString());
        examiner.setRegTime(new Date());
        examiner.setStatus("1");
        examiner.setLoginPwd(password);

        if (examinerService.userNameIsExist(examiner.getLoginName())) {
            result.setMsg("用户名被占用");
            result.setStatus(301);
            return JSONObject.fromObject(result);
        }

        if (examinerService.insertSelective(examiner) < 1) {
            result.setMsg("注册失败");
            log.error("考官注册发生错误");
        } else {
            sysMsgService.addMsg(examiner.getId(), examiner.getId(), "注册成功", "MT_RE");
            Examiner examiner1 = examinerService.erLogin(examiner.getLoginName(), examiner.getLoginPwd());
            String token = tokenService.createToken(examiner1);
            Map<String, String> tokenMap = new HashMap<>();
            tokenMap.put("token", token);

            result.setData(JSONObject.fromObject(tokenMap));
            result.setMsg("注册成功");
            result.setStatus(201);
        }

        return JSONObject.fromObject(result);
    }

    /**
     * 判断用户名是否被占用
     *
     * @param userName 用户名
     * @param regType  注册类型（“1”-考官，“2”-考生）
     * @return data中的 isExist（“true”-占用，“false”-未占用）
     */
    @RequestMapping(value = "/userNameIsExist", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "判断用户名是否被占用", notes = "" +
            "入参说明：<br/>" +
            "userName：用户名<br/>" +
            "regType：注册类型（“1”-考官，“2”-考生）<br/>" +
            "出参说明：<br/>" +
            "data中的 isExist（“true”-占用，“false”-未占用）")
    public JSONObject userNameIsExist(@RequestParam("userName") String userName, @RequestParam("regType") String regType) {
        CommonResult<JSONObject> result = new CommonResult<>();
        result.setData(JSONObject.fromObject("{}"));
        result.setStatus(400);

        boolean isExist = false;

        switch (regType) {
            case "1":
                isExist = examinerService.userNameIsExist(userName);
                break;
            case "2":
                isExist = examineeService.userNameIsExist(userName);
                break;
            default:
                result.setMsg("注册类型错误");
                break;
        }

        result.setStatus(200);
        result.setData(JSONObject.fromObject("{\"isExist\":\"" + isExist + "\"}"));
        return JSONObject.fromObject(result);
    }

    @RequestMapping(value = "/getUserInfoByType", method = RequestMethod.GET)
    @Privilege(methodName = "根据输入类型获取用户信息")
    @ResponseBody
    @ApiOperation(value = "根据输入类型获取用户信息", notes = "" +
            "入参说明：<br/>" +
            "searchType：（type-用户类型，name-用户姓名）<br/>" +
            "出参说明：<br/>" +
            "\n" +
            "{\n" +
            "\n" +
            "    \"data\":{\n" +
            "        \"returnData\":\"要查询的信息\"\n" +
            "    },\n" +
            "    \"logMsg\":\"\",\n" +
            "    \"msg\":\"\",\n" +
            "    \"status\":200\n" +
            "\n" +
            "}\n")
    public JSONObject getUserInfoByType(@RequestParam("searchType") String searchType,
                                        @RequestParam("token") String token) {
        CommonResult<JSONObject> result = new CommonResult<>();
        result.setData(JSONObject.fromObject("{}"));
        result.setStatus(400);

        Map<String, Object> jm = tokenService.getTokenMap(token);

        String realType;
        switch (searchType) {
            case "type":
                realType = "userType";
                break;
            case "name":
                realType = "realName";
                break;
            default:
                result.setMsg("查询类型错误");
                return JSONObject.fromObject(result);
        }

        String returnData = (String) jm.get(realType);

        //系统用户类型转换
        if (returnData.equals(ToolUtil.getProperty("config/sysLg.properties", "sys_user_type"))) {
            returnData = "system_type";
        }

        result.setData(JSONObject.fromObject("{\"returnData\":\"" + returnData + "\"}"));
        result.setStatus(200);

        return JSONObject.fromObject(result);
    }

    @RequestMapping(value = "/exeLoginOut", method = RequestMethod.POST)
    @Privilege(methodName = "注销", privilegeLevel = Privilege.NO_LOGIN)
    @ResponseBody
    @ApiOperation(value = "注销")
    public JSONObject exeLoginOut(@RequestParam("token") String token) {
        CommonResult<JSONObject> result = new CommonResult<>();
        result.setData(JSONObject.fromObject("{}"));
        result.setStatus(200);

        //从redis里面移除token
        tokenService.destroyToken(token);

        return JSONObject.fromObject(result);
    }

    /**
     * 保存登录日志
     */
    private void saveLoginLog(String loginName, String loginPass, String loginType, String desc) {
        String strType;
        switch (loginType) {
            case "1":
                strType = "    登录类型：考官登陆";
                break;
            case "2":
                strType = "    登录类型：考生登录";
                break;
            case "99":
                strType = "    登录类型：系统管理员登录";
                break;
            default:
                strType = "    登陆类型：错误类型";
        }

        SysLog logsEntity = new SysLog(UUID.randomUUID().toString(), desc, "0", "", new Date(),
                "用户名：" + loginName + strType + "<br/>" + "密码：" + loginPass, "Login/exeLogin");
        try {
            if (sysLogService.save(logsEntity) < 1) {
                log.error("登录日志保存失败:{}：{}", desc, logsEntity.getLogDescription());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("日志保存失败：{}:,用户名：{}密码：{}{}", "Login/exeLogin", loginName, loginPass, strType);
        }
    }
}
