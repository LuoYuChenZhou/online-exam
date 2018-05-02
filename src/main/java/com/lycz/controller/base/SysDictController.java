package com.lycz.controller.base;

import com.lycz.configAndDesign.CommonResult;
import com.lycz.configAndDesign.FixPageInfo;
import com.lycz.configAndDesign.ToolUtil;
import com.lycz.configAndDesign.annotation.Privilege;
import com.lycz.model.SysDict;
import com.lycz.service.base.SysDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;

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

    @RequestMapping(value = "/getIdValueNameByCode", method = RequestMethod.GET)
    @Privilege(methodName = "根据字典code获取id、value和name")
    @ResponseBody
    @ApiOperation(value = "根据字典code获取id、value和name(状态为1的,不分页)", notes = "" +
            "入参说明:<br/>" +
            "dictCode:字典编码<br/>" +
            "出参说明:<br/>" +
            "\n" +
            "{\n" +
            "\n" +
            "    \"data\":[\n" +
            "        {\n" +
            "            \"dictName\":\"字典名\",\n" +
            "            \"dictValue\":\"字典值\",\n" +
            "            \"id\":\"字典id\",\n" +
            "        }\n" +
            "    ],\n" +
            "    \"logMsg\":\"\",\n" +
            "    \"msg\":\"查询成功\",\n" +
            "    \"status\":200\n" +
            "\n" +
            "}\n")
    public JSONObject getIdValueNameByCode(@RequestParam("dictCode") String dictCode,
                                           @RequestParam("token") String token) {
        CommonResult<JSONArray> result = new CommonResult<>();
        result.setData(JSONArray.fromObject("[]"));
        result.setStatus(400);

        Example example = new Example(SysDict.class);
        example.or().andEqualTo("dictCode", dictCode).andEqualTo("status", "1");
        List<SysDict> dictList = sysDictService.selectByExample(example);

        if (ToolUtil.isEmpty(dictList)) {
            result.setStatus(204);
            result.setMsg("查询结束，但没有结果");
        } else {
            result.setData(JSONArray.fromObject(dictList));
            result.setMsg("查询成功");
            result.setStatus(200);
        }

        return JSONObject.fromObject(result);
    }

    @RequestMapping(value = "/getDictListByNameValueCodeUpperId", method = RequestMethod.GET)
    @Privilege(methodName = "根据字典名、字典值、字典编码、父级id查询字典列表", privilegeLevel = Privilege.SYS_TYPE)
    @ResponseBody
    @ApiOperation(value = "根据字典名、字典值、字典编码、父级id查询字典列表", notes = "" +
            "入参说明:<br/>" +
            "searchString:查询字符（用于查询字典名、字典值或字典编码,可选）<br/>" +
            "upperId:父级字典id，必填<br/>" +
            "出参说明:<br/>" +
            "\n" +
            "{\n" +
            "\n" +
            "    \"data\":[\n" +
            "        {\n" +
            "            \"id\":\"字典id\",\n" +
            "            \"dictValue\":\"字典值\",\n" +
            "            \"dictCode\":\"字典编码\",\n" +
            "            \"dictName\":\"字典名\",\n" +
            "            \"upperId\":\"父级id\",\n" +
            "            \"status\":\"当前状态\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"code\":0,\n" +
            "    \"count\":9,\n" +
            "    \"limit\":1,\n" +
            "    \"msg\":\"查询成功\",\n" +
            "    \"page\":1\n" +
            "\n" +
            "}\n")
    public JSONObject getDictListByNameValueCodeUpperId(@RequestParam(value = "searchString", required = false) String searchString,
                                                        @RequestParam("upperId") String upperId,
                                                        @RequestParam("page") Integer page,
                                                        @RequestParam("limit") Integer limit,
                                                        @RequestParam("token") String token) {

        FixPageInfo<Map<String, Object>> pageInfo = sysDictService.getDictListByNameValueCodeUpperId(searchString, upperId, page, limit);
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

    @RequestMapping(value = "/getUpperZeroDictList", method = RequestMethod.GET)
    @Privilege(methodName = "获取所有顶级菜单（upper_id为0的），不分页用于下拉")
    @ResponseBody
    @ApiOperation(value = "获取所有顶级菜单（upper_id为0的），不分页用于下拉", notes = "" +
            "入参说明:<br/>" +
            "verifyDictId:校验用id，可选，如果此id对应的是顶级字典，返回203<br/>" +
            "token:<br/>" +
            "出参说明:<br/>" +
            "\n" +
            "{\n" +
            "\n" +
            "    \"data\":[\n" +
            "        {\n" +
            "            \"id\":\"字典id\",\n" +
            "            \"dictValue\":\"字典值\",\n" +
            "            \"dictCode\":\"字典编码\",\n" +
            "            \"dictName\":\"字典名\",\n" +
            "            \"upperId\":\"父级id\",\n" +
            "            \"status\":\"当前状态\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"msg\":\"查询成功\",\n" +
            "    \"status\":200\n" +
            "\n" +
            "}\n")
    public JSONObject getUpperZeroDictList(@RequestParam(value = "verifyDictId", required = false) String verifyDictId,
                                           @RequestParam("token") String token) {
        CommonResult<JSONArray> result = new CommonResult<>();
        result.setData(JSONArray.fromObject("[]"));

        //如果用于校验的字典id对应的字典存在子级字典，直接返回203
        if (ToolUtil.isNotEmpty(verifyDictId) && sysDictService.sonDictExist(verifyDictId)) {
            result.setMsg("存在子级字典，不显示下拉");
            result.setStatus(203);
            return JSONObject.fromObject(result);
        }

        Example example = new Example(SysDict.class);
        example.or().andEqualTo("upperId", "0").andNotEqualTo("status", "4");

        List<SysDict> dictList = sysDictService.selectByExample(example);
        if (ToolUtil.isEmpty(dictList)) {
            result.setStatus(204);
            result.setMsg("查询结束，但没有数据");
        } else {
            result.setStatus(200);
            result.setMsg("查询成功");
            result.setData(JSONArray.fromObject(dictList));
        }

        return JSONObject.fromObject(result);
    }

    @RequestMapping(value = "/addDict", method = RequestMethod.POST)
    @Privilege(methodName = "添加字典", privilegeLevel = Privilege.SYS_TYPE)
    @ResponseBody
    @ApiOperation(value = "添加字典", notes = "" +
            "入参说明:<br/>" +
            "dictName:字典名<br/>" +
            "dictCode:字典编码<br/>" +
            "dictValue:字典值<br/>" +
            "upperId:父级id（0-顶级字典）<br/>" +
            "出参说明:<br/>" +
            "201")
    public JSONObject addDict(@Valid SysDict sysDict, BindingResult bindingResult,
                              @RequestParam("token") String token) {
        CommonResult<JSONObject> result = new CommonResult<>();
        result.setData(JSONObject.fromObject("{}"));
        result.setStatus(400);

        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            result.setMsg("系统繁忙");
            result.setLogMsg(errorList.get(errorList.size() - 1).getDefaultMessage());
            return JSONObject.fromObject(result);
        }

        sysDict.setId(UUID.randomUUID().toString());
        sysDict.setStatus("1");

        if (sysDictService.insertSelective(sysDict) > 0) {
            result.setMsg("添加成功");
            result.setStatus(201);
        } else {
            result.setMsg("添加失败");
        }

        return JSONObject.fromObject(result);
    }

    @RequestMapping(value = "/updateDict", method = RequestMethod.POST)
    @Privilege(methodName = "修改字典信息", privilegeLevel = Privilege.SYS_TYPE)
    @ResponseBody
    @ApiOperation(value = "修改字典信息(只有两级字典，所以顶级id不允许修改父级id，不修改status)", notes = "" +
            "入参说明:<br/>" +
            "id:字典id<br/>" +
            "dictName:字典名<br/>" +
            "dictCode:字典编码<br/>" +
            "dictValue:字典值<br/>" +
            "upperId:父级id（0-顶级字典）<br/>" +
            "出参说明:<br/>" +
            "201")
    public JSONObject updateDict(SysDict sysDict, @RequestParam("token") String token) {
        CommonResult<JSONObject> result = new CommonResult<>();
        result.setData(JSONObject.fromObject("{}"));
        result.setStatus(400);

        if (ToolUtil.isEmpty(sysDict.getId())) {
            result.setMsg("字典id不能为空");
            return JSONObject.fromObject(result);
        }

        if (sysDictService.sonDictExist(sysDict.getId())) {
            result.setMsg("有子级字典的字典不能修改父级id，其它信息已更新");
        }

        sysDict.setStatus(null);

        if (sysDictService.updateByPrimaryKeySelective(sysDict) > 0) {
            if (ToolUtil.isEmpty(result.getMsg())) result.setMsg("修改成功");
            result.setStatus(201);
        } else {
            result.setMsg("修改失败");
        }

        return JSONObject.fromObject(result);
    }

    @RequestMapping(value = "/modifyDictStatus", method = RequestMethod.POST)
    @Privilege(methodName = "修改字典状态", privilegeLevel = Privilege.SYS_TYPE)
    @ResponseBody
    @ApiOperation(value = "修改字典状态（删除状态不能修改）", notes = "" +
            "入参说明:<br/>" +
            "dictId:字典id<br/>" +
            "targetStatus:目标状态，传string型数字（0-禁用，1-启用，4-删除）<br/>" +
            "出参说明:<br/>" +
            "201")
    public JSONObject modifyDictStatus(@RequestParam("dictId") String dictId,
                                       @RequestParam("targetStatus") String targetStatus,
                                       @RequestParam("token") String token) {
        CommonResult<JSONObject> result = new CommonResult<>();
        result.setData(JSONObject.fromObject("{}"));
        result.setStatus(400);

        if (!ToolUtil.anyEqual(targetStatus, "0", "1", "4")) {
            result.setMsg("目标状态错误");
            return JSONObject.fromObject(result);
        }

        SysDict sysDict = sysDictService.selectByKey(dictId);
        if (ToolUtil.isEmpty(sysDict)) {
            result.setMsg("字典id错误");
            return JSONObject.fromObject(result);
        }

        if (Objects.equals("4", sysDict.getStatus())) {
            result.setMsg("字典已删除");
            return JSONObject.fromObject(result);
        }

        //查询是否存在子级字典，存在不能删除
        if ("4".equals(targetStatus) && sysDictService.sonDictExist(dictId)) {
            result.setMsg("请先删除子级字典");
            return JSONObject.fromObject(result);
        }

        SysDict sysDict1 = new SysDict();
        sysDict1.setId(dictId);
        sysDict1.setStatus(targetStatus);

        if (sysDictService.updateByPrimaryKeySelective(sysDict1) > 0) {
            result.setMsg("操作成功");
            result.setStatus(201);
        } else {
            result.setMsg("操作失败");
        }

        return JSONObject.fromObject(result);
    }

}
