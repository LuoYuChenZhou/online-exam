package com.lycz.controller.user;

import com.lycz.controller.common.CommonResult;
import com.lycz.controller.common.annotation.Privilege;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lizhenqing
 * @version 1.0
 * @data 2018/3/2 10:42
 */
@Controller
@RequestMapping("Examinee")
public class ExamineeController {

    @RequestMapping(value = "/getEeListByNameNoClass", method = RequestMethod.GET)
    @Privilege(methodName = "根据考生姓名、考生号、所属班级查询学生列表")
    @ResponseBody
    public JSONObject getEeListByNameNoClass(@RequestParam("token") String token) {
        CommonResult<JSONObject> result = new CommonResult<>();
        result.setData(JSONObject.fromObject("{}"));
        result.setStatus(400);


        return JSONObject.fromObject(result);
    }
}
