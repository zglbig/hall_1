package org.zgl.logic.hall.frineds.dto;


import org.zgl.utils.builder_clazz.ann.Protostuff;

@Protostuff
public class FriendBaseInfoDto {
    private long uid;
    private String userName;
    private int vipLv;
    private String gender;
    private String signAture;
    private String headIcn;
    private long blood;
    private boolean hasOnline;
    private int scenesId;

    public String getHeadIcn() {
        return headIcn;
    }

    public void setHeadIcn(String headIcn) {
        this.headIcn = headIcn;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getVipLv() {
        return vipLv;
    }

    public void setVipLv(int vipLv) {
        this.vipLv = vipLv;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSignAture() {
        return signAture;
    }

    public void setSignAture(String signAture) {
        this.signAture = signAture;
    }

    public long getBlood() {
        return blood;
    }

    public boolean isHasOnline() {
        return hasOnline;
    }

    public void setHasOnline(boolean hasOnline) {
        this.hasOnline = hasOnline;
    }

    public void setBlood(long blood) {
        this.blood = blood;
    }

    public int getScenesId() {
        return scenesId;
    }

    public void setScenesId(int scenesId) {
        this.scenesId = scenesId;
    }
}
