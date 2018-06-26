package org.zgl.logic.hall.frineds.cmd;

import org.zgl.jetty.operation.OperateCommandAbstract;
import org.zgl.jetty.session.SessionManager;
import org.zgl.logic.hall.frineds.logic.FriendsManager;
import org.zgl.player.UserMap;
import org.zgl.utils.builder_clazz.ann.Protocol;

@Protocol("6")
public class FriendHandler extends OperateCommandAbstract {
    private final int cmd;
    /**要加的好友的id*/
    private final long targetUid;
    private final long selfUid;

    public FriendHandler(int cmd, long targetUid, long selfUid) {
        super(selfUid);
        this.cmd = cmd;
        this.targetUid = targetUid;
        this.selfUid = selfUid;
    }

    @Override
    public Object execute() {
        FriendsManager manager = FriendsManager.getInstance();
        UserMap userMap = SessionManager.getSession(selfUid);
        switch (cmd){
            case 1:
                return manager.addFriend(userMap, targetUid);
            case 2:
                return manager.removeFriend(userMap, targetUid);
            case 3:
                return manager.addEnemy(userMap, targetUid);
            case 4:
                return manager.removeEnemy(userMap, targetUid);
            case 5:
                return manager.selectFriend(userMap, targetUid);
                default:
                    return null;
        }
    }
}
