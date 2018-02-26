package com.lycz.controller.grade;

import com.lycz.controller.common.FixPageInfo;
import com.lycz.controller.common.ToolUtil;
import com.lycz.controller.common.annotation.Privilege;
import com.lycz.service.base.SysLogService;
import com.lycz.service.base.TokenService;
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
 * @data 2018/2/26 9:00
 */
@Controller
@RequestMapping(value = "SysLog")
public class GradeController {

    @Resource
    private SysLogService sysLogService;
    @Resource
    private TokenService tokenService;

//    /**
//     * @param page  当前页
//     * @param limit 每页条数
//     */
//    @RequestMapping(value = "/getGradeListByNameUser", method = RequestMethod.GET)
//    @Privilege(methodName = "根据班级名称和当前用户搜索班级列表", privilegeLevel = 3)
//    @ResponseBody
//    public JSONObject getGradeListByNameUser(@RequestParam(value = "searchGradeName", required = false) String searchGradeName,
//                                    @RequestParam("page") Integer page, @RequestParam("limit") Integer limit, @RequestParam("token") String token) {
//
//        String userId = tokenService.
//
//        FixPageInfo<Map<String, Object>> logInfo = sysLogService.getLogListBySearch(startDate, endDate, searchLevel, searchTitle, page, limit);
//        if (logInfo == null) {
//            logInfo = new FixPageInfo<>();
//            logInfo.setMsg("查询结束，但没有数据");
//            logInfo.setCode(204);
//        } else {
//            logInfo.setMsg("查询成功");
//            logInfo.setCode(0);
//        }
//
//        return JSONObject.fromObject(logInfo);
//    }
}

