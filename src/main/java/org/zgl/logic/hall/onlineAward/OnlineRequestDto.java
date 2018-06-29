package org.zgl.logic.hall.onlineAward;

import org.zgl.utils.builder_clazz.ann.Protostuff;

/**
 * @作者： big
 * @创建时间： 2018/6/26
 * @文件描述：
 */
@Protostuff
public class OnlineRequestDto {
    private int canGetCount;//可以领取的次数
    private int getCounted;
    private long topUpNum;
    private long ahRoonBetNum;
    private long toRoomBetNum;
    private long diceRoomNum;
    private long betAllCount;
    public OnlineRequestDto() {
    }

    public int getCanGetCount() {
        return canGetCount;
    }

    public void setCanGetCount(int canGetCount) {
        this.canGetCount = canGetCount;
    }

    public int getGetCounted() {
        return getCounted;
    }

    public void setGetCounted(int getCounted) {
        this.getCounted = getCounted;
    }

    public long getBetAllCount() {
        return betAllCount;
    }

    public void setBetAllCount(long betAllCount) {
        this.betAllCount = betAllCount;
    }

    public long getTopUpNum() {
        return topUpNum;
    }

    public void setTopUpNum(long topUpNum) {
        this.topUpNum = topUpNum;
    }

    public long getAhRoonBetNum() {
        return ahRoonBetNum;
    }

    public void setAhRoonBetNum(long ahRoonBetNum) {
        this.ahRoonBetNum = ahRoonBetNum;
    }

    public long getToRoomBetNum() {
        return toRoomBetNum;
    }

    public void setToRoomBetNum(long toRoomBetNum) {
        this.toRoomBetNum = toRoomBetNum;
    }

    public long getDiceRoomNum() {
        return diceRoomNum;
    }

    public void setDiceRoomNum(long diceRoomNum) {
        this.diceRoomNum = diceRoomNum;
    }
}
