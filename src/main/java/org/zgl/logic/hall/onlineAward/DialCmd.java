package org.zgl.logic.hall.onlineAward;

import org.zgl.dao.entity.DBUser;
import org.zgl.error.AppErrorCode;
import org.zgl.error.GenaryAppError;
import org.zgl.jetty.operation.OperateCommandAbstract;
import org.zgl.jetty.session.SessionManager;
import org.zgl.logic.hall.shop.ShopEnum;
import org.zgl.logic.hall.siginin.po.SQLSignInModel;
import org.zgl.logic.hall.weath.po.SQLWeathModel;
import org.zgl.player.UserMap;
import org.zgl.utils.DateUtils;
import org.zgl.utils.JsonUtils;
import org.zgl.utils.builder_clazz.ann.Protocol;
import org.zgl.utils.builder_clazz.excel_init_data.StaticConfigMessage;
import org.zgl.utils.weightRandom.IWeihtRandom;
import org.zgl.utils.weightRandom.WeightRandom;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Protocol("13")
public class DialCmd extends OperateCommandAbstract {
    public enum DialEnum{
        /**酱油的*/
        NONE(1),
        /**商城中的物品*/
        SHOP(2),
        /**神秘大礼包*/
        MYSTERIOUS_GIFT_BAG(4),
        /**再送几次*/
        AGAIN(5),
        /**话费*/
        TELEPHONE_CHARGE(6);
        private int id;

        private DialEnum(int id) {
            this.id = id;
        }
        public int id(){
            return id;
        }
        private static final Map<Integer,DialEnum> map;
        static {
            map = new HashMap<>(6);
            for(DialEnum e: DialEnum.values()){
                map.putIfAbsent(e.id,e);
            }
        }
        public static DialEnum getEnum(int i){
            return map.get(i);
        }
    }
    public DialCmd(long uid) {
        super(uid);
    }

    @Override
    public Object execute() {
        UserMap um = SessionManager.getSession(getAccount());
        //幸运圆盘嵌入到签到中去了
        SQLSignInModel signIn = um.getSignIn();
        if(signIn.getDialNum() <= 0)
            new GenaryAppError(AppErrorCode.ONLINE_AWARD_NUM_ERR);
        //获取所有奖项
        Map<Serializable,Object> map = StaticConfigMessage.getInstance().getMap(DialDataTable.class);
        //权重随机并返回获奖位置
        List<IWeihtRandom> iWeihtRandoms = new ArrayList<>(map.size());
        for(Object iwr : map.values()){
            iWeihtRandoms.add((IWeihtRandom) iwr);
        }
        int position = WeightRandom.awardPosition(iWeihtRandoms);
        DialDataTable dataTable = DialDataTable.get(position);
        SQLWeathModel weath = um.getWeath();
        ShopEnum shopEnum = ShopEnum.getEnum(dataTable.getAwardId());
        if(dataTable.getAwardId() == 1) {
            um.getWeath().addGoldOrDiamond(1, dataTable.getCount());
        }else {
            um.getWeath().addResource(shopEnum,dataTable.getAwardId(),dataTable.getCount());
        }
        signIn.setDialNum(signIn.getDialNum() - 1);
        DBUser user = new DBUser();
        user.setId(um.getId());
        user.setSignIn(JsonUtils.jsonSerialize(signIn));
        user.setWeath(JsonUtils.jsonSerialize(weath));
        um.update(user);
        return new DialDto(position,dataTable.getAwardId());
    }
    private void xxx(){
//        int awardType = dataTable.getAwardType();
//        dialNum--;
//        //如果不是获得打酱油
//        if(awardType != DialEnum.NONE.id()) {
//            //获取奖励物品对应的商城id
//            CommodityDataTable commodityDataTable = CommodityDataTable.get(dataTable.getAwardId());
//            if (commodityDataTable == null)
//                new GenaryAppError(AppErrorCode.DATA_ERR);
//            ShopEnum shopEnum = ShopEnum.getEnum(commodityDataTable.getShopId());
//            //如果是商城里有卖的东西
//            if (awardType == DialEnum.SHOP.id()) {
//                um.getWeath().addResource(shopEnum, commodityDataTable.getId(), dataTable.getCount());
//                um.updateWeath();
//            } else if (awardType == DialEnum.AGAIN.id()) {
//                //再玩dataTable.getCount()次
//                signIn.setDialNum(dialNum += dataTable.getCount());
//                um.updateWeath();
//            } else if (awardType == DialEnum.TELEPHONE_CHARGE.id()) {
//                //TODO...话费
//            } else if (awardType == DialEnum.MYSTERIOUS_GIFT_BAG.id()) {
//                //TODO...神秘大礼包
//            }
//        }
    }
}
