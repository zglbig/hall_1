package org.zgl.logic.login.logic;


import org.zgl.dao.mapper.IUserDao;
import org.zgl.error.AppErrorCode;
import org.zgl.error.GenaryAppError;
import org.zgl.jetty.operation.OperateCommandAbstract;
import org.zgl.jetty.session.SessionManager;
import org.zgl.orm.core.Query;
import org.zgl.orm.core.QueryFactory;
import org.zgl.dao.entity.DBUser;
import org.zgl.player.LoginDto;
import org.zgl.player.PlayerInit;
import org.zgl.player.UserMap;
import org.zgl.utils.SpringUtils;
import org.zgl.utils.builder_clazz.ann.Protocol;

@Protocol("2")
public class LoginHandler_2 extends OperateCommandAbstract {
    private final String account;
    private final String password;
    public LoginHandler_2( String account,String password) {
        super(0);
        this.password = password;
        this.account = account;
    }

    @Override
    public Object execute() {
        //查找用户是否存在
        IUserDao iUserDao = SpringUtils.getBean(IUserDao.class);

        DBUser u = iUserDao.queryDBUserByAccount(account);
        if(u == null)
            new GenaryAppError(AppErrorCode.ACCOUNT_NOT_ERR);
        if(SessionManager.isOnlinePlayer(u.getUid()))
            new GenaryAppError(AppErrorCode.LOGIN_ERR);
        if(u == null || !u.getPassword().equals(password))
            new GenaryAppError(AppErrorCode.PASSWORD_ERR);
        UserMap um = PlayerInit.initUserMap(u);
        //绑定链接
        SessionManager.putSession(um.getUid(),um);
        return new LoginDto().getLoginDto(um);
    }
}
