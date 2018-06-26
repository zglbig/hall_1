package org.zgl.logic.hall.weath.cmd;

import org.zgl.error.AppErrorCode;
import org.zgl.error.GenaryAppError;
import org.zgl.jetty.session.SessionManager;
import org.zgl.logic.hall.weath.dto.ShopBuySyncDto;
import org.zgl.logic.hall.weath.po.SQLWeathModel;
import org.zgl.player.PlayerInfoDto;
import org.zgl.player.UserMap;
import org.zgl.remote.IProxy;
import org.zgl.remote.IShopUpdateWeath;

/**
 * @作者： big
 * @创建时间： 2018/6/15
 * @文件描述：
 */
@IProxy
public class ShopUpdateWeathImpl implements IShopUpdateWeath {
    @Override
    public PlayerInfoDto update(long uid) {
        UserMap userMap = SessionManager.getSession(uid);
        if(userMap == null)
            new GenaryAppError(AppErrorCode.DATA_ERR);
        return new PlayerInfoDto(userMap);
    }
}
