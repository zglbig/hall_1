package org.zgl.dao.mapper;

import org.zgl.dao.entity.DBUser;

import java.util.List;

/**
 * @作者： big
 * @创建时间： 2018/6/13
 * @文件描述：
 */
public interface IUserDao {
    DBUser queryDBUserByAccount(String account);
    DBUser queryDBUserByUid(Long uid);
    DBUser queryDBUserById(Integer id);
    List<DBUser> queryDBUserList(Integer id);
    int updateDBUser(DBUser user);
    int insertDBUser(DBUser user);
    int deleteDBUser(Long uid);
}
