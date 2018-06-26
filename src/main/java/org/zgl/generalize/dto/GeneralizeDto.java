package org.zgl.generalize.dto;

import org.zgl.utils.builder_clazz.ann.Protostuff;

/**
 * @作者： big
 * @创建时间： 2018/6/16
 * @文件描述：
 */
@Protostuff
public class GeneralizeDto {
    private String userNmae;
    private long award;
    private String time;

    public GeneralizeDto() {
    }

    public GeneralizeDto(String userNmae, long award, String time) {
        this.userNmae = userNmae;
        this.award = award;
        this.time = time;
    }

    public String getUserNmae() {
        return userNmae;
    }

    public void setUserNmae(String userNmae) {
        this.userNmae = userNmae;
    }

    public long getAward() {
        return award;
    }

    public void setAward(long award) {
        this.award = award;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
