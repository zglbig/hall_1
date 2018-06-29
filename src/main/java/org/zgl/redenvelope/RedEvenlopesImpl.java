package org.zgl.redenvelope;

import org.zgl.dao.entity.DBUser;
import org.zgl.dao.mapper.IUserDao;
import org.zgl.error.AppErrorCode;
import org.zgl.error.GenaryAppError;
import org.zgl.jetty.session.SessionManager;
import org.zgl.logic.hall.weath.po.SQLWeathModel;
import org.zgl.player.UserMap;
import org.zgl.redenvelope.dto.DBRedEvenlopes;
import org.zgl.dao.mapper.IDBRedEvenlopesDao;
import org.zgl.redenvelope.dto.FriendRedEnvelopesDto;
import org.zgl.redenvelope.dto.RedEnvelopesDto;
import org.zgl.remote.IProxy;
import org.zgl.remote.IRedEvenlopes;
import org.zgl.utils.DateUtils;
import org.zgl.utils.JsonUtils;
import org.zgl.utils.SpringUtils;
import org.zgl.utils.builder_clazz.excel_init_data.ExcelUtils;
import org.zgl.utils.logger.LoggerUtils;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @作者： big
 * @创建时间： 2018/6/23
 * @文件描述：
 */
@IProxy
public class RedEvenlopesImpl implements IRedEvenlopes {
    @Override
    public int insertRedEvenlopes(DBRedEvenlopes evenlopes) {
        IDBRedEvenlopesDao dao = SpringUtils.getBean(IDBRedEvenlopesDao.class);
        try {
            dao.insertRedEvenlopes(evenlopes);
            return evenlopes.getId();//返回插入的红包id
        } catch (Exception e) {
            LoggerUtils.getLogicLog().info(e);
            new GenaryAppError(AppErrorCode.DATA_ERR);
        }
        return 0;
    }

    @Override
    public int updateRedEvenlopes(DBRedEvenlopes evenlopes) {
        IDBRedEvenlopesDao dao = SpringUtils.getBean(IDBRedEvenlopesDao.class);
        try {
            return dao.updateRedEvenlopes(evenlopes);
        } catch (Exception e) {
            LoggerUtils.getLogicLog().info(e);
            new GenaryAppError(AppErrorCode.DATA_ERR);
        }
        return 0;
    }

    @Override
    public DBRedEvenlopes queryRedEvenlopesById(Integer id) {
        IDBRedEvenlopesDao dao = SpringUtils.getBean(IDBRedEvenlopesDao.class);
        try {
            return dao.queryRedEvenlopesById(id);
        } catch (Exception e) {
            LoggerUtils.getLogicLog().info(e);
            new GenaryAppError(AppErrorCode.DATA_ERR);
        }
        return null;
    }

    @Override
    public DBRedEvenlopes queryRedEvenlopesLastId() {
        IDBRedEvenlopesDao dao = SpringUtils.getBean(IDBRedEvenlopesDao.class);
        try {
            return dao.queryRedEvenlopesLastId();
        } catch (Exception e) {
            LoggerUtils.getLogicLog().info(e);
            new GenaryAppError(AppErrorCode.DATA_ERR);
        }
        return null;
    }

    @Override
    public RedEnvelopesDto queryRedEvenlopesList(Integer id) {
        IDBRedEvenlopesDao dao = SpringUtils.getBean(IDBRedEvenlopesDao.class);
        List<DBRedEvenlopes> list = null;
        try {
            list = dao.queryRedEvenlopesList(id);
        } catch (Exception e) {
            LoggerUtils.getLogicLog().info(e);
            new GenaryAppError(AppErrorCode.DATA_ERR);
        }
        List<DBRedEvenlopes> dbRedEvenlopesList = new ArrayList<>();
        if (list != null || list.size() > 0) {
            for (int i = list.size() - 1; i >= 0; i--) {
                if (dbRedEvenlopesList.size() >= 10)
                    break;
                dbRedEvenlopesList.add(list.get(i));
            }
        }
        return new RedEnvelopesDto(dbRedEvenlopesList);
    }

    @Override
    public int deleteRedEvenlopes(Integer id) {
        IDBRedEvenlopesDao dao = SpringUtils.getBean(IDBRedEvenlopesDao.class);
        try {
            return dao.deleteRedEvenlopes(id);
        } catch (Exception e) {
            LoggerUtils.getLogicLog().info(e);
            new GenaryAppError(AppErrorCode.DATA_ERR);
        }
        return 0;
    }

    @Override
    public int handOutRedEnvelopes(Long uid, FriendRedEnvelopesDto friendRedEnvelopesDto) {
        UserMap userMap = SessionManager.getSession(uid);
        List<FriendRedEnvelopesDto> f = userMap.getFriendRedEnvelopes();
        if (f == null)
            f = new ArrayList<>();
        f.add(friendRedEnvelopesDto);
        Collections.sort(f);
        DBUser dbUser = new DBUser();
        dbUser.setId(userMap.getId());
        dbUser.setFriendRedEnvelopes(JsonUtils.jsonSerialize(f));
        userMap.update(dbUser);
        return 0;
    }

    @Override
    public int updateRedEnvelopes(Long uid, Integer id) {
        UserMap userMap = SessionManager.getSession(uid);
        List<FriendRedEnvelopesDto> f = userMap.getFriendRedEnvelopes();
        if (f == null && f.size() == 0)
            new GenaryAppError(AppErrorCode.DATA_ERR);
        FriendRedEnvelopesDto fd = null;
        for (FriendRedEnvelopesDto ff : f) {
            if (ff.getId() == id) {
                fd = ff;
                break;
            }
        }
        if (fd == null || fd.isHasGet())
            new GenaryAppError(AppErrorCode.DATA_ERR);
        SQLWeathModel weath = userMap.getWeath();
        weath.addGoldOrDiamond(1, fd.getGold());
        fd.setGold(0);
        fd.setHasGet(true);
        fd.setLastEditTime(DateUtils.currentTime());

        if (f.size() >= 15) {
            for (FriendRedEnvelopesDto freld : f) {
                if (freld.isHasGet()){
                    f.remove(freld);
                    if(f.size() <= 10)
                        break;
                }
            }
        }
        DBUser dbUser = new DBUser();
        dbUser.setId(userMap.getId());
        dbUser.setWeath(JsonUtils.jsonSerialize(weath));
        dbUser.setFriendRedEnvelopes(JsonUtils.jsonSerialize(f));
        userMap.update(dbUser);
        return 0;
    }

    @Override
    public List<FriendRedEnvelopesDto> friendRedEnvelopesList(Long uid) {
        return null;
    }
}
