package com.lycz.controller.papers;

import com.lycz.configAndDesign.CommonResult;
import com.lycz.configAndDesign.annotation.Privilege;
import com.lycz.service.paper.PapersService;
import com.lycz.service.user.ExaminerService;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("Papers")
public class PapersController {
    @Resource
    private PapersService papersService;

    @Privilege(privilegeLevel = Privilege.ER_TYPE)
    @RequestMapping(value = "/searchExaminationName", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "根据试卷名搜索", notes = "" +
            "入参说明：<br/>" +
            "userName：用户名<br/>" +
            "regType：注册类型（“1”-考官，“2”-考生）<br/>" +
            "出参说明：<br/>" +
            "data中的 isExist（“true”-占用，“false”-未占用）")
    public JSONObject searchExaminationName(@RequestParam(value = "papersName", required = false)String papersName,
                                            @RequestParam("teachersId") String teachersId,
                                            @RequestParam("token") String token){
        CommonResult<JSONArray> result = new CommonResult<>();
        List<Map<String, Object>> list =  papersService.searchExaminationName(papersName,teachersId);
        if(list.size() > 0){
            result.setMsg("搜索成功");
            result.setStatus(200);
            result.setData(JSONArray.fromObject(list));
        }else {
            result.setMsg("数据为空");
            result.setStatus(400);
            result.setData(JSONArray.fromObject(""));
        }
        return JSONObject.fromObject(result);
    }
}
