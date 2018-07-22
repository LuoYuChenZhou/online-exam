package com.lycz.service.user;

import com.github.pagehelper.PageInfo;
import com.lycz.configAndDesign.FixPageInfo;
import com.lycz.model.ErEe;
import com.lycz.service.base.IBaseServiceTk;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

public interface ErEeService extends IBaseServiceTk<ErEe> {

    PageInfo<Map<String, Object>> getInvitedList(Integer page, Integer limit, String userId);

    PageInfo<Map<String, Object>> getApplyList(Integer page, Integer limit, String userId);

    FixPageInfo<Map<String, Object>> getExamineeNoRelation(String searchString, Integer page, Integer limit, String userId);

    FixPageInfo<Map<String, Object>> getOtherErListByEe(Integer page, Integer limit, String eeId, String searchErName);

    /**
     * 申请考生考官关系
     *
     * @param reType   考生申请（之前考官有邀请-eeRqOld，之前没有邀请-eeRqNoOld）
     *                 、考官邀请（之前有考生申请-erRqOld，之前没有申请-erRqNoOld）
     * @param sendName 申请方姓名
     * @param ExtraMsg 附加说明
     * @param erEe     关系实体类
     * @param erSex    他或她（考官邀请传入）
     * @param gradeId  班级id（考官邀请传入）
     * @param sortNo   考生在班级中的排序号（考官邀请并选择添加进班级时传入）
     * @return （0-没找到字典，1-保存成功，2-保存失败，3-对方id错误，4-reType错误）
     */
    String eeErRq(String reType, String sendName, String erSex, String ExtraMsg, ErEe erEe, String gradeId, Integer sortNo) throws Exception;

    /**
     * @param rmType   操作（eeRefuse-考生拒绝邀请，erRefuse-考官拒绝申请，eeRemove-考生退出，erRemove-考官踢出）
     * @param sendName 操作人姓名
     */
    String eeErRemove(String rmType, String sendName, ErEe erEe) throws Exception;

    String acceptEeEr(String sendName, ErEe erEe) throws Exception;

    List<Map<String, Object>> getErListByEe(String eeId);

    /**
     * 根据考生id获取考官id列表
     */
    List<String> getErIdListByEeId(String eeId);
}
