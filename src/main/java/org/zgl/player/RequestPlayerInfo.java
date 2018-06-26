package org.zgl.player;


import org.zgl.dao.mapper.IUserDao;
import org.zgl.error.AppErrorCode;
import org.zgl.error.GenaryAppError;
import org.zgl.jetty.operation.OperateCommandAbstract;
import org.zgl.jetty.session.SessionManager;
import org.zgl.orm.core.Query;
import org.zgl.orm.core.QueryFactory;
import org.zgl.dao.entity.DBUser;
import org.zgl.utils.SpringUtils;
import org.zgl.utils.builder_clazz.ann.Protocol;

@Protocol("21")
public class RequestPlayerInfo extends OperateCommandAbstract {
    public RequestPlayerInfo(long uid) {
        super(uid);
    }

    @Override
    public Object execute() {
        UserMap um = SessionManager.getSession(getAccount());
        if(um == null){
            IUserDao userDao = SpringUtils.getBean(IUserDao.class);
            DBUser u = userDao.queryDBUserByUid(getAccount());
            um = PlayerInit.initUserMap(u);
        }
        if(um == null)
            new GenaryAppError(AppErrorCode.FRIEND_NOT_USER_ERR);
        OtherInfoModel dto = new OtherInfoModel(um);
        return dto;
    }
}
