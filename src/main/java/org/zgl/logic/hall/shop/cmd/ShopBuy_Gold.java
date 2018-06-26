package org.zgl.logic.hall.shop.cmd;

import org.zgl.dao.entity.DBGeneralize;
import org.zgl.dao.entity.DBUser;
import org.zgl.dao.mapper.IDBGeneralizeDao;
import org.zgl.error.AppErrorCode;
import org.zgl.error.GenaryAppError;
import org.zgl.error.LogAppError;
import org.zgl.jetty.operation.OperateCommandAbstract;
import org.zgl.jetty.session.SessionManager;
import org.zgl.logic.hall.shop.data.CommodityDataTable;
import org.zgl.logic.hall.weath.cmd.BackHallImpl;
import org.zgl.logic.hall.weath.dto.WeathResourceDto;
import org.zgl.logic.hall.weath.po.SQLWeathModel;
import org.zgl.player.UserMap;
import org.zgl.utils.SpringUtils;
import org.zgl.utils.builder_clazz.ann.Protocol;

/**
 * @作者： big
 * @创建时间： 2018/5/21
 * @文件描述：
 */
@Protocol("24")
public class ShopBuy_Gold extends OperateCommandAbstract {
    private final int commodityId;

    public ShopBuy_Gold(int commodityId, long uid) {
        super(uid);
        this.commodityId = commodityId;
    }

    @Override
    public Object execute() {
        CommodityDataTable dataTable = CommodityDataTable.get(commodityId);
        if (dataTable == null)
            new LogAppError("获取不到id为:" + commodityId + " 商城对应的物品");
        UserMap userMap = SessionManager.getSession(getAccount());

        SQLWeathModel weath = userMap.getWeath();
        if (!weath.reduceDiamond(dataTable.getSelling())) {
            new GenaryAppError(AppErrorCode.DIAMOND_ERR);
        }
        weath.addGoldOrDiamond(1, dataTable.getCount());
        if (userMap.getGeneralizeUid() != 0)
            generalize(dataTable.getCount(), userMap.getUid());
        userMap.updateWeath();
        return new WeathResourceDto(weath.getGold(), weath.getDiamond(), weath.getIntegral());
    }

    private void generalize( long gold, long uid) {
        IDBGeneralizeDao generalizeDao = SpringUtils.getBean(IDBGeneralizeDao.class);
        DBGeneralize generalize = generalizeDao.queryDBGeneralizeByTargetUid(uid);
        DBGeneralize dbGeneralize = new DBGeneralize();
        long t = (long) (gold * 0.2);
        dbGeneralize.setTargetUid(uid);
        dbGeneralize.setAward(generalize.getAward() + t);
        dbGeneralize.setAllAward(generalize.getAllAward() + t);
        generalizeDao.updateDBGeneralizeByTargetUid(dbGeneralize);
    }
}
