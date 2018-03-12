package com.lycz.controller.grade;

import com.lycz.controller.common.CommonResult;
import com.lycz.controller.common.FixPageInfo;
import com.lycz.controller.common.annotation.Privilege;
import com.lycz.model.Grade;
import com.lycz.service.base.TokenService;
import com.lycz.service.grade.GradeService;
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
 * @data 2018/3/12 9:41
 */
@Controller
@RequestMapping(value = "EeGrade")
public class EeGradeController {

    @RequestMapping(value = "/change", method = RequestMethod.POST)
    @Privilege(methodName = "方法名称")
    @ResponseBody
    public JSONObject change(@RequestParam("token") String token){
        CommonResult<JSONObject> result = new CommonResult<>();
        result.setData(JSONObject.fromObject("{}"));
        result.setStatus(400);


        return JSONObject.fromObject(result);
    }
}
