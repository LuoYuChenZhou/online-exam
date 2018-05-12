package com.lycz.controller.base;

import com.github.pagehelper.PageInfo;
import com.lycz.configAndDesign.CommonResult;
import com.lycz.configAndDesign.FixPageInfo;
import com.lycz.configAndDesign.ToolUtil;
import com.lycz.configAndDesign.annotation.Privilege;
import com.lycz.service.base.SysMsgService;
import com.lycz.service.base.TokenService;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author lizhenqing
 * @version 1.0
 * @data 2018/4/10 15:24
 */
@Controller
@RequestMapping(value = "SysMsg")
@Api(value = "SysMsg", description = "系统信息相关api")
public class SysMsgController {

    @Resource
    private SysMsgService sysMsgService;
    @Resource
    private TokenService tokenService;

    @RequestMapping(value = "/getMsgListByTimeUser", method = RequestMethod.GET)
    @Privilege(methodName = "根据时间和接收方获取消息列表")
    @ResponseBody
    @ApiOperation(value = "根据时间和接收方获取消息列表", notes = "" +
            "入参说明:<br/>" +
            "出参说明:<br/>")
    public JSONObject getMsgListByTimeUser(@RequestParam(value = "searchTime", required = false) String searchTime,
                                           @RequestParam("token") String token) {
        CommonResult<JSONObject> result = new CommonResult<>();
        result.setData(JSONObject.fromObject("{}"));
        result.setStatus(400);

        List<Map<String, Object>> msgList = sysMsgService.getMsgListByTimeUser(searchTime, tokenService.getUserId(token));

        if (ToolUtil.isEmpty(msgList)) {
            result.setStatus(204);
            result.setMsg("查询结束，但没有数据");
        } else {
            Map<String, List<Map<String, Object>>> finalMap = new HashMap<>();
            for (Map<String, Object> m : msgList) {
                List<Map<String, Object>> tempList;
                if (ToolUtil.isEmpty(finalMap.get(m.get("createDate")))) {
                    tempList = new ArrayList<>();
                } else {
                    tempList = finalMap.get(m.get("createDate"));
                }
                m.put("msg", ((String) m.get("msg")).replaceAll("<br/>", "&nbsp;&nbsp;&nbsp;"));
                tempList.add(m);
                finalMap.put((String) m.get("createDate"), tempList);
            }

            result.setStatus(200);
            result.setData(JSONObject.fromObject(finalMap));
        }

        return JSONObject.fromObject(result);
    }
}
