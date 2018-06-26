package org.zgl.logic.hall.frineds.cmd;

import org.zgl.jetty.operation.OperateCommandAbstract;
import org.zgl.jetty.session.SessionManager;
import org.zgl.logic.hall.frineds.logic.FriendsManager;
import org.zgl.player.UserMap;
import org.zgl.utils.builder_clazz.ann.Protocol;

@Protocol("5")
public class FriendSendGift extends OperateCommandAbstract {
    private final long targetUid;
    private final int giftId;
    private final int count;

    public FriendSendGift(long targetUid, int giftId, int count, long uid) {
        super(uid);
        this.targetUid = targetUid;
        this.giftId = giftId;
        this.count = count;
    }

    @Override
    public Object execute() {
        UserMap userMap = SessionManager.getSession(getAccount());
        FriendsManager.getInstance().sendGift(userMap, targetUid,giftId,count);

        return null;
    }
}
