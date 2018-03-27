package com.lycz.controller.base;

import com.lycz.configAndDesign.FixPageInfo;
import com.lycz.configAndDesign.ToolUtil;
import com.lycz.configAndDesign.annotation.Privilege;
import com.lycz.service.base.SysLogService;
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
import java.util.Date;
import java.util.Map;

/**
 * @author lizhenqing
 * @version 1.0
 * @data 2018/2/23 16:45
 */
@Controller
@RequestMapping(value = "SysLog")
public class SysLogController {

    @Resource
    private SysLogService sysLogService;

    /**
     * @param page  当前页
     * @param limit 每页条数
     */
    @RequestMapping(value = "/getSysLogList", method = RequestMethod.GET)
    @Privilege(methodName = "获取日志列表", privilegeLevel = Privilege.SYS_TYPE)
    @ResponseBody
    public JSONObject getSysLogList(@RequestParam(value = "searchStartTime", required = false) String searchStartTime,
                                    @RequestParam(value = "searchEndTime", required = false) String searchEndTime,
                                    @RequestParam(value = "searchLevel", required = false) String searchLevel,
                                    @RequestParam(value = "searchTitle", required = false) String searchTitle,
                                    @RequestParam("page") Integer page, @RequestParam("limit") Integer limit, @RequestParam("token") String token) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = ToolUtil.isEmpty(searchStartTime) ? null : df.parse(searchStartTime);
            endDate = ToolUtil.isEmpty(searchEndTime) ? null : df.parse(searchEndTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        FixPageInfo<Map<String, Object>> logInfo = sysLogService.getLogListBySearch(startDate, endDate, searchLevel, searchTitle, page, limit);
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
}

