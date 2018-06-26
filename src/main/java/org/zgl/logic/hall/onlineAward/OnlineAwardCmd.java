package org.zgl.logic.hall.onlineAward;

import org.zgl.error.AppErrorCode;
import org.zgl.error.GenaryAppError;
import org.zgl.jetty.operation.OperateCommandAbstract;
import org.zgl.jetty.session.SessionManager;
import org.zgl.logic.hall.siginin.po.SQLSignInModel;
import org.zgl.logic.hall.weath.po.SQLWeathModel;
import org.zgl.orm.core.Query;
import org.zgl.orm.core.QueryFactory;
import org.zgl.dao.entity.DBUser;
import org.zgl.player.UserMap;
import org.zgl.utils.DateUtils;
import org.zgl.utils.JsonUtils;
import org.zgl.utils.RandomUtils;
import org.zgl.utils.builder_clazz.ann.Protocol;

@Protocol("9")
public class OnlineAwardCmd extends OperateCommandAbstract {

    public OnlineAwardCmd(long uid) {
        super(uid);
    }

    @Override
    public Object execute() {
        UserMap userMap = SessionManager.getSession(getAccount());
        SQLSignInModel model = userMap.getSignIn();

        long currentTime = DateUtils.currentTime();
        if(model.getOnlineAwardDay() != DateUtils.currentDay()){
            model.setOnlineAwardDay(DateUtils.currentDay());
            model.setOnlineAwardNum(0);
        }
        int awardCount = model.getOnlineAwardNum()+1;
        OnlineAwardTimerDataTable onlineAwardTimerDataTable = OnlineAwardTimerDataTable.get(awardCount);
        if(onlineAwardTimerDataTable == null && model.getOnlineAwardDay() == DateUtils.currentDay())
            new GenaryAppError(AppErrorCode.ONLINE_AWARD_NUM_ERR);
        int timerLimit = onlineAwardTimerDataTable.getTimer();
        int t = (int) ((currentTime - model.getOnlineAwardTimer())/60000);
        if(t < timerLimit)
            new GenaryAppError(AppErrorCode.ONLINE_AWARD_TIMER_ERR);
        int awardId = RandomUtils.randomIndex(8) + 1;
        OnlineAwardDataTable onlineAwardDataTable = OnlineAwardDataTable.get(awardId);
        if(onlineAwardDataTable == null)
            new GenaryAppError(AppErrorCode.DATA_ERR);
        SQLWeathModel weathModel = userMap.getWeath();
        weathModel.addGoldOrDiamond(onlineAwardDataTable.getAwardId(),onlineAwardDataTable.getNum());
        model.setOnlineAwardNum(awardCount);
        model.setOnlineAwardTimer(currentTime);
        DBUser user = new DBUser();
        user.setId(userMap.getId());
        user.setWeath(JsonUtils.jsonSerialize(weathModel));
        user.setTask(JsonUtils.jsonSerialize(model));
        userMap.update(user);
        return new DialDto(awardId,awardId);
    }
}
