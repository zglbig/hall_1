package org.zgl.generalize.dto;

import org.zgl.utils.builder_clazz.ann.Protostuff;

import java.util.List;

/**
 * @作者： big
 * @创建时间： 2018/6/16
 * @文件描述：
 */
@Protostuff
public class GeneralizeDtos {
    private long allAward;
    private List<GeneralizeDto> generalizeDtoList;

    public GeneralizeDtos() {
    }

    public GeneralizeDtos(long allAward, List<GeneralizeDto> generalizeDtoList) {
        this.allAward = allAward;
        this.generalizeDtoList = generalizeDtoList;
    }

    public long getAllAward() {
        return allAward;
    }

    public void setAllAward(long allAward) {
        this.allAward = allAward;
    }

    public List<GeneralizeDto> getGeneralizeDtoList() {
        return generalizeDtoList;
    }

    public void setGeneralizeDtoList(List<GeneralizeDto> generalizeDtoList) {
        this.generalizeDtoList = generalizeDtoList;
    }
}
