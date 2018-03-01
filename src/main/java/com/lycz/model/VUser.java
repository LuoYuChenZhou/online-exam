package com.lycz.model;

import javax.persistence.*;

@Table(name = "v_user")
public class VUser {
    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "STATUS")
    private String status;

    /**
     * @return user_id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return user_ame
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userAme
     */
    public void setUserName(String userAme) {
        this.userName = userAme;
    }

    /**
     * @return STATUS
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }
}