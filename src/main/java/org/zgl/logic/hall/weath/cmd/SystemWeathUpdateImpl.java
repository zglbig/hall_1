package org.zgl.logic.hall.weath.cmd;

import org.zgl.dao.entity.DBAdmininfo;
import org.zgl.dao.mapper.IDBAdminDao;
import org.zgl.remote.IProxy;
import org.zgl.remote.ISystemWeathUpdate;
import org.zgl.utils.SpringUtils;

/**
 * @作者： big
 * @创建时间： 2018/6/15
 * @文件描述：
 */
@IProxy
public class SystemWeathUpdateImpl implements ISystemWeathUpdate {
    @Override
    public void update(int scenesId, long todayMoney, long jackpot, long bloodGroove) {
            java.sql.Date date = new java.sql.Date(new java.util.Date().getTime());
            IDBAdminDao dao = SpringUtils.getBean(IDBAdminDao.class);
            DBAdmininfo adminInfo = dao.queryDBAdminBy();
            if(!adminInfo.getTimer().equals(date)) {
                adminInfo = new DBAdmininfo();
                adminInfo.setTimer(date);
                adminInfo.setRegistNum(1);
                dao.insertDBAdmin(adminInfo);
        }
    }
}
