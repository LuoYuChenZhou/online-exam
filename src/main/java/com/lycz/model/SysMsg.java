package com.lycz.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_msg")
public class SysMsg implements Cloneable {
    @Id
    private String id;

    /**
     * 发送方id
     */
    @Column(name = "send_id")
    private String sendId;

    /**
     * 接收方id
     */
    @Column(name = "receive_id")
    private String receiveId;

    /**
     * 信息内容
     */
    private String msg;

    /**
     * 信息类型（对应字典code为msgType的字典值）
     */
    @Column(name = "msg_type")
    private String msgType;

    /**
     * 状态（0-未读，1-已读）
     */
    private String status;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取发送方id
     *
     * @return send_id - 发送方id
     */
    public String getSendId() {
        return sendId;
    }

    /**
     * 设置发送方id
     *
     * @param sendId 发送方id
     */
    public void setSendId(String sendId) {
        this.sendId = sendId;
    }

    /**
     * 获取接收方id
     *
     * @return receive_id - 接收方id
     */
    public String getReceiveId() {
        return receiveId;
    }

    /**
     * 设置接收方id
     *
     * @param receiveId 接收方id
     */
    public void setReceiveId(String receiveId) {
        this.receiveId = receiveId;
    }

    /**
     * 获取信息内容
     *
     * @return msg - 信息内容
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 设置信息内容
     *
     * @param msg 信息内容
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 获取信息类型（对应字典code为msgType的字典值）
     *
     * @return msg_type - 信息类型（对应字典code为msgType的字典值）
     */
    public String getMsgType() {
        return msgType;
    }

    /**
     * 设置信息类型（对应字典code为msgType的字典值）
     *
     * @param msgType 信息类型（对应字典code为msgType的字典值）
     */
    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    /**
     * 获取状态（0-未读，1-已读）
     *
     * @return status - 状态（0-未读，1-已读）
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态（0-未读，1-已读）
     *
     * @param status 状态（0-未读，1-已读）
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public SysMsg clone() throws CloneNotSupportedException {
        return (SysMsg) super.clone();
    }
}