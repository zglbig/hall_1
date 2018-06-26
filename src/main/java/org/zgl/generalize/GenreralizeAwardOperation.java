package org.zgl.generalize;

import org.zgl.dao.entity.DBGeneralize;
import org.zgl.dao.entity.DBUser;
import org.zgl.dao.mapper.IDBGeneralizeDao;
import org.zgl.error.AppErrorCode;
import org.zgl.error.GenaryAppError;
import org.zgl.jetty.operation.OperateCommandAbstract;
import org.zgl.jetty.session.SessionManager;
import org.zgl.logic.hall.weath.dto.WeathResourceDto;
import org.zgl.logic.hall.weath.po.SQLWeathModel;
import org.zgl.player.UserMap;
import org.zgl.utils.SpringUtils;
import org.zgl.utils.builder_clazz.ann.Protocol;

import java.util.List;

/**
 * @作者： big
 * @创建时间： 2018/6/16
 * @文件描述：
 */
@Protocol("34")
public class GenreralizeAwardOperation extends OperateCommandAbstract {
    public GenreralizeAwardOperation(long uid) {
        super(uid);
    }

    @Override
    public Object execute() {
        IDBGeneralizeDao generalizeDao = SpringUtils.getBean(IDBGeneralizeDao.class);
        List<DBGeneralize> generalizes = generalizeDao.queryDBGeneralizeByUid(getAccount());

        UserMap userMap = SessionManager.getSession(getAccount());
        SQLWeathModel weath = userMap.getWeath();
        boolean isUpdate = false;
        for (DBGeneralize g : generalizes) {
            long award = g.getAward();
            if(award <= 0)
                continue;
            weath.addGoldOrDiamond(1, award);
            g.setAward(0L);
            generalizeDao.updateDBGeneralizeByTargetUid(g);
            isUpdate = true;
        }
        if (isUpdate)
            userMap.updateWeath();
        return new WeathResourceDto(weath.getGold(), weath.getDiamond(), weath.getIntegral());
    }
}
