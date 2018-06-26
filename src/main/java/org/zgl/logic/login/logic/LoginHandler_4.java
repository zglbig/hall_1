package org.zgl.logic.login.logic;

import org.zgl.dao.entity.DBUser;
import org.zgl.dao.mapper.IUserDao;
import org.zgl.error.AppErrorCode;
import org.zgl.error.GenaryAppError;
import org.zgl.jetty.operation.OperateCommandAbstract;
import org.zgl.utils.SpringUtils;
import org.zgl.utils.builder_clazz.ann.Protocol;

import java.rmi.server.UID;

/**
 * @作者： big
 * @创建时间： 2018/6/14
 * @文件描述：验证手机号
 */
@Protocol("29")
public class LoginHandler_4 extends OperateCommandAbstract {
    public LoginHandler_4(long uid) {
        super(uid);
    }

    @Override
    public Object execute() {
        IUserDao iUserDao = SpringUtils.getBean(IUserDao.class);
        DBUser u = iUserDao.queryDBUserByUid(getAccount());
        if(u != null)
            new GenaryAppError(AppErrorCode.ACCOUNT_ERR);
        return null;
    }
}
