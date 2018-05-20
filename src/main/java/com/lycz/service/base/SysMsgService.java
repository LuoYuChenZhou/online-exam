package com.lycz.service.base;

import com.lycz.model.SysMsg;

import java.util.List;
import java.util.Map;

public interface SysMsgService extends IBaseServiceTk<SysMsg> {

    /**
     * 添加一条系统信息
     *
     * @param sendId    发送方id
     * @param receiveId 接受方id
     * @param msg       信息内容
     * @param msgType   信息类型，传入字典内code为msgType的某个字典的字典值
     */
    boolean addMsg(String sendId, String receiveId, String msg, String msgType) throws Exception;

    /**
     * 考官向所有考生发送消息
     *
     * @param sendId  考官id
     * @param msg     信息内容
     * @param msgType 信息类型，传入字典内code为msgType的某个字典的字典值
     */
    boolean addEeMsg(String sendId, String msg, String msgType) throws Exception;

    List<Map<String, Object>> getMsgListByTimeUser(String searchTime, String userId);
}
