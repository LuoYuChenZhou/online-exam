package com.lycz.controller.user;

import com.github.pagehelper.PageInfo;
import com.lycz.configAndDesign.CommonResult;
import com.lycz.configAndDesign.FixPageInfo;
import com.lycz.configAndDesign.ToolUtil;
import com.lycz.configAndDesign.annotation.Privilege;
import com.lycz.model.ErEe;
import com.lycz.service.base.TokenService;
import com.lycz.service.user.ErEeService;
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
import java.util.*;

/**
 * @author lizhenqing
 * @version 1.0
 * @data 2018/3/2 10:42
 */
@Controller
@RequestMapping("ErEe")
@Api(value = "ErEe", description = "考生考官关系相关api")
public class ErEeController {

    @Resource
    private TokenService tokenService;
    @Resource
    private ErEeService erEeService;

    @RequestMapping(value = "/getInvitedList", method = RequestMethod.GET)
    @Privilege(methodName = "根据考生id（token中）获取邀请列表", privilegeLevel = Privilege.EE_TYPE)
    @ResponseBody
    @ApiOperation(value = "根据考生id（token中）获取邀请列表", notes = "" +
            "入参说明:<br/>" +
            "page:当前页<br/>" +
            "limit:每页大小<br/>" +
            "token:token<br/>" +
            "出参说明:<br/>" +
            "\n" +
            "{\n" +
            "\n" +
            "    \"data\":{\n" +
            "        \"hasNextPage\":是否有下一页（true或false）,\n" +
            "        \"hasPreviousPage\":是否有前一页（true或false）,\n" +
            "        \"list\":[\n" +
            "            {\n" +
            "                \"erEeId\":\"关系id\"\n" +
            "                \"erName\":\"邀请人姓名\"\n" +
            "                \"extraMsg\":\"附加信息，【\\<\\b\\r\\/\\>附加说明：】后面的是真的附加信息\",\n" +
            "            }\n" +
            "        ],\n" +
            "    },\n" +
            "    \"logMsg\":\"\",\n" +
            "    \"msg\":\"查询成功\",\n" +
            "    \"status\":200\n" +
            "\n" +
            "}\n")
    public JSONObject getInvitedList(@RequestParam("page") Integer page,
                                     @RequestParam("limit") Integer limit,
                                     @RequestParam("token") String token) {
        String userId = tokenService.getUserId(token);

        CommonResult<JSONObject> result = new CommonResult<>();
        result.setData(JSONObject.fromObject("{}"));

        PageInfo<Map<String, Object>> pageInfo = erEeService.getInvitedList(page, limit, userId);
        if (pageInfo == null) {
            result.setMsg("查询结束，但没有数据");
            result.setStatus(204);
        } else {
            result.setMsg("查询成功");
            result.setStatus(200);
            result.setData(JSONObject.fromObject(pageInfo));
        }

        return JSONObject.fromObject(result);
    }

    @RequestMapping(value = "/getApplyList", method = RequestMethod.GET)
    @Privilege(methodName = "根据考官id（token中）获取申请列表", privilegeLevel = Privilege.ER_TYPE)
    @ResponseBody
    @ApiOperation(value = "根据考官id（token中）获取申请列表", notes = "" +
            "入参说明:<br/>" +
            "page:当前页<br/>" +
            "limit:每页大小<br/>" +
            "token:token<br/>" +
            "出参说明:<br/>" +
            "\n" +
            "{\n" +
            "\n" +
            "    \"data\":{\n" +
            "        \"hasNextPage\":是否有下一页（true或false）,\n" +
            "        \"hasPreviousPage\":是否有前一页（true或false）,\n" +
            "        \"list\":[\n" +
            "            {\n" +
            "                \"erEeId\":\"关系id\"\n" +
            "                \"erName\":\"邀请人姓名\"\n" +
            "                \"extraMsg\":\"附加信息，【\\<\\b\\r\\/\\>附加说明：】后面的是真的附加信息\",\n" +
            "            }\n" +
            "        ],\n" +
            "    },\n" +
            "    \"logMsg\":\"\",\n" +
            "    \"msg\":\"查询成功\",\n" +
            "    \"status\":200\n" +
            "\n" +
            "}\n")
    public JSONObject getApplyList(@RequestParam("page") Integer page,
                                   @RequestParam("limit") Integer limit,
                                   @RequestParam("token") String token) {
        String userId = tokenService.getUserId(token);

        CommonResult<JSONObject> result = new CommonResult<>();
        result.setData(JSONObject.fromObject("{}"));

        PageInfo<Map<String, Object>> pageInfo = erEeService.getApplyList(page, limit, userId);
        if (pageInfo == null) {
            result.setMsg("查询结束，但没有数据");
            result.setStatus(204);
        } else {
            result.setMsg("查询成功");
            result.setStatus(200);
            result.setData(JSONObject.fromObject(pageInfo));
        }

        return JSONObject.fromObject(result);
    }

    @RequestMapping(value = "/getExamineeNoRelation", method = RequestMethod.GET)
    @Privilege(methodName = "获取与自己没有建立关系的考生列表", privilegeLevel = Privilege.ER_TYPE)
    @ResponseBody
    @ApiOperation(value = "获取与自己没有建立关系的考生列表", notes = "" +
            "入参说明<br/>" +
            "searchString：搜索字符，查询姓名、考生号、手机号（可选）<br/>" +
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
    public JSONObject getExamineeNoRelation(@RequestParam(value = "searchString", required = false) String searchString,
                                            @RequestParam("page") Integer page,
                                            @RequestParam("limit") Integer limit,
                                            @RequestParam("token") String token) {

        String userId = tokenService.getUserId(token);
        FixPageInfo<Map<String, Object>> pageInfo = erEeService.getExamineeNoRelation(searchString, page, limit, userId);
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

    /**
     * 请求建立考生考官关系,如果双方同时申请，直接建立关系
     */
    @RequestMapping(value = "/requestEeEr", method = RequestMethod.POST)
    @Privilege(methodName = "请求建立考生考官关系")
    @ResponseBody
    @ApiOperation(value = "请求建立考生考官关系", notes = "" +
            "入参说明：<br/>" +
            "targetId：对方id（如：考生申请填考官id）<br/>" +
            "operate：操作类型（考生申请-eeRequest,考官邀请-erRequest）<br/>" +
            "gradeId：班级id（考官邀请传入，传入noAddToGrade表示不添加进班级）<br/>" +
            "sortNo：考生在班级内的排序号（不传为1）<br/>" +
            "ExtraMsg：附加说明<br/>" +
            "出参说明：<br/>" +
            "201")
    public JSONObject requestEeEr(@RequestParam("targetId") String targetId,
                                  @RequestParam("operate") String operate,
                                  @RequestParam(value = "gradeId", required = false) String gradeId,
                                  @RequestParam(value = "sortNo", required = false) Integer sortNo,
                                  @RequestParam(value = "extraMsg", required = false) String extraMsg,
                                  @RequestParam("token") String token) throws Exception {
        CommonResult<JSONObject> result = new CommonResult<>();
        result.setData(JSONObject.fromObject("{}"));
        result.setStatus(400);

        long curTime = System.currentTimeMillis();
        Map<String, Object> tokenMap = tokenService.getTokenMap(token);
        String userId = (String) tokenMap.get("id");
        String erSex = Objects.equals(tokenMap.get("sex"), "1") ? "她" : "他";
        String sendName = (String) tokenMap.get("realName");//发送方姓名
        String flag;
        ErEe erEe;

        Example example = new Example(ErEe.class);
        switch (operate) {
            case "eeRequest":
                //查找是否存在对方申请(只查询当前状态小于4的)
                example.or().andEqualTo("examinerId", targetId).andEqualTo("examineeId", userId).andLessThan("curStatus", "4");
                List<ErEe> oldErEeList = erEeService.selectByExample(example);
                if (ToolUtil.isNotEmpty(oldErEeList)) {
                    if (oldErEeList.size() > 1) {
                        result.setMsg("关系异常，请联系管理员");
                        result.setLogMsg("存在相同的考生考官关系，考生：" + userId + "考官：" + targetId);
                        return JSONObject.fromObject(result);
                    } else {
                        erEe = oldErEeList.get(0);
                        if (ToolUtil.anyEqual(erEe.getCurStatus(), "1", "3")) {
                            result.setMsg("已添加此考官");
                            return JSONObject.fromObject(result);
                        } else {
                            erEe.setCurStatus("3");
                            erEe.setHisStatus(erEe.getHisStatus() + "_1_3");
                            erEe.setHisTime(erEe.getHisTime() + "_" + curTime + "_" + curTime);
                            flag = erEeService.eeErRq("eeRqOld", sendName, erSex, extraMsg, erEe, gradeId, sortNo);
                        }
                    }
                } else {
                    erEe = new ErEe();
                    erEe.setId(UUID.randomUUID().toString());
                    erEe.setExaminerId(targetId);
                    erEe.setExamineeId(userId);
                    erEe.setCurStatus("1");
                    erEe.setHisStatus("1");
                    erEe.setHisTime(curTime + "");
                    erEe.setModifyTime(new Date());
                    flag = erEeService.eeErRq("eeRqNoOld", sendName, erSex, extraMsg, erEe, gradeId, sortNo);
                }
                break;
            case "erRequest":
                example.or().andEqualTo("examinerId", userId).andEqualTo("examineeId", targetId).andLessThan("curStatus", "4");
                oldErEeList = erEeService.selectByExample(example);
                if (ToolUtil.isNotEmpty(oldErEeList)) {
                    if (oldErEeList.size() > 1) {
                        result.setMsg("关系异常，请联系管理员");
                        result.setLogMsg("存在相同的考生考官关系，考生：" + targetId + "考官：" + userId);
                        return JSONObject.fromObject(result);
                    } else {
                        erEe = oldErEeList.get(0);
                        if (ToolUtil.anyEqual(erEe.getCurStatus(), "2", "3")) {
                            result.setMsg("已邀请此考生");
                            return JSONObject.fromObject(result);
                        } else {
                            erEe.setCurStatus("3");
                            erEe.setHisStatus(erEe.getHisStatus() + "_2_3");
                            erEe.setHisTime(erEe.getHisTime() + "_" + curTime + "_" + curTime);
                            flag = erEeService.eeErRq("erRqOld", sendName, erSex, extraMsg, erEe, gradeId, sortNo);
                        }
                    }
                } else {
                    erEe = new ErEe();
                    erEe.setId(UUID.randomUUID().toString());
                    erEe.setExaminerId(userId);
                    erEe.setExamineeId(targetId);
                    erEe.setCurStatus("2");
                    erEe.setHisStatus("2");
                    erEe.setHisTime(curTime + "");
                    erEe.setModifyTime(new Date());
                    flag = erEeService.eeErRq("erRqNoOld", sendName, erSex, extraMsg, erEe, gradeId, sortNo);
                }
                break;
            default:
                result.setMsg("操作类型错误");
                result.setLogMsg("操作类型错误：" + operate + "操作人：" + userId);
                return JSONObject.fromObject(result);
        }

        result.setMsg("服务器维护中");
        switch (flag) {
            case "1":
                result.setMsg("操作成功");
                result.setStatus(201);
                break;
            case "2":
                result.setMsg("操作失败");
                break;
            case "3":
                result.setLogMsg("未根据" + targetId + "找到对应用户");
                break;
            case "4":
                result.setLogMsg("erErRq方法的reType入参错误（Service层）");
                break;
            default:
                result.setLogMsg(flag);
        }

        return JSONObject.fromObject(result);
    }

    /**
     * 解除考生考官关系
     */
    @RequestMapping(value = "/removeEeEr", method = RequestMethod.POST)
    @Privilege(methodName = "解除考生考官关系")
    @ResponseBody
    @ApiOperation(value = "解除考生考官关系", notes = "" +
            "入参说明：<br/>" +
            "erEeId：关系主键id（可选）<br/>" +
            "targetId：对方id（可选）<br/>" +
            "<span style='color:red'>上面两个可选的至少选择一个传入，都传入按照关系主键id处理</span><br/>" +
            "operate：操作（eeRefuse-考生拒绝邀请，erRefuse-考官拒绝申请，eeRemove-考生退出，erRemove-考官踢出）<br/>" +
            "出参说明：<br/>" +
            "201")
    public JSONObject removeEeEr(@RequestParam(value = "erEeId", required = false) String erEeId,
                                 @RequestParam(value = "targetId", required = false) String targetId,
                                 @RequestParam("operate") String operate,
                                 @RequestParam("token") String token) throws Exception {
        CommonResult<JSONObject> result = new CommonResult<>();
        result.setData(JSONObject.fromObject("{}"));
        result.setStatus(400);
        result.setMsg("系统繁忙");

        Map<String, Object> tokenMap = tokenService.getTokenMap(token);
        String sendName = (String) tokenMap.get("realName");//发送方姓名
        String userId = (String) tokenMap.get("id");//发送方姓名

        ErEe erEe;
        if (ToolUtil.isEmpty(erEeId)) {
            if (ToolUtil.isEmpty(targetId)) {
                result.setLogMsg("关系主键id和对方id至少传入一个");
                return JSONObject.fromObject(result);
            }
            Example example = new Example(ErEe.class);
            Example.Criteria criteria = example.createCriteria();
            boolean isEe = ToolUtil.anyEqual(operate, "eeRefuse", "eeRemove");//当前是否是考生操作
            if (isEe) {
                criteria.andEqualTo("examinerId", targetId)
                        .andEqualTo("examineeId", userId)
                        .andLessThan("curStatus", "4");
            } else {
                criteria.andEqualTo("examineeId", targetId)
                        .andEqualTo("examinerId", userId)
                        .andLessThan("curStatus", "4");
            }
            List<ErEe> list = erEeService.selectByExample(example);
            if (ToolUtil.isEmpty(list)) {
                if (isEe) {
                    result.setLogMsg("根据考生id：【" + userId + "】和考官id【" + targetId + "】没有找到关系");
                } else {
                    result.setLogMsg("根据考官id：【" + userId + "】和考生id【" + targetId + "】没有找到关系");
                }
                result.setMsg("操作失败");
                return JSONObject.fromObject(result);
            } else if (list.size() > 1) {
                if (isEe) {
                    result.setLogMsg("根据考生id：【" + userId + "】和考官id【" + targetId + "】找到“当前关系小于4的”大于一条");
                } else {
                    result.setLogMsg("根据考官id：【" + userId + "】和考生id【" + targetId + "】找到”当前关系小于4的“大于一条");
                }
                result.setMsg("操作失败");
                return JSONObject.fromObject(result);
            } else {
                erEe = list.get(0);
            }
        } else {
            erEe = erEeService.selectByKey(erEeId);
        }

        if (ToolUtil.isEmpty(erEe)) {
            result.setMsg("关系id错误");
            return JSONObject.fromObject(result);
        }
        if (Integer.parseInt(erEe.getCurStatus()) > 3) {
            result.setMsg("不存在该关系");
            return JSONObject.fromObject(result);
        }

        String resStr = erEeService.eeErRemove(operate, sendName, erEe);
        switch (resStr) {
            case "0":
                result.setMsg("操作失败");
                break;
            case "1":
                result.setMsg("操作成功");
                result.setStatus(201);
                break;
            default:
                result.setLogMsg(resStr);
        }

        return JSONObject.fromObject(result);
    }

    /**
     * 接受邀请或接受申请
     */
    @RequestMapping(value = "/acceptEeEr", method = RequestMethod.POST)
    @Privilege(methodName = "接受邀请或接受申请")
    @ResponseBody
    @ApiOperation(value = "接受邀请或接受申请", notes = "" +
            "入参说明：<br/>" +
            "erEeId：关系主键id<br/>" +
            "出参说明：<br/>" +
            "201")
    public JSONObject acceptEeEr(@RequestParam("erEeId") String erEeId,
                                 @RequestParam("token") String token) throws Exception {
        CommonResult<JSONObject> result = new CommonResult<>();
        result.setData(JSONObject.fromObject("{}"));
        result.setStatus(400);
        result.setMsg("系统繁忙");

        ErEe erEe = erEeService.selectByKey(erEeId);
        if (ToolUtil.isEmpty(erEe)) {
            result.setMsg("关系id错误");
            return JSONObject.fromObject(result);
        }
        if (Integer.parseInt(erEe.getCurStatus()) > 3) {
            result.setMsg("不存在该关系");
            return JSONObject.fromObject(result);
        }

        Map<String, Object> tokenMap = tokenService.getTokenMap(token);
        String sendName = (String) tokenMap.get("realName");//发送方姓名

        String resStr = erEeService.acceptEeEr(sendName, erEe);
        switch (resStr) {
            case "0":
                result.setMsg("操作失败");
                break;
            case "1":
                result.setMsg("操作成功");
                result.setStatus(201);
                break;
            default:
                result.setLogMsg(resStr);
        }

        return JSONObject.fromObject(result);
    }

    @RequestMapping(value = "/getErListByEe", method = RequestMethod.GET)
    @Privilege(methodName = "获取自己的考官列表", privilegeLevel = Privilege.EE_TYPE)
    @ResponseBody
    @ApiOperation(value = "获取自己的考官列表", notes = "" +
            "入参说明:<br/>" +
            "出参说明:<br/>" +
            "[" +
            "   {" +
            "       erId:考官id" +
            "       erName:考官姓名" +
            "   }" +
            "]")
    public JSONObject getErListByEe(@RequestParam("token") String token) {
        CommonResult<JSONArray> result = new CommonResult<>();
        result.setData(JSONArray.fromObject("[]"));
        result.setStatus(400);

        List<Map<String, Object>> erList = erEeService.getErListByEe(tokenService.getUserId(token));
        if (ToolUtil.isEmpty(erList)) {
            result.setMsg("查询成功，但没有数据");
        } else {
            result.setMsg("查询成功");
            result.setData(JSONArray.fromObject(erList));
            result.setStatus(200);
        }

        return JSONObject.fromObject(result);
    }

    @RequestMapping(value = "/getOtherErListByEe", method = RequestMethod.GET)
    @Privilege(methodName = "获取自己考官外的其它考官")
    @ResponseBody
    @ApiOperation(value = "获取自己考官外的其它考官", notes = "" +
            "入参说明:<br/>" +
            "出参说明:<br/>")
    public JSONObject getOtherErListByEe(@RequestParam("page") Integer page,
                                         @RequestParam("limit") Integer limit,
                                         @RequestParam(value = "searchErName", required = false) String searchErName,
                                         @RequestParam("token") String token) {

        FixPageInfo<Map<String, Object>> pageInfo = erEeService.getOtherErListByEe(page, limit, tokenService.getUserId(token), searchErName);
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

}
