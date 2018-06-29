package org.zgl.logic.hall.siginin.logic;


import org.zgl.dao.entity.DBUser;
import org.zgl.error.AppErrorCode;
import org.zgl.error.GenaryAppError;
import org.zgl.jetty.operation.OperateCommandAbstract;
import org.zgl.jetty.session.SessionManager;
import org.zgl.logic.hall.shop.ShopEnum;
import org.zgl.logic.hall.siginin.data.SignInDataTable;
import org.zgl.logic.hall.siginin.po.SQLSignInModel;
import org.zgl.logic.hall.vip.po.VipDataTable;
import org.zgl.logic.hall.weath.dto.WeathResourceDto;
import org.zgl.logic.hall.weath.po.SQLWeathModel;
import org.zgl.player.UserMap;
import org.zgl.utils.DateUtils;
import org.zgl.utils.JsonUtils;
import org.zgl.utils.builder_clazz.ann.Protocol;

@Protocol("16")
public class SignIn extends OperateCommandAbstract {
    public SignIn(long uid) {
        super(uid);
    }


    @Override
    public Object execute() {
        UserMap um = SessionManager.getSession(getAccount());
        SQLSignInModel model = um.getSignIn();
        if(model == null)
            new GenaryAppError(AppErrorCode.DATA_ERR);
        int todayTime = DateUtils.currentDay();
        if(model.getSignInTime() >= todayTime)
            new GenaryAppError(AppErrorCode.AWARD_GET_ERR);
        int day = model.getSignDay();
        if(todayTime - model.getSignDay() != 1){
            day = 0;
        }
        if(day >= 7)
            day = 6;
        day++;

        SignInDataTable dataTable = SignInDataTable.get(day);
        if(dataTable == null)
            new GenaryAppError(AppErrorCode.DATA_ERR);
        SQLWeathModel weath = um.getWeath();
        //vip加成
        int vipSignIn = 0;
        VipDataTable vipDataTable = VipDataTable.get(weath.getVipLv());
        if(vipDataTable != null)
            vipSignIn = vipDataTable.getSingIn();
        long gold = dataTable.getGold();

        model.setSignDay(day);
        model.setSignInTime(todayTime);
        weath.addGoldOrDiamond(1,gold+vipSignIn*gold);
        //换牌卡
        weath.addResource(ShopEnum.PROP,22,dataTable.getChangeCard());
        DBUser dbUser = new DBUser();
        dbUser.setId(um.getId());
        dbUser.setWeath(JsonUtils.jsonSerialize(um.getWeath()));
        dbUser.setSignIn(JsonUtils.jsonSerialize(um.getSignIn()));
        um.update(dbUser);
        return new WeathResourceDto(weath.getGold(),weath.getDiamond(),weath.getIntegral());
    }
}
