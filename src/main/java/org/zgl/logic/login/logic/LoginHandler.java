package org.zgl.logic.login.logic;


import org.zgl.dao.entity.DBAdmininfo;
import org.zgl.dao.mapper.IDBAdminDao;
import org.zgl.dao.mapper.IUserDao;
import org.zgl.error.AppErrorCode;
import org.zgl.error.GenaryAppError;
import org.zgl.jetty.operation.OperateCommandAbstract;
import org.zgl.jetty.session.SessionManager;
import org.zgl.logic.login.dto.LoginCmdBody;
import org.zgl.orm.core.Query;
import org.zgl.orm.core.QueryFactory;
import org.zgl.dao.entity.DBUser;
import org.zgl.player.LoginDto;
import org.zgl.player.PlayerInit;
import org.zgl.player.UserMap;
import org.zgl.utils.IDFactory;
import org.zgl.utils.SpringUtils;
import org.zgl.utils.builder_clazz.ann.Protocol;

import java.sql.Date;

/**
 * 登陆
 */
@Protocol("1")
public class LoginHandler extends OperateCommandAbstract {
    /**登陆类型*/
    private final int loginType;
    private final String password;
    private final String username;
    /**头像*/
    private final String headIcon;
    /**性别*/
    private final String gender;
    private final String account;
    public LoginHandler(int loginType, String account, String password, String username, String headIcon, String gender) {
        super(0);
        this.loginType = loginType;
        this.password = password;
        this.username = username;
        this.headIcon = headIcon;
        this.gender = gender;
        this.account = account;
    }

    @Override
    public Object execute() {
        LoginCmdBody cmdBody = new LoginCmdBody();
        cmdBody.setLoginType(loginType);
        cmdBody.setAccount(account);
        cmdBody.setHeadIcon(headIcon);
        cmdBody.setPassword(password);
        cmdBody.setUsername(username);
        cmdBody.setGender(gender);
        //查找用户是否存在
        IUserDao iUserDao = SpringUtils.getBean(IUserDao.class);
        DBUser u = iUserDao.queryDBUserByAccount(account);
        if(u == null){
            u = PlayerInit.getUserInitInfo(cmdBody);
            int effectNum = iUserDao.insertDBUser(u);
            if(effectNum <= 0){
                new GenaryAppError(AppErrorCode.SERVER_ERR);
            }
            long uid = IDFactory.getId(u.getId());
            u.setUid(uid);
            DBUser u2 = new DBUser();
            u2.setId(u.getId());
            u2.setUid(uid);
            effectNum = iUserDao.updateDBUser(u2);
            if(effectNum <= 0){
                new GenaryAppError(AppErrorCode.SERVER_ERR);
            }
            IDBAdminDao idbAdminDao = SpringUtils.getBean(IDBAdminDao.class);
            DBAdmininfo dbAdmininfo = idbAdminDao.queryDBAdminBy();
            Date date = new Date(new java.util.Date().getTime());
            if (dbAdmininfo == null || !dbAdmininfo.getTimer().toString().equals(date.toString())) {
                dbAdmininfo = new DBAdmininfo();
                dbAdmininfo.setTimer(date);
                dbAdmininfo.setRegistNum(0);
                idbAdminDao.insertDBAdmin(dbAdmininfo);
            }
            dbAdmininfo.setRegistUid(uid);
            dbAdmininfo.setRegistNum(dbAdmininfo.getRegistNum() + 1);
            idbAdminDao.updateDBAdmin(dbAdmininfo);
        }
        if(SessionManager.isOnlinePlayer(u.getUid()))
            new GenaryAppError(AppErrorCode.LOGIN_ERR);
        UserMap um = PlayerInit.initUserMap(u);
        //绑定链接
        SessionManager.putSession(um.getUid(),um);
        return new LoginDto().getLoginDto(um);
    }
}
