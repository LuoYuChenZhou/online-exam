package com.lycz.controller.grade;

import com.lycz.configAndDesign.CommonResult;
import com.lycz.configAndDesign.annotation.Privilege;
import com.lycz.service.grade.EeGradeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;


/**
 * @author lizhenqing
 * @version 1.0
 * @data 2018/3/12 9:41
 */
@Controller
@RequestMapping(value = "EeGrade")
@Api(value = "EeGrade", description = "考生班级相关api")
public class EeGradeController {
    @Resource
    private EeGradeService eeGradeService;

    @RequestMapping(value = "/insertEeToGrade", method = RequestMethod.POST)
    @Privilege(methodName = "将考生放入班级", privilegeLevel = Privilege.ER_TYPE)
    @ResponseBody
    @ApiOperation(value = "将考生放入班级", notes = "" +
            "入参说明<br/>" +
            "eeId：考生id<br/>" +
            "gradeId：班级id<br/>" +
            "出参说明<br/>" +
            "201")
    public JSONObject insertEeToGrade(@RequestParam("eeId") String eeId,
                                      @RequestParam("gradeId") String gradeId,
                                      @RequestParam("token") String token) {
        CommonResult<JSONObject> result = new CommonResult<>();
        result.setData(JSONObject.fromObject("{}"));

        if (eeGradeService.insertEeToGrade(eeId, gradeId) > 0) {
            result.setStatus(201);
            result.setMsg("修改成功");
        } else {
            result.setStatus(400);
            result.setMsg("修改失败");
        }

        return JSONObject.fromObject(result);
    }
}
