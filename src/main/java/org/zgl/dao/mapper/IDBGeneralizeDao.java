package org.zgl.dao.mapper;

import org.zgl.dao.entity.DBGeneralize;

import java.util.List;

/**
 * @作者： big
 * @创建时间： 2018/6/16
 * @文件描述：
 */
public interface IDBGeneralizeDao {
    List<DBGeneralize> queryDBGeneralizeByUid(Long uid);
    DBGeneralize queryDBGeneralizeByTargetUid(Long uid);
    int insertDBGeneralize(DBGeneralize generalize);
    int updateDBGeneralize(DBGeneralize generalize);
    int updateDBGeneralizeByTargetUid(DBGeneralize generalize);
    int deleteDBGeneralizeByUid(Long uid);
}
