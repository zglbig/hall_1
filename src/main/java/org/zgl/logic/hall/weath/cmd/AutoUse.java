package org.zgl.logic.hall.weath.cmd;

import org.zgl.error.AppErrorCode;
import org.zgl.error.GenaryAppError;
import org.zgl.jetty.operation.OperateCommandAbstract;
import org.zgl.jetty.session.SessionManager;
import org.zgl.logic.hall.weath.po.SQLWeathModel;
import org.zgl.orm.core.Query;
import org.zgl.orm.core.QueryFactory;
import org.zgl.dao.entity.DBUser;
import org.zgl.player.UserMap;
import org.zgl.utils.builder_clazz.ann.Protocol;

/**
 * 使用座驾
 */
@Protocol("19")
public class AutoUse extends OperateCommandAbstract {
    private final int id;
    public AutoUse(int id, long uid) {
        super(uid);
        this.id = id;
    }

    @Override
    public Object execute() {
        UserMap userMap = SessionManager.getSession(getAccount());
        SQLWeathModel weath = userMap.getWeath();
        if(!weath.autoUse(id))
            new GenaryAppError(AppErrorCode.NOT_AUTO_ERR);
        userMap.updateWeath();
        return null;
    }
}
