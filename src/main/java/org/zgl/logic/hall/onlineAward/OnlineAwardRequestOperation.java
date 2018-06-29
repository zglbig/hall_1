package org.zgl.logic.hall.onlineAward;

import org.zgl.jetty.operation.OperateCommandAbstract;
import org.zgl.jetty.session.SessionManager;
import org.zgl.logic.hall.siginin.po.SQLSignInModel;
import org.zgl.player.UserMap;
import org.zgl.utils.builder_clazz.ann.Protocol;

/**
 * @作者： big
 * @创建时间： 2018/6/26
 * @文件描述：
 */
@Protocol("38")
public class OnlineAwardRequestOperation extends OperateCommandAbstract {
    public OnlineAwardRequestOperation(long uid) {
        super(uid);
    }

    @Override
    public Object execute() {
        UserMap userMap = SessionManager.getSession(getAccount());
        SQLSignInModel signIn = userMap.getSignIn();
        OnlineRequestDto dto = new OnlineRequestDto();
        dto.setCanGetCount(signIn.awardCount());
        dto.setAhRoonBetNum(signIn.getAhRoomBetAllNum());
        dto.setDiceRoomNum(signIn.getDiceRoomBetAllNum());
        dto.setTopUpNum(signIn.getTopUpAllNum());
        dto.setToRoomBetNum(signIn.getToRoomBetAllNum());
        dto.setGetCounted(signIn.getDialNum());
        dto.setBetAllCount(200000);
        return dto;
    }
}
