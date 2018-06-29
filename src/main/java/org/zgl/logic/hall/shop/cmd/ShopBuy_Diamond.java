package org.zgl.logic.hall.shop.cmd;

import org.zgl.dao.entity.DBUser;
import org.zgl.error.LogAppError;
import org.zgl.jetty.operation.OperateCommandAbstract;
import org.zgl.jetty.session.SessionManager;
import org.zgl.logic.hall.shop.data.CommodityDataTable;
import org.zgl.logic.hall.siginin.po.SQLSignInModel;
import org.zgl.logic.hall.weath.dto.WeathResourceDto;
import org.zgl.logic.hall.weath.po.SQLWeathModel;
import org.zgl.player.UserMap;
import org.zgl.utils.DateUtils;
import org.zgl.utils.JsonUtils;
import org.zgl.utils.builder_clazz.ann.Protocol;

/**
 * @作者： big
 * @创建时间： 18-5-31
 * @文件描述：
 */
@Protocol("27")
public class ShopBuy_Diamond extends OperateCommandAbstract {
    private final int commodityId;

    public ShopBuy_Diamond(int commodityId,long uid) {
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

        weath.addGoldOrDiamond(48,dataTable.getCount());
        weath.addIntegral(dataTable.getIntegral());
        SQLSignInModel signIn = userMap.getSignIn();
        signIn.setRoomTime(DateUtils.currentDay());
        signIn.addTopUpNum(dataTable.getCount());
        DBUser dbUser = new DBUser();
        dbUser.setId(userMap.getId());
        dbUser.setWeath(JsonUtils.jsonSerialize(userMap.getWeath()));
        dbUser.setSignIn(JsonUtils.jsonSerialize(userMap.getSignIn()));//今天累计充值
        userMap.update(dbUser);
        userMap.update(dbUser);
        return new WeathResourceDto(weath.getGold(),weath.getDiamond(),weath.getIntegral());
    }
}
