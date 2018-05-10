package com.lycz.controller.papers;

import com.lycz.configAndDesign.CommonResult;
import com.lycz.configAndDesign.FixPageInfo;
import com.lycz.configAndDesign.ToolUtil;
import com.lycz.configAndDesign.annotation.Privilege;
import com.lycz.model.*;
import com.lycz.service.base.TokenService;
import com.lycz.service.paper.PaperQuestionService;
import com.lycz.service.paper.PapersService;
import com.lycz.service.paper.ScoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.lycz.configAndDesign.ToolUtil.returnEmptyIfNull;

@Controller
@RequestMapping("Papers")
@Api(description = "试卷相关Api")
public class PapersController {
    @Resource
    private PapersService papersService;
    @Resource
    private TokenService tokenService;
    @Resource
    private PaperQuestionService paperQuestionService;
    @Resource
    private ScoreService scoreService;

    @RequestMapping(value = "/addPaper", method = RequestMethod.POST)
    @Privilege(methodName = "添加新试卷", privilegeLevel = Privilege.ER_TYPE)
    @ResponseBody
    @ApiOperation(value = "添加新试卷", notes = "" +
            "入参说明:<br/>" +
            "出参说明:<br/>" +
            "201")
    public JSONObject addPaper(Papers paperInfo, BatchListEntity questionInfoList,
                               @RequestParam(value = "endTimeS", required = false) String endTimeS,
                               @RequestParam("token") String token) throws ParseException {
        CommonResult<JSONObject> result = new CommonResult<>();
        result.setData(JSONObject.fromObject("{}"));
        result.setMsg("新增失败");
        result.setStatus(400);

        String newPaperId = UUID.randomUUID().toString();
        String createUserId = tokenService.getUserId(token);
        Date date = new Date();

        if (ToolUtil.isNotEmpty(endTimeS)) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            paperInfo.setEndTime(df.parse(endTimeS));
        } else if (Objects.equals(paperInfo.getStatus(), "2")) {
            result.setMsg("考试截止时间必填");
            result.setStatus(400);
        }

        paperInfo.setId(newPaperId);
        paperInfo.setExaminerId(createUserId);
        paperInfo.setCreateTime(date);
        paperInfo.setModifyTime(date);

        List<BaseQuestions> baseQuestionsList = questionInfoList.getBaseQuestionsList();
        List<PaperQuestion> paperQuestionList = questionInfoList.getPaperQuestionList();
        if (ToolUtil.isNotEmpty(baseQuestionsList)) {
            for (int i = 0; i < baseQuestionsList.size(); i++) {
                BaseQuestions bq = baseQuestionsList.get(i);
                PaperQuestion pq = paperQuestionList.get(i);

                String newBqId = UUID.randomUUID().toString();

                bq.setId(newBqId);
                bq.setErId(createUserId);
                bq.setCreateTime(date);
                bq.setModifyTime(date);

                pq.setId(UUID.randomUUID().toString());
                pq.setPaperId(newPaperId);
                pq.setQuestionId(newBqId);
            }
        }

        if (papersService.addNewPaper(paperInfo, baseQuestionsList, paperQuestionList)) {
            result.setStatus(201);
            result.setMsg("保存成功");
        }

        return JSONObject.fromObject(result);
    }

    @RequestMapping(value = "/modifyPaper", method = RequestMethod.POST)
    @Privilege(methodName = "修改试卷", privilegeLevel = Privilege.ER_TYPE)
    @ResponseBody
    @ApiOperation(value = "修改试卷", notes = "" +
            "入参说明:<br/>" +
            "出参说明:<br/>" +
            "201")
    public JSONObject modifyPaper(Papers paperInfo, BatchListEntity questionInfoList,
                                  @RequestParam("delQaId") String delQaId,
                                  @RequestParam("token") String token) {
        CommonResult<JSONObject> result = new CommonResult<>();
        result.setData(JSONObject.fromObject("{}"));
        result.setMsg("保存失败");
        result.setStatus(400);

        String paperId = paperInfo.getId();
        String createUserId = tokenService.getUserId(token);
        Date date = new Date();

        paperInfo.setModifyTime(date);

        List<BaseQuestions> baseQuestionsList = questionInfoList.getBaseQuestionsList();
        List<PaperQuestion> paperQuestionList = questionInfoList.getPaperQuestionList();
        List<BaseQuestions> bqAddList = new ArrayList<>();
        List<BaseQuestions> bqModifyList = new ArrayList<>();
        List<PaperQuestion> pqAddList = new ArrayList<>();
        List<PaperQuestion> pqModifyList = new ArrayList<>();
        if (ToolUtil.isNotEmpty(baseQuestionsList)) {
            for (int i = 0; i < baseQuestionsList.size(); i++) {
                BaseQuestions bq = baseQuestionsList.get(i);
                PaperQuestion pq = paperQuestionList.get(i);

                if (ToolUtil.isNotEmpty(bq.getId())) {
                    bq.setModifyTime(date);
                    bqModifyList.add(bq);
                    pqModifyList.add(pq);
                } else {
                    String newBqId = UUID.randomUUID().toString();

                    bq.setId(newBqId);
                    bq.setErId(createUserId);
                    bq.setCreateTime(date);
                    bq.setModifyTime(date);

                    pq.setId(UUID.randomUUID().toString());
                    pq.setPaperId(paperId);
                    pq.setQuestionId(newBqId);

                    bqAddList.add(bq);
                    pqAddList.add(pq);
                }
            }
        }

        if (papersService.modifyPaper(paperInfo, pqAddList, pqModifyList, bqAddList, bqModifyList, delQaId)) {
            result.setStatus(201);
            result.setMsg("保存成功");
        }

        return JSONObject.fromObject(result);
    }

    @Privilege(methodName = "根据试卷名搜索（考官端）", privilegeLevel = Privilege.ER_TYPE)
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

    @Privilege(methodName = "根据试卷名和出题考官搜索已发布的试卷（考生端）", privilegeLevel = Privilege.EE_TYPE)
    @RequestMapping(value = "/selectPapersByErName", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "根据试卷名和出题考官搜索已发布的试卷（考生端）", notes = "" +
            "入参说明：<br/>" +
            "paperErId：出题考官id<br/>" +
            "searchPaperName：搜索的试卷名称<br/>" +
            "出参说明：<br/>" +
            "\n" +
            "{\n" +
            "\n" +
            "    \"code\":0,\n" +
            "    \"count\":总计条数,\n" +
            "    \"data\":[\n" +
            "        {\n" +
            "            \"hadExam\":是否参加过该考试,\n" +
            "            \"papersName\":\"试卷名称\",\n" +
            "            \"erName\":\"考官姓名\",\n" +
            "            \"fullScore\":试卷总分,\n" +
            "            \"examTime\":考试时间（分钟）\n" +
            "        }\n" +
            "    ],\n" +
            "    \"limit\":每页条数,\n" +
            "    \"msg\":\"查询成功\",\n" +
            "    \"page\":当前页\n" +
            "\n" +
            "}\n")
    public JSONObject selectPapersByErName(@RequestParam(value = "searchPaperName", required = false) String searchPaperName,
                                           @RequestParam("paperErId") String paperErId,
                                           @RequestParam("page") Integer page,
                                           @RequestParam("limit") Integer limit,
                                           @RequestParam("token") String token) {
        FixPageInfo<Map<String, Object>> pageInfo = papersService.selectPapersByErName(tokenService.getUserId(token), searchPaperName, paperErId, page, limit);
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
    @Privilege(methodName = "修改试卷名称", privilegeLevel = Privilege.ER_TYPE)
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
    @Privilege(methodName = "修改试卷名称", privilegeLevel = Privilege.ER_TYPE)
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

    @RequestMapping(value = "/getPaperQuestionInfoById", method = RequestMethod.GET)
    @Privilege(methodName = "根据试卷id获取详细信息(考官端)", privilegeLevel = Privilege.ER_TYPE)
    @ResponseBody
    @ApiOperation(value = "根据试卷id获取详细信息", notes = "" +
            "入参说明:<br/>" +
            "paperId:试卷id" +
            "出参说明:<br/>" +
            "\n" +
            "{\n" +
            "\n" +
            "    \"data\":{\n" +
            "        \"paperId\":\"试卷id\"\n" +
            "        \"defaultSubject\":\"默认科目\",\n" +
            "        \"paperName\":\"试卷名称\",\n" +
            "        \"examTime\":考试时间,\n" +
            "        \"questionList\":[\n" +
            "            {\n" +
            "                \"questionDesc\":\"问题描述\",\n" +
            "                \"answer\":\"答案\",\n" +
            "                \"subject\":\"科目\",\n" +
            "                \"options\":\"选项\",\n" +
            "                \"questionScore\":\"总分\",\n" +
            "                \"scoreType\":\"得分模式\",\n" +
            "                \"assignScore\":\"指定分\",\n" +
            "                \"correctType\":\"批改模式\",\n" +
            "                \"questionType\":\"问题类型\"\n" +
            "            }\n" +
            "        ],\n" +
            "    },\n" +
            "    \"logMsg\":\"\",\n" +
            "    \"msg\":\"获取成功\",\n" +
            "    \"status\":200\n" +
            "\n" +
            "}\n")
    public JSONObject getPaperQuestionInfoById(@RequestParam("paperId") String paperId,
                                               @RequestParam("token") String token) {
        CommonResult<JSONObject> result = new CommonResult<>();
        result.setData(JSONObject.fromObject("{}"));
        result.setStatus(400);

        Example example = new Example(Papers.class);
        example.or().andNotEqualTo("status", "4").andEqualTo("id", paperId);
        List<Papers> paperInfo = papersService.selectByExample(example);
        if (ToolUtil.isEmpty(paperInfo)) {
            result.setMsg("试卷读取错误");
            result.setLogMsg("读取试卷时发现不存在试卷，试卷id：" + paperId);
            return JSONObject.fromObject(result);
        }

        Map<String, Object> finalMap = new HashMap<>();
        Papers paper = paperInfo.get(0);
        finalMap.put("paperId", returnEmptyIfNull(paper.getId()));
        finalMap.put("paperName", returnEmptyIfNull(paper.getPapersName()));
        finalMap.put("defaultSubject", returnEmptyIfNull(paper.getDefaultSubject()));
        finalMap.put("examTime", returnEmptyIfNull(paper.getExamTime()));
        finalMap.put("fullScore", returnEmptyIfNull(paper.getFullScore()));
        finalMap.put("endTime", returnEmptyIfNull(paper.getEndTime()));

        List<Map<String, Object>> questionList = paperQuestionService.getPaperQuestionInfoById(null, paperId);
        if (ToolUtil.isNotEmpty(questionList)) {
            finalMap.put("questionList", questionList);
        }

        result.setMsg("获取成功");
        result.setStatus(200);
        result.setData(JSONObject.fromObject(finalMap));
        return JSONObject.fromObject(result);
    }

    @RequestMapping(value = "/getPaperQuestionSimpleInfoById", method = RequestMethod.GET)
    @Privilege(methodName = "根据试卷id获取详细信息(考生端)", privilegeLevel = Privilege.EE_TYPE)
    @ResponseBody
    @ApiOperation(value = "根据试卷id获取详细信息", notes = "" +
            "入参说明:<br/>" +
            "paperId:试卷id" +
            "出参说明:<br/>" +
            "\n" +
            "{\n" +
            "\n" +
            "    \"data\":{\n" +
            "        \"paperId\":\"试卷id\"\n" +
            "        \"paperName\":\"试卷名称\",\n" +
            "        \"examTime\":考试时间,\n" +
            "        \"questionList\":[\n" +
            "            {\n" +
            "                \"questionId\":\"问题id\",\n" +
            "                \"questionDesc\":\"问题描述\",\n" +
            "                \"subject\":\"科目\",\n" +
            "                \"options\":\"选项\",\n" +
            "                \"questionScore\":\"总分\",\n" +
            "                \"isMulti\":\"是否多选\",\n" +
            "                \"fullScore\":\"真总分\",\n" +
            "                \"blankIndex\":\"填空题专属\",\n" +
            "                \"scoreType\":\"得分模式\",\n" +
            "                \"assignScore\":\"指定分\",\n" +
            "                \"correctType\":\"批改模式\",\n" +
            "                \"questionType\":\"问题类型\"\n" +
            "            }\n" +
            "        ],\n" +
            "    },\n" +
            "    \"logMsg\":\"\",\n" +
            "    \"msg\":\"获取成功\",\n" +
            "    \"status\":200\n" +
            "\n" +
            "}\n")
    public JSONObject getPaperQuestionSimpleInfoById(@RequestParam("paperId") String paperId,
                                                     @RequestParam("token") String token) {
        CommonResult<JSONObject> result = new CommonResult<>();
        result.setData(JSONObject.fromObject("{}"));
        result.setStatus(400);

        Example example = new Example(Papers.class);
        example.or().andEqualTo("status", "2").andEqualTo("id", paperId);
        List<Papers> paperInfo = papersService.selectByExample(example);
        if (ToolUtil.isEmpty(paperInfo)) {
            result.setMsg("试卷读取错误");
            result.setLogMsg("读取试卷时发现不存在试卷，试卷id：" + paperId);
            return JSONObject.fromObject(result);
        }

        Map<String, Object> finalMap = new HashMap<>();
        Papers paper = paperInfo.get(0);
        finalMap.put("paperId", returnEmptyIfNull(paper.getId()));
        finalMap.put("paperName", returnEmptyIfNull(paper.getPapersName()));
        finalMap.put("examTime", returnEmptyIfNull(paper.getExamTime()));

        List<Map<String, Object>> questionList;

        // 根据剩余时间判断是否参加过考试并且时间已用完
        Example example1 = new Example(Score.class);
        example1.or().andEqualTo("eeId", tokenService.getUserId(token)).andEqualTo("paperId", paperId);
        List<Score> scoreList = scoreService.selectByExample(example1);
        if (ToolUtil.isNotEmpty(scoreList)) {
            Score score = scoreList.get(0);
            if (ToolUtil.isNotEmpty(score.getAnswer())) {
                result.setMsg("您已参加过此考试");
                result.setLogMsg("id为" + tokenService.getUserId(token) + "的考生重复参加试卷id为" + paperId + "的考试");
                return JSONObject.fromObject(result);
            }
            long timeLeft = paper.getExamTime() * 60000 - (System.currentTimeMillis() - score.getBlurStartTime().getTime());
            if (timeLeft > 0) {
                questionList = paperQuestionService.getPaperQuestionInfoById("1", paperId);
            } else {
                result.setMsg("您已参加过此考试");
                result.setLogMsg("id为" + tokenService.getUserId(token) + "的考生重复参加试卷id为" + paperId + "的考试");
                return JSONObject.fromObject(result);
            }
        } else {
            questionList = paperQuestionService.getPaperQuestionInfoById("1", paperId, tokenService.getUserId(token));
        }
        if (ToolUtil.isNotEmpty(questionList)) {
            finalMap.put("questionList", questionList);
        }

        result.setMsg("获取成功");
        result.setStatus(200);
        result.setData(JSONObject.fromObject(finalMap));
        return JSONObject.fromObject(result);
    }

    @RequestMapping(value = "/getPaperQuestionInfoByIdAfterExam", method = RequestMethod.GET)
    @Privilege(methodName = "根据试卷id获取详细信息(考试后查看用)")
    @ResponseBody
    @ApiOperation(value = "根据试卷id获取详细信息(考试后查看用)", notes = "" +
            "入参说明:<br/>" +
            "paperId:试卷id" +
            "出参说明:<br/>" +
            "\n" +
            "{\n" +
            "\n" +
            "    \"data\":{\n" +
            "        \"paperId\":\"试卷id\"\n" +
            "        \"paperName\":\"试卷名称\",\n" +
            "        \"examTime\":考试时间,\n" +
            "        \"questionList\":[\n" +
            "            {\n" +
            "                \"questionId\":\"问题id\",\n" +
            "                \"questionDesc\":\"问题描述\",\n" +
            "                \"subject\":\"科目\",\n" +
            "                \"options\":\"选项\",\n" +
            "                \"questionScore\":\"总分\",\n" +
            "                \"isMulti\":\"是否多选\",\n" +
            "                \"fullScore\":\"真总分\",\n" +
            "                \"blankIndex\":\"填空题专属\",\n" +
            "                \"scoreType\":\"得分模式\",\n" +
            "                \"assignScore\":\"指定分\",\n" +
            "                \"correctType\":\"批改模式\",\n" +
            "                \"questionType\":\"问题类型\"\n" +
            "            }\n" +
            "        ],\n" +
            "    },\n" +
            "    \"logMsg\":\"\",\n" +
            "    \"msg\":\"获取成功\",\n" +
            "    \"status\":200\n" +
            "\n" +
            "}\n")
    public JSONObject getPaperQuestionInfoByIdAfterExam(@RequestParam("paperId") String paperId,
                                                        @RequestParam("token") String token) {
        CommonResult<JSONObject> result = new CommonResult<>();
        result.setData(JSONObject.fromObject("{}"));
        result.setStatus(400);

        Example example = new Example(Papers.class);
        example.or().andEqualTo("status", "2").andEqualTo("id", paperId);
        List<Papers> paperInfo = papersService.selectByExample(example);
        if (ToolUtil.isEmpty(paperInfo)) {
            result.setMsg("试卷读取错误");
            result.setLogMsg("读取试卷时发现不存在试卷，试卷id：" + paperId);
            return JSONObject.fromObject(result);
        }

        Map<String, Object> finalMap = new HashMap<>();
        Papers paper = paperInfo.get(0);
        finalMap.put("paperId", returnEmptyIfNull(paper.getId()));
        finalMap.put("paperName", returnEmptyIfNull(paper.getPapersName()));
        finalMap.put("examTime", returnEmptyIfNull(paper.getExamTime()));

        List<Map<String, Object>> questionList;

        // 根据剩余时间判断是否参加过考试并且时间已用完
        Example example1 = new Example(Score.class);
        example1.or().andEqualTo("eeId", tokenService.getUserId(token)).andEqualTo("paperId", paperId);
        List<Score> scoreList = scoreService.selectByExample(example1);
        if (ToolUtil.isNotEmpty(scoreList)) {
            Score score = scoreList.get(0);
            if (ToolUtil.isNotEmpty(score.getAnswer())) {
                result.setMsg("您已参加过此考试");
                result.setLogMsg("id为" + tokenService.getUserId(token) + "的考生重复参加试卷id为" + paperId + "的考试");
                return JSONObject.fromObject(result);
            }
            long timeLeft = paper.getExamTime() * 60000 - (System.currentTimeMillis() - score.getBlurStartTime().getTime());
            if (timeLeft > 0) {
                questionList = paperQuestionService.getPaperQuestionInfoById("1", paperId);
            } else {
                result.setMsg("您已参加过此考试");
                result.setLogMsg("id为" + tokenService.getUserId(token) + "的考生重复参加试卷id为" + paperId + "的考试");
                return JSONObject.fromObject(result);
            }
        } else {
            questionList = paperQuestionService.getPaperQuestionInfoById(null, paperId, tokenService.getUserId(token));
        }
        if (ToolUtil.isNotEmpty(questionList)) {
            finalMap.put("questionList", questionList);
        }

        result.setMsg("获取成功");
        result.setStatus(200);
        result.setData(JSONObject.fromObject(finalMap));
        return JSONObject.fromObject(result);
    }
}
