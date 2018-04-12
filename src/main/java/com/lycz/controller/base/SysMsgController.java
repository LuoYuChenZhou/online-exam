package com.lycz.controller.base;

import com.github.pagehelper.PageInfo;
import com.lycz.configAndDesign.CommonResult;
import com.lycz.configAndDesign.FixPageInfo;
import com.lycz.configAndDesign.annotation.Privilege;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author lizhenqing
 * @version 1.0
 * @data 2018/4/10 15:24
 */
@Controller
@RequestMapping(value = "SysMsg")
@Api(value = "SysMsg", description = "系统日志相关api")
public class SysMsgController {


}
