package com.lycz.controller.grade;

import com.lycz.configAndDesign.CommonResult;
import com.lycz.configAndDesign.FixPageInfo;
import com.lycz.configAndDesign.ToolUtil;
import com.lycz.configAndDesign.annotation.Privilege;
import com.lycz.model.Grade;
import com.lycz.service.base.CommonService;
import com.lycz.service.base.TokenService;
import com.lycz.service.grade.GradeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author lizhenqing
 * @version 1.0
 * @data 2018/2/26 9:00
 */
@Controller
@RequestMapping(value = "Grade")
@Api(description = "班级相关api")
public class GradeController {

    @Resource
    private GradeService gradeService;
    @Resource
    private TokenService tokenService;
    @Resource
    private CommonService commonService;

    @RequestMapping(value = "/setGradeSortNo", method = RequestMethod.POST)
    @Privilege(methodName = "设置班级排序号")
    @ResponseBody
    @ApiOperation(value = "设置班级排序号", notes = "" +
            "入参说明：<br/>" +
            "gradeId:班级id<br/>" +
            "sortNo:要设置的排序号<br/>" +
            "出参说明：<br/>" +
            "201")
    public JSONObject setGradeSortNo(@RequestParam("gradeId") String gradeId,
                                     @RequestParam("sortNo") Integer sortNo,
                                     @RequestParam("token") String token) {
        CommonResult<JSONObject> result = new CommonResult<>();
        result.setData(JSONObject.fromObject("{}"));

        //调用的是存储过程，编写时没有设置返回值，默认视为排序完成，排序失败会报错
        commonService.setSortNoByLineNum("grade", gradeId, sortNo, 1, "er_id='" + tokenService.getUserId(token) + "' AND status != '4' ");

        result.setMsg("排序完成");
        result.setStatus(201);
        return JSONObject.fromObject(result);
    }

    /**
     * 添加班级
     */
    @RequestMapping(value = "/addGrade", method = RequestMethod.POST)
    @Privilege(methodName = "添加班级", privilegeLevel = Privilege.ER_TYPE)
    @ResponseBody
    @ApiOperation(value = "添加班级", notes = "" +
            "入参说明：<br/>" +
            "gradeName:班级名称（必填）<br/>" +
            "sortNo:排序号(可选，默认为1)<br/>" +
            "出参说明：<br/>" +
            "201")
    public JSONObject addGrade(@Valid Grade grade, BindingResult bindingResult, @RequestParam("token") String token) {
        CommonResult<JSONObject> result = new CommonResult<>();
        result.setData(JSONObject.fromObject("{}"));
        result.setStatus(400);

        if (bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            result.setMsg(allErrors.get(allErrors.size() - 1).getDefaultMessage());
            return JSONObject.fromObject(result);
        }

        Date date = new Date();
        grade.setId(UUID.randomUUID().toString());
        grade.setErId(tokenService.getUserId(token));
        grade.setCreateTime(date);
        grade.setModifyTime(date);
        grade.setStatus("1");
        if (ToolUtil.isEmpty(grade.getSortNo())) {
            grade.setSortNo(1);
        }

        if (gradeService.addGrade(grade)) {
            result.setMsg("新增成功");
            result.setStatus(201);
        } else {
            result.setMsg("新增失败");
        }

        return JSONObject.fromObject(result);
    }

    /**
     * @return {
     * "code":状态,
     * "count":总条数,
     * "data":[
     * {
     * "id":"班级id",
     * "gradeName":"班级名称",
     * "createTime":创建时间,
     * "studentNum":班级当前人数
     * "sortNo":排序号
     * "status":当前状态（0-禁用，1-正常）
     * }
     * ],
     * "limit":每页条数,
     * "msg":提示信息,
     * "page":当前页
     * }
     */
    @RequestMapping(value = "/getGradeListByNameUser", method = RequestMethod.GET)
    @Privilege(methodName = "根据班级名称和当前用户搜索班级列表", privilegeLevel = Privilege.ER_TYPE)
    @ResponseBody
    @ApiOperation(value = "根据班级名称和当前用户搜索班级列表", notes = "" +
            "入参说明：<br/>" +
            "searchGradeName:班级名称<br/>" +
            "page:当前页<br/>" +
            "limit:每页条数<br/>" +
            "出参说明：<br/>" +
            "\n" +
            "{\n" +
            "\n" +
            "    \"code\":0,\n" +
            "    \"count\":5,\n" +
            "    \"data\":[\n" +
            "        {\n" +
            "            \"id\": \"班级id\",\n" +
            "            \"gradeName\": \"班级名称\",\n" +
            "            \"createTime\": 创建时间,\n" +
            "            \"studentNum\": 班级当前人数,\n" +
            "            \"sortNo\": 排序号,\n" +
            "            \"status\": \"当前状态（0-禁用，1-正常）\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"limit\":1,\n" +
            "    \"logMsg\":\"\",\n" +
            "    \"msg\":\"查询成功\",\n" +
            "    \"page\":1\n" +
            "\n" +
            "}\n" +
            "\n")
    public JSONObject getGradeListByNameUser(@RequestParam(value = "searchGradeName", required = false) String searchGradeName,
                                             @RequestParam("page") Integer page,
                                             @RequestParam("limit") Integer limit,
                                             @RequestParam("token") String token) {

        String userId = tokenService.getUserId(token);

        FixPageInfo<Map<String, Object>> logInfo = gradeService.getGradeListByNameUser(searchGradeName, userId, page, limit);
        if (logInfo == null) {
            logInfo = new FixPageInfo<>();
            logInfo.setMsg("查询结束，但没有数据");
            logInfo.setCode(204);
        } else {
            logInfo.setMsg("查询成功");
            logInfo.setCode(0);
        }

        return JSONObject.fromObject(logInfo);
    }

    @RequestMapping(value = "changeGradeStatus", method = RequestMethod.POST)
    @Privilege(methodName = "修改班级的状态", privilegeLevel = Privilege.ER_TYPE)
    @ResponseBody
    @ApiOperation(value = "修改班级的状态", notes = "" +
            "入参说明：<br/>" +
            "id:班级id<br/>" +
            "status:目标状态，传入数字（0-禁用，1-启用，4-删除）<br/>" +
            "出参说明：<br/>" +
            "201")
    public JSONObject changeGradeStatus(@RequestParam("id") String id,
                                        @RequestParam("status") String status,
                                        @RequestParam("token") String token) {

        CommonResult<Object> result = new CommonResult<>();

        Grade grade = new Grade();
        grade.setId(id);
        grade.setStatus(status);

        if (gradeService.updateByPrimaryKeySelective(grade) > 0) {
            result.setMsg("修改成功");
            result.setStatus(201);
            result.setData("");
        } else {
            result.setMsg("修改失败");
            result.setStatus(400);
            result.setData("");
        }

        return JSONObject.fromObject(result);
    }

}

