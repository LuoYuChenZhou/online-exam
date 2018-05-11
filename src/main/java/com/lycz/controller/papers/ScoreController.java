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
import java.util.*;

import static com.lycz.configAndDesign.ToolUtil.returnEmptyIfNull;

/**
 * @author lizhenqing
 * @version 1.0
 * @data 2018/5/8 10:16
 */
@Controller
@RequestMapping("Score")
@Api(description = "成绩相关Api")
public class ScoreController {

    @Resource
    private TokenService tokenService;
    @Resource
    private ScoreService scoreService;
    @Resource
    private PaperQuestionService paperQuestionService;

    @RequestMapping(value = "/getTimeLeft", method = RequestMethod.GET)
    @Privilege(methodName = "获取剩余分钟数", privilegeLevel = Privilege.EE_TYPE)
    @ResponseBody
    @ApiOperation(value = "获取剩余分钟数", notes = "" +
            "入参说明:<br/>" +
            "出参说明:<br/>")
    public JSONObject getTimeLeft(@RequestParam("paperId") String paperId,
                                  @RequestParam("token") String token) {
        CommonResult<JSONObject> result = new CommonResult<>();
        result.setData(JSONObject.fromObject("{}"));
        result.setStatus(400);

        Map<String, Object> timeMap = scoreService.getStartAndAllTime(paperId, tokenService.getUserId(token));
        if (ToolUtil.isNotEmpty(timeMap)) {
            if (ToolUtil.isNotEmpty(timeMap.get("answer"))) {
                result.setStatus(104);
            }
            long startTime = ((Date) timeMap.get("blurStartTime")).getTime();
            long timeLeft = (int) timeMap.get("examTime") * 60 - (System.currentTimeMillis() - startTime) / 1000;
            if (timeLeft <= 20) {
                result.setStatus(103);
                result.setData(JSONObject.fromObject("{\"timeLeft\":\"" + timeLeft + "\"}"));
            } else if (timeLeft <= 60) {
                result.setStatus(200);
                result.setData(JSONObject.fromObject("{\"timeLeft\":\"<1\"}"));
            } else {
                result.setStatus(200);
                result.setData(JSONObject.fromObject("{\"timeLeft\":" + timeLeft / 60 + "}"));
            }
        } else {
            result.setMsg("获取剩余时间失败");
            result.setLogMsg("获取剩余时间失败，试卷id：" + paperId + "考生id：" + tokenService.getUserId(token));
        }

        return JSONObject.fromObject(result);
    }

    @RequestMapping(value = "/saveAnswer", method = RequestMethod.POST)
    @Privilege(methodName = "保存答案")
    @ResponseBody
    @ApiOperation(value = "保存答案", notes = "" +
            "入参说明:<br/>" +
            "出参说明:<br/>")
    public JSONObject saveAnswer(@RequestParam("answerInfo") String answerInfo,
                                 @RequestParam("paperId") String paperId,
                                 @RequestParam("token") String token) {
        CommonResult<JSONObject> result = new CommonResult<>();
        result.setData(JSONObject.fromObject("{}"));
        result.setStatus(400);
        result.setMsg("系统繁忙");

        // 根据试卷id和token获取用于保存的Score对象
        Example example1 = new Example(Score.class);
        example1.or().andEqualTo("eeId", tokenService.getUserId(token)).andEqualTo("paperId", paperId);
        List<Score> scoreList = scoreService.selectByExample(example1);
        if (ToolUtil.isEmpty(scoreList)) {
            result.setLogMsg("保存答案时找不到Score对象，试卷id：" + paperId + "考生id：" + tokenService.getUserId(token));
            return JSONObject.fromObject(result);
        }

        Score score = scoreList.get(0);
        // 判断是否已经交卷
        if (ToolUtil.isNotEmpty(score.getScoreDetail())) {
            result.setMsg("您已交卷");
            result.setLogMsg("重复交卷，试卷id：" + paperId + "考生id：" + tokenService.getUserId(token));
            return JSONObject.fromObject(result);
        }

        Score score1 = new Score();
        score1.setId(score.getId());
        score1.setCommitTime(new Date());
        score1.setAnswer(answerInfo);

        if (scoreService.updateByPrimaryKeySelective(score1) > 0) {
            result.setMsg("提交成功");
            result.setStatus(201);
            result.setData(JSONObject.fromObject("{\"scoreId\":\"" + score1.getId() + "\"}"));
        } else {
            result.setStatus(400);
            result.setLogMsg("保存答案时发生错误");
        }

        return JSONObject.fromObject(result);
    }

    @RequestMapping(value = "/autoCorrect", method = RequestMethod.POST)
    @Privilege(methodName = "自动批改")
    @ResponseBody
    @ApiOperation(value = "自动批改", notes = "" +
            "入参说明:<br/>" +
            "出参说明:<br/>")
    public JSONObject autoCorrect(@RequestParam("scoreId") String scoreId, @RequestParam("token") String token) {
        CommonResult<JSONObject> result = new CommonResult<>();
        result.setData(JSONObject.fromObject("{}"));
        result.setStatus(400);

        // 获取Json格式的答案
        Score score = scoreService.selectByKey(scoreId);
        if (ToolUtil.isEmpty(score)) {
            result.setMsg("获取答案信息失败");
            result.setLogMsg("获取答案信息失败,scoreId:" + scoreId);
            return JSONObject.fromObject(result);
        }
        JSONObject answerObj = JSONObject.fromObject(score.getAnswer());

        // 获取试卷试题列表
        List<Map<String, Object>> questionList = paperQuestionService.getPaperQuestionInfoById(null, score.getPaperId());

        // 根据试题id和试题批改类型，将自动批改的试题批改，获得得分详情json
        boolean allAuto = true;
        int allScoreCount = 0;
        Map<String, String> scoreDetailMap = new HashMap<>();
        for (Map<String, Object> qaMap : questionList) {
            if (Objects.equals("1", qaMap.get("correctType"))) {
                String questionId = (String) qaMap.get("questionId");
                // 如果此题没有答案，则不进行批改
                if (ToolUtil.isNotEmpty(answerObj.get(questionId))) {
                    String answer = answerObj.getString(questionId);
                    //0-选择，1-填空
                    if (Objects.equals("0", qaMap.get("questionType"))) {
                        // 选择题答案为0表示没有选择
                        if (Objects.equals("0", answer)) {
                            scoreDetailMap.put(questionId, "0");
                        } else {
                            if (Objects.equals("0", qaMap.get("isMulti"))) {
                                if (Objects.equals(answer, qaMap.get("answer"))) {
                                    scoreDetailMap.put(questionId, (String) qaMap.get("fullScore"));
                                } else {
                                    scoreDetailMap.put(questionId, "0");
                                }
                            } else {
                                if (Objects.equals(answer, qaMap.get("answer"))) {
                                    scoreDetailMap.put(questionId, (String) qaMap.get("fullScore"));
                                } else {
                                    if (Objects.equals("1", qaMap.get("scoreType"))) {
                                        scoreDetailMap.put(questionId, "0");
                                    } else {
                                        Integer wrongAnswer = ToolUtil.getWrongOptionsValue(Integer.parseInt((String) qaMap.get("answer")));
                                        Integer inputAnswer = Integer.parseInt(answer);
                                        if (ToolUtil.isEmpty(wrongAnswer)) {
                                            scoreDetailMap.put(questionId, (String) qaMap.get("assignScore"));
                                        } else {
                                            if ((inputAnswer & wrongAnswer) > 0) {
                                                scoreDetailMap.put(questionId, "0");
                                            } else {
                                                scoreDetailMap.put(questionId, "" + qaMap.get("assignScore"));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else if (Objects.equals("1", qaMap.get("questionType"))) {
                        String rightAnswer = (String) qaMap.get("answer");
                        String rightAnswerScore = (String) qaMap.get("questionScore");
                        String[] answerArray = answerObj.getString(questionId).substring(0, answerObj.getString(questionId).length() - 2).split("\\$\\$");
                        String[] rightAnswerArray = rightAnswer.substring(0, rightAnswer.length() - 2).split("\\$\\$");
                        String[] scoreArray = rightAnswerScore.substring(0, rightAnswerScore.length() - 1).split(",");
                        Integer countScore = 0;
                        for (int m = 0; m < answerArray.length; m++) {
                            if (Objects.equals(answerArray[m], rightAnswerArray[m])) {
                                countScore += Integer.parseInt(scoreArray[m]);
                            }
                        }
                        scoreDetailMap.put(questionId, "" + countScore);
                    }
                    allScoreCount += Integer.parseInt(scoreDetailMap.get(questionId));
                }
            } else {
                allAuto = false;
            }
        }

        Score score1 = new Score();
        score1.setId(score.getId());
        score1.setScoreDetail(JSONObject.fromObject(scoreDetailMap).toString());

        // 如果所有题目都自动批改了，设置总分
        if (allAuto) {
            score1.setScore(allScoreCount);
        }

        if (scoreService.updateByPrimaryKeySelective(score1) > 0) {
            result.setMsg("批改完成");
            result.setStatus(201);
        } else {
            result.setStatus(400);
            result.setLogMsg("保存批改信息发生错误");
        }

        return JSONObject.fromObject(result);
    }

    @RequestMapping(value = "/getScoreListByPaperId", method = RequestMethod.GET)
    @Privilege(methodName = "根据试卷id获取考试信息", privilegeLevel = Privilege.ER_TYPE)
    @ResponseBody
    @ApiOperation(value = "根据试卷id获取考试信息", notes = "" +
            "入参说明:<br/>" +
            "出参说明:<br/>")
    public JSONObject getScoreListByPaperId(@RequestParam("paperId") String paperId,
                                            @RequestParam(value = "searchEeInfo", required = false) String searchEeInfo,
                                            @RequestParam("page") Integer page,
                                            @RequestParam("limit") Integer limit,
                                            @RequestParam("token") String token) {

        FixPageInfo<Map<String, Object>> pageInfo = scoreService.getScoreListByPaperId(paperId, searchEeInfo, page, limit);
        if (pageInfo == null) {
            pageInfo = new FixPageInfo<>();
            pageInfo.setMsg("查询结束，但没有数据");
            pageInfo.setCode(204);
        } else {
            pageInfo.setMsg("查询成功");
            pageInfo.setCode(0);
        }

        return JSONObject.fromObject(pageInfo);
    }

    @RequestMapping(value = "/changeScore", method = RequestMethod.POST)
    @Privilege(methodName = "修改考试成绩", privilegeLevel = Privilege.ER_TYPE)
    @ResponseBody
    @ApiOperation(value = "修改考试成绩", notes = "" +
            "入参说明:<br/>" +
            "出参说明:<br/>")
    public JSONObject changeScore(Score score,
                                  @RequestParam("saveCountScore") String saveCountScore,
                                  @RequestParam("token") String token) {
        CommonResult<JSONObject> result = new CommonResult<>();
        result.setData(JSONObject.fromObject("{}"));
        result.setStatus(400);

        boolean flag;

        Score score1;
        // 如果saveCountScore为1表示保存总分，用updateByPrimaryKeySelective保存
        if ("1".equals(saveCountScore)) {
            score1 = new Score();
            score1.setId(score.getId());
            score1.setScore(score.getScore());
            score1.setScoreDetail(score.getScoreDetail());
            flag = scoreService.updateByPrimaryKeySelective(score1) > 0;
        } else {
            // 不保存总分的情况需要把得分设为null，用updateAll保存
            score1 = scoreService.selectByKey(score.getId());
            score1.setScore(null);
            score1.setScoreDetail(score.getScoreDetail());
            flag = scoreService.updateAll(score1) > 0;
        }

        if (flag) {
            result.setMsg("修改成功");
            result.setStatus(201);
        } else {
            result.setMsg("修改失败");
        }

        return JSONObject.fromObject(result);
    }
}
