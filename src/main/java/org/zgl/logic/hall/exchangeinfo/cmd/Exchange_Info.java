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
@Protocol("35")
public class Exchange_Info extends OperateCommandAbstract {
    private final String userName;
    private final String gender;
    private final String desc;
    private final String relation;
    public Exchange_Info(long uid, String userName, String gender, String desc, String relation) {
        super(uid);
        this.userName = userName;
        this.gender = gender;
        this.desc = desc;
        this.relation = relation;
    }

    @Override
    public Object execute() {
        UserMap userMap = SessionManager.getSession(getAccount());
        if(userMap == null)
            new GenaryAppError(AppErrorCode.DATA_ERR);
        SQLUserBaseInfo base = userMap.getBaseInfo();
        base.setGender(gender);
        base.setRelation(relation);
        base.setUserName(userName);
        base.setSignAture(desc);
        DBUser dbUser = new DBUser();
        dbUser.setId(userMap.getId());
        dbUser.setBaseInfo(JsonUtils.jsonSerialize(base));
        userMap.update(dbUser);
        return null;
    }
}
