package org.zgl.bank;

import org.zgl.utils.builder_clazz.ann.Protostuff;

/**
 * @作者： big
 * @创建时间： 2018/6/15
 * @文件描述：
 */
@Protostuff
public class BankDto {
    private long gold;

    public BankDto() {
    }

    public BankDto(long gold) {
        this.gold = gold;
    }

    public long getGold() {
        return gold;
    }

    public void setGold(long gold) {
        this.gold = gold;
    }
}
