package com.lycz.controller.papers;

import com.lycz.configAndDesign.CommonResult;
import com.lycz.configAndDesign.FixPageInfo;
import com.lycz.configAndDesign.ToolUtil;
import com.lycz.configAndDesign.annotation.Privilege;
import com.lycz.model.Papers;
import com.lycz.service.base.TokenService;
import com.lycz.service.paper.PapersService;
import com.lycz.service.user.ExaminerService;
import io.swagger.annotations.Api;
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
@Api(description = "试卷相关Api")
public class PapersController {
    @Resource
    private PapersService papersService;
    @Resource
    private TokenService tokenService;

    @Privilege(privilegeLevel = Privilege.ER_TYPE)
    @RequestMapping(value = "/selectPapersByName", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "根据试卷名搜索", notes = "" +
            "入参说明：<br/>" +
            "userName：用户名<br/>" +
            "出参说明：<br/>" +
            "\n" +
            "{\n" +
            "\n" +
            "    \"code\":0,\n" +
            "    \"count\":总计条数,\n" +
            "    \"data\":[\n" +
            "        {\n" +
            "            \"status\":\"（1-编写，2-已发布，4-删除）\",\n" +
            "            \"examNum\":本试卷作答人数,\n" +
            "            \"papersName\":\"试卷名称\",\n" +
            "            \"fullScore\":试卷总分,\n" +
            "            \"examTime\":考试时间（分钟）\n" +
            "        }\n" +
            "    ],\n" +
            "    \"limit\":每页条数,\n" +
            "    \"msg\":\"查询成功\",\n" +
            "    \"page\":当前页\n" +
            "\n" +
            "}\n")
    public JSONObject selectPapersByName(@RequestParam(value = "searchPaperName", required = false) String searchPaperName,
                                         @RequestParam("page") Integer page,
                                         @RequestParam("limit") Integer limit,
                                         @RequestParam("token") String token) {
        FixPageInfo<Map<String, Object>> pageInfo = papersService.selectPapersByName(searchPaperName, tokenService.getUserId(token), page, limit);
        if (ToolUtil.isEmpty(pageInfo)) {
            pageInfo = new FixPageInfo<>();
            pageInfo.setCode(204);
            pageInfo.setMsg("查询结束，但没有结果");
        } else {
            pageInfo.setMsg("查询成功");
            pageInfo.setCode(0);
        }
        return JSONObject.fromObject(pageInfo);
    }

    @RequestMapping(value = "/changePaperName", method = RequestMethod.POST)
    @Privilege(methodName = "修改试卷名称")
    @ResponseBody
    @ApiOperation(value = "修改试卷名称", notes = "" +
            "入参说明:<br/>" +
            "paperId:修改试卷的id<br/>" +
            "newPaperName:新试卷名称<br/>" +
            "出参说明:<br/>" +
            "201")
    public JSONObject changePaperName(@RequestParam("paperId") String paperId,
                                      @RequestParam("newPaperName") String newPaperName,
                                      @RequestParam("token") String token) {
        CommonResult<JSONObject> result = new CommonResult<>();
        result.setData(JSONObject.fromObject("{}"));
        result.setStatus(400);

        Papers paper = new Papers();
        paper.setId(paperId);
        paper.setPapersName(newPaperName);

        if (papersService.updateByPrimaryKeySelective(paper) > 0) {
            result.setMsg("修改成功");
            result.setStatus(201);
        } else {
            result.setMsg("修改失败");
        }

        return JSONObject.fromObject(result);
    }

    @RequestMapping(value = "/changePaperStatus", method = RequestMethod.POST)
    @Privilege(methodName = "修改试卷名称")
    @ResponseBody
    @ApiOperation(value = "修改试卷名称", notes = "" +
            "入参说明:<br/>" +
            "paperId:修改试卷的id<br/>" +
            "targetStatus:试卷目标状态<br/>" +
            "出参说明:<br/>" +
            "201")
    public JSONObject changePaperStatus(@RequestParam("paperId") String paperId,
                                        @RequestParam("targetStatus") String targetStatus,
                                        @RequestParam("token") String token) {
        CommonResult<JSONObject> result = new CommonResult<>();
        result.setData(JSONObject.fromObject("{}"));
        result.setStatus(400);

        Papers paper = new Papers();
        paper.setId(paperId);
        paper.setStatus(targetStatus);

        if (papersService.updateByPrimaryKeySelective(paper) > 0) {
            result.setMsg("操作成功");
            result.setStatus(201);
        } else {
            result.setMsg("操作失败");
        }

        return JSONObject.fromObject(result);
    }
}
