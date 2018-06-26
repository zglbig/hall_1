package org.zgl.logic.login.logic;

import org.zgl.dao.entity.DBAdmininfo;
import org.zgl.dao.entity.DBUser;
import org.zgl.dao.mapper.IDBAdminDao;
import org.zgl.dao.mapper.IUserDao;
import org.zgl.error.AppErrorCode;
import org.zgl.error.GenaryAppError;
import org.zgl.jetty.operation.OperateCommandAbstract;
import org.zgl.jetty.session.SessionManager;
import org.zgl.logic.login.dto.LoginCmdBody;
import org.zgl.player.PlayerInit;
import org.zgl.utils.IDFactory;
import org.zgl.utils.SpringUtils;
import org.zgl.utils.builder_clazz.ann.Protocol;
import org.zgl.utils.logger.LoggerUtils;

import java.sql.Date;

/**
 * @作者： big
 * @创建时间： 2018/6/13
 * @文件描述：
 */
@Protocol("28")
public class LoginHandler_3 extends OperateCommandAbstract {
    private final int loginType;
    private final String password;
    private final String userName;
    private final String headIcon;
    private final String gender;
    private final String account;
    public LoginHandler_3(int loginType,String account,String password,String userName,String headIcon,String gender) {
        super(0);
        this.loginType = loginType;
        this.password = password;
        this.headIcon = headIcon;
        this.userName = userName;
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
        cmdBody.setUsername(userName);
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
        }else {
            new GenaryAppError(AppErrorCode.ACCOUNT_ERR);
        }
        //绑定链接
        return null;
    }
}
