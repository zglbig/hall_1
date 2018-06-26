package org.zgl.logic.hall.exchangeinfo.cmd;

import org.zgl.dao.entity.DBUser;
import org.zgl.error.AppErrorCode;
import org.zgl.error.GenaryAppError;
import org.zgl.jetty.operation.OperateCommandAbstract;
import org.zgl.jetty.session.SessionManager;
import org.zgl.logic.login.po.SQLUserBaseInfo;
import org.zgl.player.UserMap;
import org.zgl.utils.JsonUtils;
import org.zgl.utils.builder_clazz.ann.Protocol;

/**
 * @作者： big
 * @创建时间： 2018/6/19
 * @文件描述：
 */
@Protocol("36")
public class Exchange_HeadIcon extends OperateCommandAbstract {
    private final String headIcen;

    public Exchange_HeadIcon(long uid, String headIcen) {
        super(uid);
        this.headIcen = headIcen;
    }


    @Override
    public Object execute() {
        UserMap userMap = SessionManager.getSession(getAccount());
        if(userMap == null)
            new GenaryAppError(AppErrorCode.DATA_ERR);
        SQLUserBaseInfo base = userMap.getBaseInfo();
        base.setHeadIcon(headIcen);
        DBUser dbUser = new DBUser();
        dbUser.setId(userMap.getId());
        dbUser.setBaseInfo(JsonUtils.jsonSerialize(base));
        userMap.update(dbUser);
        return null;
    }
}
