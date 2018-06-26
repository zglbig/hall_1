package org.zgl.logic.hall.weath.cmd;

import org.zgl.dao.entity.DBGeneralize;
import org.zgl.dao.mapper.IDBGeneralizeDao;
import org.zgl.error.AppErrorCode;
import org.zgl.error.GenaryAppError;
import org.zgl.error.RPCError;
import org.zgl.jetty.session.SessionManager;
import org.zgl.logic.hall.giftBag.manaer.GiftBagManager;
import org.zgl.logic.hall.weath.dto.RoomWeathDtos;
import org.zgl.logic.hall.weath.po.SQLWeathModel;
import org.zgl.player.PlayerInfoDto;
import org.zgl.player.UserMap;
import org.zgl.remote.IBackHall;
import org.zgl.remote.IProxy;
import org.zgl.utils.SpringUtils;
import org.zgl.utils.logger.LoggerUtils;

import java.util.List;

/**
 * @作者： big
 * @创建时间： 2018/6/15
 * @文件描述：
 */
@IProxy
public class BackHallImpl implements IBackHall {
    @Override
    public short backHall(List<PlayerInfoDto> weathDtos) {
        for(PlayerInfoDto r : weathDtos){
            UserMap userMap = SessionManager.getSession(r.getUid());
            if(userMap == null){
                return 404;
            }
            SQLWeathModel weathModel = userMap.getWeath();
            if(userMap.getGeneralizeUid() != 0){
                generalize(weathModel,r.getGold(),r.getUid());
            }
            r.infoToWeath(userMap);
            weathModel.updateResource(r.getGold(),r.getDiamond(),r.getIntegral());
            GiftBagManager.getInstance().executeTask(userMap);//成长礼包
            userMap.updateWeath();
        }
        return 200;
    }
    private void generalize(SQLWeathModel weathModel,long gold,long uid){
        long goldTemp = gold - weathModel.getGold();
        if(goldTemp > 0){
            IDBGeneralizeDao generalizeDao = SpringUtils.getBean(IDBGeneralizeDao.class);
            DBGeneralize generalize = generalizeDao.queryDBGeneralizeByTargetUid(uid);
            DBGeneralize dbGeneralize = new DBGeneralize();
            long t = (long) (goldTemp * 0.1);
            weathModel.reduceGold(t);
            dbGeneralize.setTargetUid(uid);
            dbGeneralize.setAward(generalize.getAward() + t);
            dbGeneralize.setAllAward(generalize.getAllAward() + t);
            generalizeDao.updateDBGeneralizeByTargetUid(dbGeneralize);
        }
    }
}
