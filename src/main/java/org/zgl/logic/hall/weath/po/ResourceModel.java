package org.zgl.logic.hall.weath.po;


import org.zgl.utils.DateUtils;
import org.zgl.utils.builder_clazz.ann.Protostuff;

import java.util.Objects;

@Protostuff
public class ResourceModel {
    /**资源id*/
    private int id;
    /**类型 道具、座驾、道具 ：对应商城id*/
    private int type;
    /**拥有数量*/
    private int count;
    /**购买时间*/
    private long createTime;
    public ResourceModel() {
    }

    public ResourceModel(int id, int type, int count) {
        this.id = id;
        this.type = type;
        this.count = count;
        this.createTime = DateUtils.currentTime();
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResourceModel that = (ResourceModel) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
