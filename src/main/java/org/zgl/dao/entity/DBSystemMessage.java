package org.zgl.dao.entity;

/**
 * @作者： big
 * @创建时间： 2018/6/15
 * @文件描述：
 */
public class DBSystemMessage {
    private Integer id;
    private String generalize;
    private String bank;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGeneralize() {
        return generalize;
    }

    public void setGeneralize(String generalize) {
        this.generalize = generalize;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }
}
