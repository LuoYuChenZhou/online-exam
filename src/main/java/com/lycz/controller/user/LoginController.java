package com.lycz.controller.user;

import com.lycz.controller.common.CommonMethods;
import com.lycz.controller.common.CommonResult;
import com.lycz.model.Examinee;
import com.lycz.model.Examiner;
import com.lycz.service.user.ExamineeService;
import com.lycz.service.user.ExaminerService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("Login")
public class LoginController {

    @Autowired
    private ExamineeService examineeService;
    @Autowired
    private ExaminerService examinerService;

    /**
     * 登录
     *
     * @param type      登录类型（1-考官登录，2-考生登录，99-管理员登录）
     * @param loginName 用户名
     * @param loginPass 密码
     */
    @RequestMapping(value = "/exeLogin", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject exeLogin(@RequestParam("type") String type, @RequestParam("loginName") String loginName,
                               @RequestParam("loginPass") String loginPass) {
        CommonResult<String> result = new CommonResult<>();
        result.setData("");
        result.setStatus(400);

        switch (type) {
            case "1":
                Examiner examiner = examinerService.erLogin(loginName, loginPass);
                if (examiner == null) {
                    result.setMsg("用户名或密码错误");
                    return JSONObject.fromObject(result);
                }
                break;
            case "2":
                Examinee examinee = examineeService.eeLogin(loginName, loginPass);
                if (examinee == null) {
                    result.setMsg("用户名或密码错误");
                    return JSONObject.fromObject(result);
                }
                break;
            case "99":
                String serviceName = CommonMethods.getProperty("config/sysLg.properties", "sys_lg_na");
                String servicePass = CommonMethods.getProperty("config/sysLg.properties", "sys_lg_pw");
                if (serviceName.equals(loginName) && servicePass.equals(loginPass)) {

                }
                break;
            default:
                result.setStatus(401);
                result.setMsg("非法登录");
                return JSONObject.fromObject(result);
        }

        result.setStatus(200);
        result.setMsg("登录成功");
        return JSONObject.fromObject(result);
    }
}
