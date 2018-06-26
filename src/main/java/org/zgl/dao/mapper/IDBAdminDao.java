package org.zgl.dao.mapper;

import org.zgl.dao.entity.DBAdmininfo;

/**
 * @作者： big
 * @创建时间： 2018/6/13
 * @文件描述：
 */
public interface IDBAdminDao {
    int insertDBAdmin(DBAdmininfo dbAdmininfo);
    int updateDBAdmin(DBAdmininfo dbAdmininfo);
    DBAdmininfo queryDBAdminBy();
    int deleteDBAdmin(Integer id);
}
