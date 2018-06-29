package org.zgl.player;


import org.zgl.logic.hall.siginin.po.SQLSignInModel;
import org.zgl.logic.hall.weath.po.ResourceModel;
import org.zgl.logic.hall.weath.po.SQLWeathModel;
import org.zgl.logic.login.po.SQLUserBaseInfo;
import org.zgl.utils.DateUtils;

import java.util.List;

public class PlayerInfoDto implements IPlayer {

    private int scenesId;
    private int roomId;
    private int roomPosition;

    private int id;
    private long uid;
    private String account;
    private String username;
    private String headIcon;
    private String gender;
    private long gold;
    private long diamond;
    private long integral;
    private int vipLv;
    private String describe;
    private String gener;
    /**联系方式*/
    private String relation;
    /**地址*/
    private String site;
    /**战绩*/
    private String exploits;
    /**当前使用座驾*/
    private int nowUserAutos;
    /**座驾*/
    private List<ResourceModel> autos;
    /**礼物*/
    private List<ResourceModel> gifts;
    /**道具*/
    private List<ResourceModel> props;
    /**当天所输 / 赢的钱*/
    private long todayGetMoney;
    /**
     * 人品值
     */
    private long characterNum;
    /**在万人 天天 骰子场今天分别总的投了多少 在返利轮盘中使用*/
    private long toRoomBetAllNum;
    private long ahRoomBetAllNum;
    private long diceRoomBetAllNum;
    public PlayerInfoDto() {
    }

    public long getCharacterNum() {
        return characterNum;
    }

    public void setCharacterNum(long characterNum) {
        this.characterNum = characterNum;
    }

    public String getGener() {
        return gener;
    }

    public void setGener(String gener) {
        this.gener = gener;
    }

    public PlayerInfoDto(UserMap um) {
        SQLUserBaseInfo baseInfo = um.getBaseInfo();
        SQLWeathModel weath = um.getWeath();
        this.id = um.getId();
        this.uid = um.getUid();
        this.account = um.getAccount();
        this.gender = baseInfo.getGender();
        this.username = baseInfo.getUserName();
        this.headIcon = baseInfo.getHeadIcon();
        this.gender = baseInfo.getGender();
        this.gold = weath.getGold();
        this.diamond = weath.getDiamond();
        this.integral = weath.getIntegral();
        this.vipLv = weath.getVipLv();
//        this.describe =
        this.relation = baseInfo.getRelation();
//        this.site =
        //TODO...战绩
//        this.exploits =
        this.characterNum = weath.getCharacterNum();
        this.nowUserAutos = weath.getAuto();
        this.autos = weath.getAutos();
        this.gifts = weath.getGifts();
        this.props = weath.getProps();
    }
    public void infoToWeath(UserMap userMap){
        SQLWeathModel weath = userMap.getWeath();
        weath.setGold(this.gold);
        weath.setVipLv(this.vipLv);
        weath.setDiamond(this.diamond);
        weath.setAuto(this.nowUserAutos);
        weath.setAutos(this.autos);
        weath.setGifts(this.gifts);
        weath.setIntegral(this.integral);
        weath.setProps(this.props);
        SQLSignInModel signIn = userMap.getSignIn();
        signIn.setRoomTime(DateUtils.currentDay());
        signIn.addAhRoomBetAllNum(this.ahRoomBetAllNum);
        signIn.addToRoomBetAllNum(this.toRoomBetAllNum);
        signIn.addDiceRoomBetAllNum(this.diceRoomBetAllNum);
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public long getUid() {
        return uid;
    }

    public long getToRoomBetAllNum() {
        return toRoomBetAllNum;
    }

    public void setToRoomBetAllNum(long toRoomBetAllNum) {
        this.toRoomBetAllNum = toRoomBetAllNum;
    }

    public long getAhRoomBetAllNum() {
        return ahRoomBetAllNum;
    }

    public void setAhRoomBetAllNum(long ahRoomBetAllNum) {
        this.ahRoomBetAllNum = ahRoomBetAllNum;
    }

    public long getDiceRoomBetAllNum() {
        return diceRoomBetAllNum;
    }

    public void setDiceRoomBetAllNum(long diceRoomBetAllNum) {
        this.diceRoomBetAllNum = diceRoomBetAllNum;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHeadIcon() {
        return headIcon;
    }

    public void setHeadIcon(String headIcon) {
        this.headIcon = headIcon;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public long getGold() {
        return gold;
    }

    public void setGold(long gold) {
        this.gold = gold;
    }

    public long getDiamond() {
        return diamond;
    }

    public void setDiamond(long diamond) {
        this.diamond = diamond;
    }

    public long getIntegral() {
        return integral;
    }

    public void setIntegral(long integral) {
        this.integral = integral;
    }

    public int getVipLv() {
        return vipLv;
    }

    public void setVipLv(int vipLv) {
        this.vipLv = vipLv;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getExploits() {
        return exploits;
    }

    public void setExploits(String exploits) {
        this.exploits = exploits;
    }

    public List<ResourceModel> getAutos() {
        return autos;
    }

    public void setAutos(List<ResourceModel> autos) {
        this.autos = autos;
    }

    public List<ResourceModel> getGifts() {
        return gifts;
    }

    public void setGifts(List<ResourceModel> gifts) {
        this.gifts = gifts;
    }

    public List<ResourceModel> getProps() {
        return props;
    }

    public void setProps(List<ResourceModel> props) {
        this.props = props;
    }

    public int getScenesId() {
        return scenesId;
    }

    public void setScenesId(int scenesId) {
        this.scenesId = scenesId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getRoomPosition() {
        return roomPosition;
    }

    public void setRoomPosition(int roomPosition) {
        this.roomPosition = roomPosition;
    }

    public int getNowUserAutos() {
        return nowUserAutos;
    }

    public void setNowUserAutos(int nowUserAutos) {
        this.nowUserAutos = nowUserAutos;
    }

    public long getTodayGetMoney() {
        return todayGetMoney;
    }

    public void setTodayGetMoney(long todayGetMoney) {
        this.todayGetMoney = todayGetMoney;
    }
}