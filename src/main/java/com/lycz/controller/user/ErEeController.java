package com.lycz.controller.user;

import com.lycz.configAndDesign.CommonResult;
import com.lycz.configAndDesign.FixPageInfo;
import com.lycz.configAndDesign.ToolUtil;
import com.lycz.configAndDesign.annotation.Privilege;
import com.lycz.model.ErEe;
import com.lycz.service.base.TokenService;
import com.lycz.service.user.ErEeService;
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
                                  @RequestParam(value = "ExtraMsg", required = false) String ExtraMsg,
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

        switch (operate) {
            case "eeRequest":
                //查找是否存在对方申请(只查询当前状态小于4的)
                Example example = new Example(ErEe.class);
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
                            flag = erEeService.eeErRq("eeRqOld", sendName, erSex, ExtraMsg, erEe, gradeId, sortNo);
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
                    flag = erEeService.eeErRq("eeRqNoOld", sendName, erSex, ExtraMsg, erEe, gradeId, sortNo);
                }
                break;
            case "erRequest":
                example = new Example(ErEe.class);
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
                            flag = erEeService.eeErRq("erRqOld", sendName, erSex, ExtraMsg, erEe, gradeId, sortNo);
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
                    flag = erEeService.eeErRq("erRqNoOld", sendName, erSex, ExtraMsg, erEe, gradeId, sortNo);
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
     * 解除考生考官关系（正常返回值有200和201两种）
     */
    @RequestMapping(value = "/removeEeEr", method = RequestMethod.POST)
    @Privilege(methodName = "解除考生考官关系")
    @ResponseBody
    @ApiOperation(value = "解除考生考官关系", notes = "" +
            "入参说明：<br/>" +
            "erEeId：关系主键id<br/>" +
            "operate：操作（eeRemove-考生退出，erRemove-考官踢出）<br/>" +
            "出参说明：<br/>" +
            "201")
    public JSONObject removeEeEr(@RequestParam("erEeId") String erEeId,
                                 @RequestParam("operate") String operate,
                                 @RequestParam("token") String token) {
        CommonResult<JSONObject> result = new CommonResult<>();
        result.setData(JSONObject.fromObject("{}"));
        result.setStatus(400);

        long curTime = System.currentTimeMillis();
        boolean flag;

        ErEe erEe = erEeService.selectByKey(erEeId);
        if (ToolUtil.isEmpty(erEe)) {
            result.setMsg("关系id错误");
            return JSONObject.fromObject(result);
        }
        if (Integer.parseInt(erEe.getCurStatus()) > 3) {
            result.setMsg("关系已解除");
            result.setStatus(200);
            return JSONObject.fromObject(result);
        }
        switch (operate) {
            case "eeRemove":
                erEe.setCurStatus("4");
                erEe.setHisStatus(erEe.getHisStatus() + "_4");
                erEe.setHisTime(erEe.getHisTime() + "_" + curTime);
                flag = erEeService.updateByPrimaryKeySelective(erEe) > 0;
                break;
            case "erRemove":
                erEe.setCurStatus("5");
                erEe.setHisStatus(erEe.getHisStatus() + "_5");
                erEe.setHisTime(erEe.getHisTime() + "_" + curTime);
                flag = erEeService.updateByPrimaryKeySelective(erEe) > 0;
                break;
            default:
                result.setMsg("操作类型错误");
                result.setLogMsg("操作类型错误：" + operate + "操作人：" + tokenService.getUserId(token));
                return JSONObject.fromObject(result);
        }

        if (flag) {
            result.setMsg("已解除关系");
            result.setStatus(201);
        } else {
            result.setMsg("操作失败");
        }

        return JSONObject.fromObject(result);
    }

}
