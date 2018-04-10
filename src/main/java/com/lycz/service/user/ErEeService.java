package com.lycz.service.user;

import com.lycz.configAndDesign.FixPageInfo;
import com.lycz.model.ErEe;
import com.lycz.service.base.IBaseServiceTk;

import java.util.Map;

public interface ErEeService extends IBaseServiceTk<ErEe> {

    FixPageInfo<Map<String, Object>> getExamineeNoRelation(String searchString, Integer page, Integer limit, String userId);

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
}
