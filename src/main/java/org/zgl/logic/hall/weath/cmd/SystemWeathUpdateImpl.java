package org.zgl.logic.hall.weath.cmd;

import org.zgl.dao.entity.DBAdmininfo;
import org.zgl.dao.mapper.IDBAdminDao;
import org.zgl.logic.hall.weath.dto.SqlSystemWeathModel;
import org.zgl.remote.IProxy;
import org.zgl.remote.ISystemWeathUpdate;
import org.zgl.utils.JsonUtils;
import org.zgl.utils.SpringUtils;
import org.zgl.utils.logger.LoggerUtils;

/**
 * @作者： big
 * @创建时间： 2018/6/15
 * @文件描述：
 */
@IProxy
public class SystemWeathUpdateImpl implements ISystemWeathUpdate {
    @Override
    public void update(int scenesId, long todayMoney, long jackpot, long bloodGroove) {
        LoggerUtils.getLogicLog().info("xxxxx");

        java.sql.Date date = new java.sql.Date(new java.util.Date().getTime());
        IDBAdminDao dao = SpringUtils.getBean(IDBAdminDao.class);
        DBAdmininfo adminInfo = dao.queryDBAdminBy();
        if (adminInfo == null || !adminInfo.getTimer().toString().equals(date.toString())) {
            adminInfo = new DBAdmininfo();
            adminInfo.setTimer(date);
            adminInfo.setRegistNum(0);
            dao.insertDBAdmin(adminInfo);
        }

        SqlSystemWeathModel sqlSystemWeath = null;
        if(adminInfo.getSystemWeath() == null || adminInfo.getSystemWeath() == "" || adminInfo.getSystemWeath() == "{}"){
            sqlSystemWeath = new SqlSystemWeathModel();
        }else {
            sqlSystemWeath = JsonUtils.jsonDeserialization(adminInfo.getSystemWeath(),SqlSystemWeathModel.class);
        }

        switch (scenesId){
            case 4:
                sqlSystemWeath.setScenes4Today(todayMoney);
                sqlSystemWeath.setScenes4Blood(bloodGroove);
                sqlSystemWeath.setScenes4Jackpot(jackpot);
                break;
            case 5:
                sqlSystemWeath.setScenes5Today(todayMoney);
                sqlSystemWeath.setScenes5Blood(bloodGroove);
                sqlSystemWeath.setScenes5Jackpot(jackpot);
                break;
            case 9:
                sqlSystemWeath.setScenes9Today(todayMoney);
                sqlSystemWeath.setScenes9Blood(bloodGroove);
                sqlSystemWeath.setScenes9Jackpot(jackpot);
                break;
        }
        DBAdmininfo dbAdmininfo = new DBAdmininfo();
        dbAdmininfo.setId(adminInfo.getId());
        dbAdmininfo.setSystemWeath(JsonUtils.jsonSerialize(sqlSystemWeath));
        dao.updateDBAdmin(dbAdmininfo);
    }
}
