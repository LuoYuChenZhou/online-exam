package com.lycz.controller.base;

import com.lycz.configAndDesign.CommonResult;
import com.lycz.configAndDesign.ToolUtil;
import com.lycz.configAndDesign.annotation.Privilege;
import com.lycz.model.SysDict;
import com.lycz.service.base.SysDictService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lizhenqing
 * @version 1.0
 * @data 2018/4/10 9:27
 */
@Controller
@RequestMapping(value = "SysDict")
@Api(value = "SysDict", description = "字典相关的Api")
public class SysDictController {

    @Resource
    private SysDictService sysDictService;

    @RequestMapping(value = "/getIdValueMapByCode", method = RequestMethod.GET)
    @Privilege(methodName = "根据字典code获取id和value的键值对")
    @ResponseBody
    @ApiOperation(value = "根据字典code获取id和value的键值对(状态不为4的)", notes = "" +
            "入参说明:<br/>" +
            "dictCode:字典编码<br/>" +
            "出参说明:<br/>" +
            "\n" +
            "{\n" +
            "\n" +
            "    \"data\":{\n" +
            "        \"字典id\":\"字典值\",\n" +
            "    },\n" +
            "    \"msg\":\"查询成功\",\n" +
            "    \"status\":200\n" +
            "\n" +
            "}\n")
    public JSONObject getIdValueMapByCode(@RequestParam("dictCode") String dictCode,
                                          @RequestParam("token") String token) {
        CommonResult<JSONObject> result = new CommonResult<>();
        result.setData(JSONObject.fromObject("{}"));
        result.setStatus(400);

        Example example = new Example(SysDict.class);
        example.or().andEqualTo("dictCode", dictCode).andNotEqualTo("status", "4");
        List<SysDict> dictList = sysDictService.selectByExample(example);

        if (ToolUtil.isEmpty(dictList)) {
            result.setStatus(204);
            result.setMsg("查询结束，但没有结果");
        } else {
            Map<String, String> tempMap = new HashMap<>();
            for (SysDict sd : dictList) {
                tempMap.put(sd.getId(), sd.getDictValue());
            }
            result.setData(JSONObject.fromObject(tempMap));
            result.setMsg("查询成功");
            result.setStatus(200);
        }

        return JSONObject.fromObject(result);
    }

}
