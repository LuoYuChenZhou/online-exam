package com.lycz.service.user.impl;

import com.github.pagehelper.PageHelper;
import com.lycz.configAndDesign.FixPageInfo;
import com.lycz.configAndDesign.ToolUtil;
import com.lycz.dao.ErEeMapper;
import com.lycz.model.*;
import com.lycz.service.base.CommonService;
import com.lycz.service.base.SysDictService;
import com.lycz.service.base.SysMsgService;
import com.lycz.service.base.impl.BaseServiceTk;
import com.lycz.service.grade.EeGradeService;
import com.lycz.service.user.ErEeService;
import com.lycz.service.user.ExaminerService;
import com.lycz.service.user.VUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.*;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ErEeServiceImpl extends BaseServiceTk<ErEe> implements ErEeService {

    @Resource
    private ErEeMapper erEeMapper;
    @Resource
    private EeGradeService eeGradeService;
    @Resource
    private CommonService commonService;
    @Resource
    private SysDictService sysDictService;
    @Resource
    private SysMsgService sysMsgService;
    @Resource
    private VUserService vUserService;

    @Override
    public FixPageInfo<Map<String, Object>> getExamineeNoRelation(String searchString, Integer page, Integer limit, String userId) {
        PageHelper.startPage(page, limit);
        List<Map<String, Object>> eeList = erEeMapper.getExamineeNoRelation(searchString, userId);
        if (ToolUtil.isEmpty(eeList)) {
            return null;
        } else {
            return new FixPageInfo<>(eeList);
        }
    }

    @Override
    public String eeErRq(String reType, String sendName, String erSex, String ExtraMsg, ErEe erEe, String gradeId, Integer sortNo) throws Exception {

        ExtraMsg = null == ExtraMsg ? "" : "<br/>附加说明：" + ExtraMsg;
        String dictId;
        boolean flag;
        SysMsg sysMsg = new SysMsg();
        sysMsg.setId(UUID.randomUUID().toString());
        sysMsg.setStatus("0");
        sysMsg.setCreateTime(new Date());

        switch (reType) {
            case "eeRqNoOld":
                dictId = sysDictService.selectIdByCodeValue("msgType", "MT_EE_RQ");
                if (ToolUtil.isEmpty(dictId)) {
                    return "code为msgType，value为MT_EE_RQ的字典未找到";
                }
                sysMsg.setSendId(erEe.getExamineeId());
                sysMsg.setReceiveId(erEe.getExaminerId());
                sysMsg.setMsg("考生”" + sendName + "“申请成为您的考生" + ExtraMsg);
                sysMsg.setMsgType(dictId);
                flag = sysMsgService.insertSelective(sysMsg) > 0 && insertSelective(erEe) > 0;
                break;
            case "eeRqOld":
                dictId = sysDictService.selectIdByCodeValue("msgType", "MT_EE_RQ");
                if (ToolUtil.isEmpty(dictId)) {
                    return "code为msgType，value为MT_EE_RQ的字典未找到";
                }
                VUser examiner = vUserService.selectByKey(erEe.getExaminerId());
                if (ToolUtil.isEmpty(examiner)) {
                    return "3";
                }
                sysMsg.setSendId(erEe.getExamineeId());
                sysMsg.setReceiveId(erEe.getExaminerId());
                sysMsg.setMsg("您对”" + sendName + "“的邀请已被接受");
                sysMsg.setMsgType(dictId);
                SysMsg sysMsg1 = sysMsg.clone();
                sysMsg1.setId(UUID.randomUUID().toString());
                sysMsg1.setSendId(sysMsg.getReceiveId());
                sysMsg1.setReceiveId(sysMsg.getSendId());
                sysMsg1.setMsgType("您对" + examiner.getUserName() + "的申请已被接受");
                flag = sysMsgService.insertSelective(sysMsg) > 0
                        && sysMsgService.insertSelective(sysMsg1) > 0
                        && updateByPrimaryKeySelective(erEe) > 0;
                break;
            case "erRqNoOld":
                dictId = sysDictService.selectIdByCodeValue("msgType", "MT_ER_RQ");
                if (ToolUtil.isEmpty(dictId)) {
                    return "code为msgType，value为MT_ER_RQ的字典未找到";
                }
                sysMsg.setSendId(erEe.getExaminerId());
                sysMsg.setReceiveId(erEe.getExamineeId());
                sysMsg.setMsg("考官”" + sendName + "“邀请您成为" + erSex + "的考生" + ExtraMsg);
                sysMsg.setMsgType(dictId);
                flag = sysMsgService.insertSelective(sysMsg) > 0 && insertSelective(erEe) > 0;

                if (flag && ToolUtil.isNotEmpty(gradeId) && !Objects.equals("noAddToGrade", gradeId)) {
                    //如果传入了班级id，则添加到考生班级关系表
                    String newId = UUID.randomUUID().toString();
                    sortNo = ToolUtil.isEmpty(sortNo) ? 1 : sortNo;
                    EeGrade eeGrade = new EeGrade();
                    eeGrade.setId(newId);
                    eeGrade.setGradeId(gradeId);
                    eeGrade.setSortNo(sortNo);
                    eeGrade.setEeId(erEe.getExamineeId());
                    eeGrade.setStatus("0");
                    flag = eeGradeService.insertSelective(eeGrade) > 0;

                    //排序
                    commonService.setSortNoByLineNum("ee_grade", newId, sortNo, 0, " grade_id = '" + gradeId + "' ");
                }
                break;
            case "erRqOld":
                dictId = sysDictService.selectIdByCodeValue("msgType", "MT_ER_RQ");
                if (ToolUtil.isEmpty(dictId)) {
                    return "code为msgType，value为MT_ER_RQ的字典未找到";
                }
                VUser examinee = vUserService.selectByKey(erEe.getExaminerId());
                if (ToolUtil.isEmpty(examinee)) {
                    return "3";
                }
                sysMsg.setSendId(erEe.getExamineeId());
                sysMsg.setReceiveId(erEe.getExaminerId());
                sysMsg.setMsg("您对”" + examinee.getUserName() + "“的邀请已被接受");
                sysMsg.setMsgType(dictId);
                SysMsg sysMsg2 = sysMsg.clone();
                sysMsg2.setId(UUID.randomUUID().toString());
                sysMsg2.setSendId(sysMsg.getReceiveId());
                sysMsg2.setReceiveId(sysMsg.getSendId());
                sysMsg2.setMsgType("您对”" + sendName + "“的申请已被接受");
                flag = sysMsgService.insertSelective(sysMsg) > 0
                        && sysMsgService.insertSelective(sysMsg2) > 0
                        && updateByPrimaryKeySelective(erEe) > 0;

                if (flag && ToolUtil.isNotEmpty(gradeId) && !Objects.equals("noAddToGrade", gradeId)) {
                    //如果传入了班级id，则添加到考生班级关系表
                    String newId = UUID.randomUUID().toString();
                    sortNo = ToolUtil.isEmpty(sortNo) ? 1 : sortNo;
                    EeGrade eeGrade = new EeGrade();
                    eeGrade.setId(newId);
                    eeGrade.setGradeId(gradeId);
                    eeGrade.setSortNo(sortNo);
                    eeGrade.setEeId(erEe.getExamineeId());
                    eeGrade.setStatus("1");
                    flag = eeGradeService.insertSelective(eeGrade) > 0;

                    //排序
                    commonService.setSortNoByLineNum("ee_grade", newId, sortNo, 0, " grade_id = '" + gradeId + "' ");
                }
                break;
            default:
                return "4";
        }

        if (flag) {
            return "1";
        } else {
            return "2";
        }

    }
}
