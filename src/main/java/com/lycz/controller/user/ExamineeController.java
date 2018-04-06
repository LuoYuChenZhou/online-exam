package com.lycz.controller.user;

import com.lycz.configAndDesign.FixPageInfo;
import com.lycz.configAndDesign.annotation.Privilege;
import com.lycz.service.user.ExamineeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author lizhenqing
 * @version 1.0
 * @data 2018/3/2 10:42
 */
@Controller
@RequestMapping("Examinee")
@Api(value = "Examinee", description = "考生相关api")
public class ExamineeController {
    @Resource
    private ExamineeService examineeService;


}
