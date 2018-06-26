package org.zgl.logic.hall.giftBag;

import org.zgl.error.AppErrorCode;
import org.zgl.error.GenaryAppError;
import org.zgl.jetty.operation.OperateCommandAbstract;
import org.zgl.jetty.session.SessionManager;
import org.zgl.logic.hall.giftBag.data.GiftBagAwardDataTable;
import org.zgl.logic.hall.giftBag.po.SQLGiftBagModel;
import org.zgl.player.UserMap;
import org.zgl.utils.builder_clazz.ann.Protocol;

/**
 * @作者： big
 * @创建时间： 2018/6/25
 * @文件描述：
 */
@Protocol("37")
public class GiftBagRequestOperation extends OperateCommandAbstract {
    public GiftBagRequestOperation(long uid) {
        super(uid);
    }

    @Override
    public Object execute() {
        UserMap userMap = SessionManager.getSession(getAccount());
        if(userMap == null)
            new GenaryAppError(AppErrorCode.ACCOUNT_NOT_LOGIN_ERROR);
        SQLGiftBagModel giftBag = userMap.getGiftBag();
        GiftBagAwardDataTable dataTable = GiftBagAwardDataTable.get(giftBag.getDay());
        boolean canGet = giftBag.getPrimary() == dataTable.getPrimary()
                && giftBag.getIntermedite() == dataTable.getIntermediate()
                && giftBag.getAdvanced() == dataTable.getAdvanced();
        return new GiftBagRequestDto(giftBag.isHasGet(),canGet);
    }
}
