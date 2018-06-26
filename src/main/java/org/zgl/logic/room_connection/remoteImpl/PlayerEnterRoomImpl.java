package org.zgl.logic.room_connection.remoteImpl;

import org.zgl.error.AppErrorCode;
import org.zgl.error.GenaryAppError;
import org.zgl.jetty.session.SessionManager;
import org.zgl.player.PlayerInfoDto;
import org.zgl.player.UserMap;
import org.zgl.remote.IPlayerEnterRoom;
import org.zgl.remote.IProxy;

/**
 * @作者： big
 * @创建时间： 2018/6/15
 * @文件描述：
 */
@IProxy
public class PlayerEnterRoomImpl implements IPlayerEnterRoom {
    @Override
    public PlayerInfoDto enter(long uid) {
        UserMap u = SessionManager.getSession(uid);
        if(u == null)
            new GenaryAppError(AppErrorCode.DATA_ERR);
        if(!SessionManager.isOnlinePlayer(-999999999)) {
            UserMap userMap = new UserMap();
            userMap.setUid(-999999999);
            SessionManager.putSession(userMap.getUid(), u);
        }
        return new PlayerInfoDto(u);
    }
}
