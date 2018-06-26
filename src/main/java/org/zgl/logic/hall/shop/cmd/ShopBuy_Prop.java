package org.zgl.logic.hall.shop.cmd;

import org.zgl.dao.entity.DBUser;
import org.zgl.error.AppErrorCode;
import org.zgl.error.GenaryAppError;
import org.zgl.error.LogAppError;
import org.zgl.jetty.operation.OperateCommandAbstract;
import org.zgl.jetty.session.SessionManager;
import org.zgl.logic.hall.shop.ShopEnum;
import org.zgl.logic.hall.shop.data.CommodityDataTable;
import org.zgl.logic.hall.task.manager.TaskManager;
import org.zgl.logic.hall.weath.dto.WeathResourceDto;
import org.zgl.logic.hall.weath.po.SQLWeathModel;
import org.zgl.player.UserMap;
import org.zgl.utils.JsonUtils;
import org.zgl.utils.builder_clazz.ann.Protocol;

/**
 * @作者： big
 * @创建时间： 2018/5/21
 * @文件描述：
 */
@Protocol("26")
public class ShopBuy_Prop extends OperateCommandAbstract {
    private final int commodityId;
    public ShopBuy_Prop(int commodityId,long uid) {
        super(uid);
        this.commodityId = commodityId;
    }

    @Override
    public Object execute() {
        CommodityDataTable dataTable = CommodityDataTable.get(commodityId);
        if(dataTable == null)
            new LogAppError("获取不到id为:"+commodityId+" 商城对应的物品");
        UserMap userMap = SessionManager.getSession(getAccount());
        SQLWeathModel weath = userMap.getWeath();
        if(!weath.reduceIntegral(dataTable.getSelling())){
            new GenaryAppError(AppErrorCode.GOLD_NOT_ERR);
        }
        if(dataTable.getDescribe().equals("1")){
            //vip啊
            ShopBuy_Vip vip = new ShopBuy_Vip(commodityId,getAccount());
            vip.execute();

        }else {
            ShopEnum shopEnum = ShopEnum.getEnum(dataTable.getShopId());
            int count = dataTable.getCount();
            TaskManager.getInstance().listener(userMap,9);//道具
            weath.addResource(shopEnum,commodityId,count);
            DBUser dbUser = new DBUser();
            dbUser.setId(userMap.getId());
            dbUser.setWeath(JsonUtils.jsonSerialize(userMap.getWeath()));
            dbUser.setTask(JsonUtils.jsonSerialize(userMap.getTask()));
            userMap.update(dbUser);
        }
        WeathResourceDto dto = new WeathResourceDto(weath.getGold(),weath.getDiamond(),weath.getIntegral());
        dto.setVipLv(weath.getVipLv());
        return dto;
    }
}
