package com.lycz.controller.grade;

import com.lycz.configAndDesign.CommonResult;
import com.lycz.configAndDesign.FixPageInfo;
import com.lycz.configAndDesign.ToolUtil;
import com.lycz.configAndDesign.annotation.Privilege;
import com.lycz.model.EeGrade;
import com.lycz.model.Grade;
import com.lycz.service.base.CommonService;
import com.lycz.service.base.TokenService;
import com.lycz.service.grade.EeGradeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.UUID;


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
    @Resource
    private CommonService commonService;
    @Resource
    private TokenService tokenService;

    @RequestMapping(value = "/getNoEeGradeList", method = RequestMethod.GET)
    @Privilege(methodName = "根据考官id获取不含有特定考生的班级列表(不分页)")
    @ResponseBody
    @ApiOperation(value = "根据考官id获取不含有特定考生的班级列表", notes = "" +
            "入参说明:<br/>" +
            "eeId:当前操作考生id<br/>" +
            "token:token<br/>" +
            "出参说明:<br/>" +
            "\n" +
            "{\n" +
            "\n" +
            "    \"data\":[\n" +
            "        {\n" +
            "            \"gradeName\":\"班级名称\",\n" +
            "            \"gradeId\":\"班级id\"\n" +
            "        },\n" +
            "    ],\n" +
            "    \"msg\":\"查询成功\",\n" +
            "    \"status\":200\n" +
            "\n" +
            "}\n")
    public JSONObject getNoEeGradeList(@RequestParam("eeId") String eeId,
                                       @RequestParam("token") String token) {
        CommonResult<JSONArray> result = new CommonResult<>();
        result.setData(JSONArray.fromObject("[]"));
        result.setStatus(400);

        List<Map<String, Object>> gradeList = eeGradeService.getNoEeGradeList(tokenService.getUserId(token), eeId);
        if(ToolUtil.isEmpty(gradeList)){
            result.setMsg("查询结束，但没有结果");
            result.setStatus(204);
        }else {
            result.setStatus(200);
            result.setMsg("查询成功");
            result.setData(JSONArray.fromObject(gradeList));
        }

        return JSONObject.fromObject(result);
    }

    @RequestMapping(value = "/insertEeToGrade", method = RequestMethod.POST)
    @Privilege(methodName = "将考生放入班级", privilegeLevel = Privilege.ER_TYPE)
    @ResponseBody
    @ApiOperation(value = "将考生放入班级", notes = "" +
            "入参说明<br/>" +
            "eeId：考生id<br/>" +
            "gradeId：班级id<br/>" +
            "sortNo：考生在班级内的排序号<br/>" +
            "出参说明<br/>" +
            "201")
    public JSONObject insertEeToGrade(@RequestParam("eeId") String eeId,
                                      @RequestParam("gradeId") String gradeId,
                                      @RequestParam("sortNo") Integer sortNo,
                                      @RequestParam("token") String token) {
        CommonResult<JSONObject> result = new CommonResult<>();
        result.setData(JSONObject.fromObject("{}"));
        result.setStatus(400);

        //查询该班级是否存在该考生
        Example example = new Example(EeGrade.class);
        example.or().andEqualTo("eeId", eeId).andEqualTo("gradeId", gradeId);
        if (ToolUtil.isNotEmpty(eeGradeService.selectByExample(example))) {
            result.setMsg("考生已经在班级内！");
            return JSONObject.fromObject(result);
        }

        EeGrade eeGrade = new EeGrade();
        eeGrade.setId(UUID.randomUUID().toString());
        eeGrade.setEeId(eeId);
        eeGrade.setGradeId(gradeId);
        eeGrade.setSortNo(sortNo);

        if (eeGradeService.insertSelective(eeGrade) > 0) {
            result.setStatus(201);
            result.setMsg("修改成功");
        } else {
            result.setMsg("修改失败");
        }

        return JSONObject.fromObject(result);
    }

    @RequestMapping(value = "/getEeListByNameNoClass", method = RequestMethod.GET)
    @Privilege(methodName = "根据考生姓名、考生号、手机号、所属班级查询当前考官所属考生列表", privilegeLevel = Privilege.ER_TYPE)
    @ResponseBody
    @ApiOperation(value = "根据考生姓名、考生号、手机号、所属班级查询当前考官所属考生列表", notes = "" +
            "入参说明<br/>" +
            "searchClass：班级id（可选）<br/>" +
            "searchString：搜索字符，可查询姓名、考生号、手机号（可选）<br/>" +
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
            "            \"gradeNames\":\"所属班级（逗号隔开）\"\n" +
            "            \"sortNo\":\"排序号\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"limit\":每页条数,\n" +
            "    \"logMsg\":\"日志信息（前端无意义）\",\n" +
            "    \"msg\":\"提示信息\",\n" +
            "    \"page\":当前页\n" +
            "\n" +
            "}\n")
    public JSONObject getEeListByNameNoClass(@RequestParam(value = "searchClass", required = false) String searchClass
            , @RequestParam(value = "searchString", required = false) String searchString
            , @RequestParam("page") Integer page
            , @RequestParam("limit") Integer limit
            , @RequestParam("token") String token) {

        String erId = tokenService.getUserId(token);
        FixPageInfo<Map<String, Object>> logInfo = eeGradeService.getEeListByNameNoClass(erId, searchClass, searchString, page, limit);
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

    @RequestMapping(value = "/setEeGradeSortNo", method = RequestMethod.POST)
    @Privilege(methodName = "设置考生在班级里面的排序号")
    @ResponseBody
    @ApiOperation(value = "设置考生在班级里面的排序号", notes = "" +
            "入参说明:<br/>" +
            "eeId:考生id<br/>" +
            "gradeId:班级id<br/>" +
            "targetSortNo:目标排序号<br/>" +
            "出参说明:<br/>" +
            "201")
    public JSONObject setEeGradeSortNo(@RequestParam("eeId") String eeId,
                                       @RequestParam("gradeId") String gradeId,
                                       @RequestParam("targetSortNo") Integer targetSortNo,
                                       @RequestParam("token") String token) {
        CommonResult<JSONObject> result = new CommonResult<>();
        result.setData(JSONObject.fromObject("{}"));
        result.setStatus(400);

        //根据班级和考生id获取关系id
        Example example = new Example(EeGrade.class);
        example.or().andEqualTo("eeId", eeId).andEqualTo("gradeId", gradeId);
        List<EeGrade> list = eeGradeService.selectByExample(example);
        if (ToolUtil.isEmpty(list)) {
            result.setMsg("班级内不存在该考生！");
            return JSONObject.fromObject(result);
        }

        commonService.setSortNoByLineNum("ee_grade", list.get(0).getId(), targetSortNo, 0, " grade_id = '" + gradeId + "' ");

        result.setMsg("排序完成");
        result.setStatus(201);
        return JSONObject.fromObject(result);
    }

    @RequestMapping(value = "/removeEeFromGrade", method = RequestMethod.POST)
    @Privilege(methodName = "从班级中剔除考生")
    @ResponseBody
    @ApiOperation(value = "从班级中剔除考生", notes = "" +
            "入参说明:<br/>" +
            "eeId:考生id<br/>" +
            "gradeId:班级id<br/>" +
            "出参说明:<br/>" +
            "201")
    public JSONObject removeEeFromGrade(@RequestParam("eeId") String eeId,
                                        @RequestParam("gradeId") String gradeId,
                                        @RequestParam("token") String token) {
        CommonResult<JSONObject> result = new CommonResult<>();
        result.setData(JSONObject.fromObject("{}"));
        result.setStatus(400);

        EeGrade eeGrade = new EeGrade();
        eeGrade.setEeId(eeId);
        eeGrade.setGradeId(gradeId);

        if (eeGradeService.delete(eeGrade) > 0) {
            result.setStatus(201);
            result.setMsg("删除成功");
        } else {
            result.setMsg("删除失败");
        }

        return JSONObject.fromObject(result);
    }
}
