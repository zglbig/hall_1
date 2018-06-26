package org.zgl.logic.room_connection;

import org.zgl.jetty.session.SessionManager;
import org.zgl.logic.room.RoomManager;
import org.zgl.player.UserMap;
import org.zgl.remote.IErrorLogout;
import org.zgl.remote.IProxy;
import org.zgl.utils.logger.LoggerUtils;

/**
 * @作者： big
 * @创建时间： 2018/6/15
 * @文件描述：
 */
@IProxy
public class ErrorLogoutImpl implements IErrorLogout {
    @Override
    public void logout(long uid) {
        UserMap userMap = SessionManager.getSession(uid);
        if(userMap != null){
            RoomManager.exit(uid+"",userMap.getScenesId());
            SessionManager.removeSession(uid);
        }
    }
}
