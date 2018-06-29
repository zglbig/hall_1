package org.zgl.logic.hall.onlineAward;

import org.zgl.error.AppErrorCode;
import org.zgl.error.GenaryAppError;
import org.zgl.jetty.operation.OperateCommandAbstract;
import org.zgl.jetty.session.SessionManager;
import org.zgl.logic.hall.siginin.po.SQLSignInModel;
import org.zgl.logic.hall.weath.po.SQLWeathModel;
import org.zgl.dao.entity.DBUser;
import org.zgl.player.UserMap;
import org.zgl.utils.JsonUtils;
import org.zgl.utils.builder_clazz.ann.Protocol;
import org.zgl.utils.builder_clazz.excel_init_data.StaticConfigMessage;
import org.zgl.utils.weightRandom.IWeihtRandom;
import org.zgl.utils.weightRandom.WeightRandom;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Protocol("9")
public class OnlineAwardCmd extends OperateCommandAbstract {

    public OnlineAwardCmd(long uid) {
        super(uid);
    }

    @Override
    public Object execute() {
        UserMap um = SessionManager.getSession(getAccount());
        //幸运圆盘嵌入到签到中去了
        SQLSignInModel signIn = um.getSignIn();
        if(!signIn.canAward())
            new GenaryAppError(AppErrorCode.DIAL_AWARD_NUM_ERR);
        //获取所有奖项
        Map<Serializable,Object> map = StaticConfigMessage.getInstance().getMap(DialDataTable.class);
        //权重随机并返回获奖位置
        List<IWeihtRandom> iWeihtRandoms = new ArrayList<>(map.size());
        for(Object iwr : map.values()){
            iWeihtRandoms.add((IWeihtRandom) iwr);
        }
        int position = WeightRandom.awardPosition(iWeihtRandoms);
        //获取奖项位置的奖励物品
        OnlineAwardDataTable dataTable = OnlineAwardDataTable.get(position);
        signIn.addDialNum();
        SQLWeathModel weath = um.getWeath();
        weath.addGoldOrDiamond(1,dataTable.getNum());

        DBUser dbUser = new DBUser();
        dbUser.setId(um.getId());
        dbUser.setSignIn(JsonUtils.jsonSerialize(signIn));
        dbUser.setWeath(JsonUtils.jsonSerialize(weath));
        um.update(dbUser);
        return new DialDto(position,dataTable.getAwardId());
    }
}
