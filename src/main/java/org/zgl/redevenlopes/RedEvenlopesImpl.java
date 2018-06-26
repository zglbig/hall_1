package org.zgl.redevenlopes;

import org.zgl.dao.entity.DBRedEvenlopes;
import org.zgl.dao.mapper.IDBRedEvenlopesDao;
import org.zgl.remote.IProxy;
import org.zgl.remote.IRedEvenlopes;
import org.zgl.utils.SpringUtils;

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
        return dao.insertRedEvenlopes(evenlopes);
    }

    @Override
    public int updateRedEvenlopes(DBRedEvenlopes evenlopes) {
        IDBRedEvenlopesDao dao = SpringUtils.getBean(IDBRedEvenlopesDao.class);
        return dao.updateRedEvenlopes(evenlopes);
    }

    @Override
    public DBRedEvenlopes queryRedEvenlopesById(Integer id) {
        IDBRedEvenlopesDao dao = SpringUtils.getBean(IDBRedEvenlopesDao.class);
        return dao.queryRedEvenlopesById(id);
    }

    @Override
    public DBRedEvenlopes queryRedEvenlopesLastId() {
        IDBRedEvenlopesDao dao = SpringUtils.getBean(IDBRedEvenlopesDao.class);
        return dao.queryRedEvenlopesLastId();
    }

    @Override
    public List<DBRedEvenlopes> queryRedEvenlopesList(Integer id) {
        IDBRedEvenlopesDao dao = SpringUtils.getBean(IDBRedEvenlopesDao.class);
        return dao.queryRedEvenlopesList(id);
    }

    @Override
    public int deleteRedEvenlopes(Integer id) {
        IDBRedEvenlopesDao dao = SpringUtils.getBean(IDBRedEvenlopesDao.class);
        return dao.deleteRedEvenlopes(id);
    }
}
