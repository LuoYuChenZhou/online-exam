package com.lycz.controller.user;

import com.lycz.configAndDesign.FixPageInfo;
import com.lycz.configAndDesign.annotation.Privilege;
import com.lycz.service.user.ExamineeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author lizhenqing
 * @version 1.0
 * @data 2018/3/2 10:42
 */
@Controller
@RequestMapping("Examinee")
@Api(value = "Examinee", description = "考生相关api")
public class ExamineeController {
    @Resource
    private ExamineeService examineeService;

    @RequestMapping(value = "/getEeListByNameNoClass", method = RequestMethod.GET)
    @Privilege(methodName = "根据考生姓名、考生号、所属班级查询学生列表", privilegeLevel = Privilege.ER_TYPE)
    @ResponseBody
    @ApiOperation(value = "根据考生姓名、考生号、所属班级查询学生列表", notes = "" +
            "入参说明<br/>" +
            "searchClass：班级id（可选）<br/>" +
            "examineeName：搜索的考生名称（可选）<br/>" +
            "examineeNum：搜索的考生号（可选）<br/>" +
            "page：当前页<br/>" +
            "limit：每页条数<br/>" +
            "出参说明<br/>" +
            "\n" +
            "{\n" +
            "\n" +
            "    \"code\":状态值，0为正常,\n" +
            "    \"count\":总条数,\n" +
            "    \"data\":[\n" +
            "        {\n" +
            "            \"eeId\":\"考生id\",\n" +
            "            \"eeNo\":\"考生号\",\n" +
            "            \"realName\":\"真实姓名\",\n" +
            "            \"sex\":\"性别（0-男，1-女）\",\n" +
            "            \"phone\":\"手机号\",\n" +
            "            \"email\":\"电子邮箱\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"limit\":每页条数,\n" +
            "    \"logMsg\":\"日志信息（前端无意义）\",\n" +
            "    \"msg\":\"提示信息\",\n" +
            "    \"page\":当前页\n" +
            "\n" +
            "}\n")
    public JSONObject getEeListByNameNoClass(@RequestParam(value = "searchClass", required = false) String searchClass
            , @RequestParam(value = "examineeName", required = false) String examineeName
            , @RequestParam(value = "examineeNum", required = false) String examineeNum
            , @RequestParam("page") Integer page
            , @RequestParam("limit") Integer limit
            , @RequestParam("token") String token) {
        FixPageInfo<Map<String, Object>> logInfo = examineeService.getEeListByNameNoClass(searchClass, examineeName, examineeNum, page, limit);
        if (logInfo == null) {
            logInfo = new FixPageInfo<>();
            logInfo.setCode(204);
            logInfo.setMsg("没有数据");
        } else {
            logInfo.setCode(0);
            logInfo.setMsg("查询成功");
        }
        return JSONObject.fromObject(logInfo);
    }
}
