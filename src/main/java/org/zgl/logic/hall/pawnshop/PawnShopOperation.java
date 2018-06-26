package org.zgl.logic.hall.pawnshop;

import org.zgl.error.AppErrorCode;
import org.zgl.error.GenaryAppError;
import org.zgl.jetty.operation.OperateCommandAbstract;
import org.zgl.jetty.session.SessionManager;
import org.zgl.logic.hall.shop.data.CommodityDataTable;
import org.zgl.logic.hall.weath.po.ResourceModel;
import org.zgl.logic.hall.weath.po.SQLWeathModel;
import org.zgl.player.UserMap;

import java.util.List;

/**
 * @作者： big
 * @创建时间： 2018/6/23
 * @文件描述：
 */
public class PawnShopOperation extends OperateCommandAbstract {
    private final int giftId;
    public PawnShopOperation(long uid,int giftId) {
        super(uid);
        this.giftId = giftId;
    }

    @Override
    public Object execute() {
        CommodityDataTable dataTable = CommodityDataTable.get(giftId);
        if (dataTable == null)
            new GenaryAppError(AppErrorCode.DATA_ERR);
        if (dataTable.getIntegral() != -1)//不是礼物不能送
            new GenaryAppError(2007);
        UserMap userMap = SessionManager.getSession(getAccount());
        if(userMap == null)
            new GenaryAppError(AppErrorCode.DATA_ERR);
        SQLWeathModel weath = userMap.getWeath();
        List<ResourceModel> gift = weath.getGifts();
        if(gift == null || gift.size() <= 0)
            new GenaryAppError(AppErrorCode.NOT_GIFT_CAN_PAWN_SHOP);
        boolean hasGif = false;
        ResourceModel g = null;
        for(ResourceModel r:gift){
            if(r.getId() == giftId){
                hasGif = true;
               g = r;
                break;
            }
        }
        if(!hasGif)
            new GenaryAppError(AppErrorCode.NOT_GIFT_CAN_PAWN_SHOP);
        gift.remove(g);
        weath.addGoldOrDiamond(1, (long) (dataTable.getSelling() * 0.8));
        userMap.updateWeath();
        return null;
    }
}
