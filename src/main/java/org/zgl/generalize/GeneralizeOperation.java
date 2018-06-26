package org.zgl.generalize;

import org.zgl.dao.entity.DBGeneralize;
import org.zgl.dao.entity.DBUser;
import org.zgl.dao.mapper.IDBGeneralizeDao;
import org.zgl.dao.mapper.IUserDao;
import org.zgl.error.AppErrorCode;
import org.zgl.error.GenaryAppError;
import org.zgl.jetty.operation.OperateCommandAbstract;
import org.zgl.jetty.session.SessionManager;
import org.zgl.logic.hall.weath.dto.WeathResourceDto;
import org.zgl.logic.hall.weath.po.SQLWeathModel;
import org.zgl.player.UserMap;
import org.zgl.utils.JsonUtils;
import org.zgl.utils.SpringUtils;
import org.zgl.utils.builder_clazz.ann.Protocol;

import java.sql.Date;
/**
 * @作者： big
 * @创建时间： 2018/6/16
 * @文件描述：
 */
@Protocol("32")
public class GeneralizeOperation extends OperateCommandAbstract {
    private final long targetUid;
    public GeneralizeOperation(long uid,long targetUid1) {
        super(uid);
        this.targetUid = targetUid1;
    }

    @Override
    public Object execute() {
        if(targetUid == getAccount()){
            new GenaryAppError(AppErrorCode.GENERALIZE_UID_ERROR);
        }
        IUserDao userDao = SpringUtils.getBean(IUserDao.class);
        UserMap userMap = SessionManager.getSession(getAccount());
        if(userMap.getGeneralizeUid() != 0)
            new GenaryAppError(AppErrorCode.DATA_ERR);
        DBUser dbUser = userDao.queryDBUserByUid(targetUid);
        if(dbUser == null)
            new GenaryAppError(AppErrorCode.FRIEND_NOT_USER_ERR);

        DBUser d1 = new DBUser();
        SQLWeathModel sqlWeathModel = userMap.getWeath();
        sqlWeathModel.setHasGeneralizeId(true);
        sqlWeathModel.addGoldOrDiamond(1,100000);
        //自己的数据
        d1.setWeath(JsonUtils.jsonSerialize(sqlWeathModel));
        d1.setId(userMap.getId());
        d1.setGeneralizeUid(targetUid);
        userMap.setGeneralizeUid(targetUid);
        userDao.updateDBUser(d1);

        //推广人的数据
        SQLWeathModel weathModel = JsonUtils.jsonDeserialization(dbUser.getWeath(),SQLWeathModel.class);
        weathModel.addGoldOrDiamond(1,100000);
        d1 = new DBUser();
        d1.setId(dbUser.getId());
        d1.setWeath(JsonUtils.jsonSerialize(weathModel));
        userDao.updateDBUser(d1);

        IDBGeneralizeDao generalizeDao = SpringUtils.getBean(IDBGeneralizeDao.class);
        DBGeneralize dbGeneralize = new DBGeneralize();
        dbGeneralize.setCreateTime(new Date(new java.util.Date().getTime()));
        if(dbGeneralize.getNumber() == null){
            dbGeneralize.setNumber(0);
        }
        dbGeneralize.setNumber(dbGeneralize.getNumber() +1);
        dbGeneralize.setSelfUid(targetUid);
        dbGeneralize.setTargetUid(getAccount());
        dbGeneralize.setTargetUserName(userMap.getBaseInfo().getUserName());
        dbGeneralize.setAllAward(0L);
        dbGeneralize.setAward(0L);
        generalizeDao.insertDBGeneralize(dbGeneralize);
        return new WeathResourceDto(sqlWeathModel.getGold(),sqlWeathModel.getDiamond(),sqlWeathModel.getIntegral());
    }
}
