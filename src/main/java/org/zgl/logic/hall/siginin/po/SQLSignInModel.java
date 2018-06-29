package org.zgl.logic.hall.siginin.po;


import org.zgl.utils.DateUtils;

public class SQLSignInModel {
    /**签到第几天*/
    private int signDay;
    /**上次签到时间*/
    private int signInTime;
    /**今天是否已经抽奖*/
    private int dialTime;
//
//    private int onlineAwardDay;
//    /**在线抽奖次数*/
//    private int onlineAwardNum;
//    /**在线抽奖上次抽奖时间*/
//    private long onlineAwardTimer;

    /**首充奖励领取第几天*/
    private int firstBayDay;
    /**首充奖励当天是否已经领取*/
    private boolean hasFirstBay;

    /**幸运大转盘剩余次数*/
    private int dialNum;
    /**在万人 天天 骰子场今天分别总的投了多少 在返利轮盘中使用*/
    private int roomTime;
    private long toRoomBetAllNum;
    private long ahRoomBetAllNum;
    private long diceRoomBetAllNum;
    private long topUpAllNum;
    public SQLSignInModel() {
    }
    public int getSignDay() {
        return signDay;
    }

    public int getRoomTime() {
        return roomTime;
    }

    public int getDialTime() {
        return dialTime;
    }

    public void setDialTime(int dialTime) {
        this.dialTime = dialTime;
    }

    public void setRoomTime(int roomTimete) {
        if(this.roomTime != DateUtils.currentDay()){
            this.roomTime = DateUtils.currentDay();
            this.topUpAllNum = 0;
            this.ahRoomBetAllNum = 0;
            this.toRoomBetAllNum = 0;
            this.diceRoomBetAllNum = 0;
            this.roomTime = roomTimete;
        }
    }

    public void setSignDay(int signDay) {
        this.signDay = signDay;
    }

    public int getSignInTime() {
        return signInTime;
    }

//    public int getOnlineAwardNum() {
//        return onlineAwardNum;
//    }
//
//    public void setOnlineAwardNum(int onlineAwardNum) {
//        this.onlineAwardNum = onlineAwardNum;
//    }
//
//    public long getOnlineAwardTimer() {
//        return onlineAwardTimer;
//    }
//
//    public void setOnlineAwardTimer(long onlineAwardTimer) {
//        this.onlineAwardTimer = onlineAwardTimer;
//    }

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

    public long getTopUpAllNum() {
        return topUpAllNum;
    }

    public void setTopUpAllNum(long topUpAllNum) {
        this.topUpAllNum = topUpAllNum;
    }

    public void setSignInTime(int signInTime) {
        this.signInTime = signInTime;
    }

    public int getFirstBayDay() {
        return firstBayDay;
    }

    public void setFirstBayDay(int firstBayDay) {
        this.firstBayDay = firstBayDay;
    }

    public boolean isHasFirstBay() {
        return hasFirstBay;
    }

    public void setHasFirstBay(boolean hasFirstBay) {
        this.hasFirstBay = hasFirstBay;
    }

    public int getDialNum() {
        return dialNum;
    }

    public void setDialNum(int dialNum) {
        this.dialNum = dialNum;
    }
    public void addDialNum(){
        this.dialNum++;
    }
    public void addToRoomBetAllNum(long num){
        this.toRoomBetAllNum += num;
    }
    public void addAhRoomBetAllNum(long num){
        this.ahRoomBetAllNum+= num;
    }
    public void addDiceRoomBetAllNum(long num){
        this.diceRoomBetAllNum += num;
    }
    public void addTopUpNum(int num){
        topUpAllNum += num;
    }

    /**
     * 能否领取奖励
     * @return
     */
    public boolean canAward(){
        if(roomTime != DateUtils.currentDay()){
            this.roomTime = DateUtils.currentDay();
            this.topUpAllNum = 0;
            this.ahRoomBetAllNum = 0;
            this.toRoomBetAllNum = 0;
            this.diceRoomBetAllNum = 0;
        }
        if(dialNum > 0) {
            if (topUpAllNum / dialNum >= 20)
                return true;
            if (toRoomBetAllNum / dialNum >= 5000 &&
                    ahRoomBetAllNum / dialNum >= 5000 &&
                    diceRoomBetAllNum / dialNum >= 5000) {
                return true;
            }
        }else {
            if(topUpAllNum >= 20)
                return true;
            if(toRoomBetAllNum >= 5000 && ahRoomBetAllNum >= 5000 && diceRoomBetAllNum >= 5000)
                return true;
        }
        return false;
    }

    /**
     * 领取
     * @return
     */
    public void award(){
        dialNum++;
    }

    /**
     * 还可以领取的次数
     * @return
     */
    public int awardCount(){
        if(dialNum > 0) {
            int topUp = (int) ((topUpAllNum / dialNum) / 20);
            int[] roomNum = new int[3];
            roomNum[0] = (int) ((ahRoomBetAllNum / dialNum) / 5000);
            roomNum[1] = (int) ((diceRoomBetAllNum / dialNum) / 5000);
            roomNum[2] = (int) ((toRoomBetAllNum / dialNum) / 5000);
            return topUp + roomNum[2];
        }else if(dialNum == 0){
            int topUp = (int) (topUpAllNum/20);
            int[] roomNum = new int[3];
            roomNum[0] = (int) (ahRoomBetAllNum / 5000);
            roomNum[1] = (int) (diceRoomBetAllNum / 5000);
            roomNum[2] = (int) (toRoomBetAllNum / 5000);
            return topUp + roomNum[2];
        }
        return 0;
    }
}
