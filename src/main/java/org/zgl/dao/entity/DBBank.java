package org.zgl.dao.entity;

/**
 * @作者： big
 * @创建时间： 2018/6/15
 * @文件描述：
 */
public class DBBank {
    private Integer id;
    private Long uid;
    private Long gold;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getGold() {
        return gold;
    }

    public void setGold(Long gold) {
        this.gold = gold;
    }
}
