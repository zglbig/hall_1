package org.zgl.generalize;

import org.zgl.dao.entity.DBGeneralize;
import org.zgl.dao.mapper.IDBGeneralizeDao;
import org.zgl.generalize.dto.GeneralizeDto;
import org.zgl.generalize.dto.GeneralizeDtos;
import org.zgl.jetty.operation.OperateCommandAbstract;
import org.zgl.utils.SpringUtils;
import org.zgl.utils.builder_clazz.ann.Protocol;

import java.util.ArrayList;
import java.util.List;

/**
 * @作者： big
 * @创建时间： 2018/6/16
 * @文件描述：
 */
@Protocol("33")
public class LookGeneralizeInfoOperation extends OperateCommandAbstract {
    public LookGeneralizeInfoOperation(long uid) {
        super(uid);
    }

    @Override
    public Object execute() {
        IDBGeneralizeDao generalizeDao = SpringUtils.getBean(IDBGeneralizeDao.class);
        List<DBGeneralize> generalizes = generalizeDao.queryDBGeneralizeByUid(getAccount());
        List<GeneralizeDto> generalizeDtos = new ArrayList<>(generalizes.size());
        long allAward = 0;
        for(DBGeneralize d: generalizes){
            allAward += d.getAllAward()==null ? 0 : d.getAllAward();
            generalizeDtos.add(new GeneralizeDto(d.getTargetUserName(),d.getAward(),d.getCreateTime().toString()));
        }
        return new GeneralizeDtos(allAward,generalizeDtos);
    }
}
