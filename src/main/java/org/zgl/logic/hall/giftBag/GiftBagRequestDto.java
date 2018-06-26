package org.zgl.logic.hall.giftBag;

import org.zgl.utils.builder_clazz.ann.Protostuff;

/**
 * @作者： big
 * @创建时间： 2018/6/25
 * @文件描述：
 */
@Protostuff
public class GiftBagRequestDto {
    private boolean hasGet;
    private boolean canGet;
    public GiftBagRequestDto() {
    }

    public GiftBagRequestDto(boolean hasGet, boolean canGet) {
        this.hasGet = hasGet;
        this.canGet = canGet;
    }

    public boolean isCanGet() {
        return canGet;
    }

    public void setCanGet(boolean canGet) {
        this.canGet = canGet;
    }

    public boolean isHasGet() {
        return hasGet;
    }

    public void setHasGet(boolean hasGet) {
        this.hasGet = hasGet;
    }
}
