package org.zgl.dao.entity;

import java.sql.Date;

/**
 * @作者： big
 * @创建时间： 2018/6/16
 * @文件描述：
 */
public class DBGeneralize {
    private Integer id;
    private Integer number;
    private Long selfUid;
    private Long TargetUid;
    private Long award;
    private Long allAward;
    private String targetUserName;
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public Long getAllAward() {
        return allAward;
    }

    public void setAllAward(Long allAward) {
        this.allAward = allAward;
    }

    public String getTargetUserName() {
        return targetUserName;
    }

    public void setTargetUserName(String targetUserName) {
        this.targetUserName = targetUserName;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Long getSelfUid() {
        return selfUid;
    }

    public void setSelfUid(Long selfUid) {
        this.selfUid = selfUid;
    }

    public Long getTargetUid() {
        return TargetUid;
    }

    public void setTargetUid(Long targetUid) {
        TargetUid = targetUid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Long getAward() {
        return award;
    }

    public void setAward(Long award) {
        this.award = award;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
