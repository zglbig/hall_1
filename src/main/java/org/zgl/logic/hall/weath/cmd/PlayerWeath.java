package org.zgl.logic.hall.weath.cmd;

import org.zgl.jetty.operation.OperateCommandAbstract;
import org.zgl.jetty.session.SessionManager;
import org.zgl.logic.hall.weath.po.ResourceModel;
import org.zgl.logic.hall.weath.po.SQLWeathModel;
import org.zgl.player.UserMap;
import org.zgl.utils.DateUtils;
import org.zgl.utils.builder_clazz.ann.Protocol;

import java.util.ArrayList;
import java.util.List;

@Protocol("20")
public class PlayerWeath extends OperateCommandAbstract {

    public PlayerWeath(long uid) {
        super(uid);
    }

    @Override
    public Object execute() {
        UserMap userMap = SessionManager.getSession(getAccount());
        return userMap.getWeath().weathDto();
    }

}
