package org.zgl.logic.hall.ranking;

import org.zgl.logic.hall.weath.po.SQLWeathModel;
import org.zgl.logic.login.po.SQLUserBaseInfo;
import org.zgl.player.UserMap;
import org.zgl.utils.builder_clazz.ann.Protostuff;

@Protostuff
public class RankingGoldPlayerDto {
    private int id;
    private long uid;
    private String userName;
    private String headIcon;
    private int vipLv;
    private int autoId;
    private long goldNum;
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHeadIcon() {
        return headIcon;
    }

    public void setHeadIcon(String headIcon) {
        this.headIcon = headIcon;
    }

    public int getVipLv() {
        return vipLv;
    }

    public void setVipLv(int vipLv) {
        this.vipLv = vipLv;
    }

    public int getAutoId() {
        return autoId;
    }

    public void setAutoId(int autoId) {
        this.autoId = autoId;
    }

    public long getGoldNum() {
        return goldNum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public void setGoldNum(long goldNum) {
        this.goldNum = goldNum;
    }
    public RankingGoldPlayerDto dto(UserMap userMap){
        this.uid = userMap.getUid();
        SQLWeathModel weath = userMap.getWeath();
        this.goldNum = weath.getGold();
        this.autoId = weath.getAuto();
        this.vipLv = weath.getVipLv();

        SQLUserBaseInfo base = userMap.getBaseInfo();
        this.headIcon = base.getHeadIcon();
        this.userName = base.getUserName();
        this.id = userMap.getId();
        return this;
    }
}
