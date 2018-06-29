package org.zgl.dao.mapper;

import org.zgl.redenvelope.dto.DBRedEvenlopes;

import java.util.List;

/**
 * @作者： big
 * @创建时间： 2018/6/23
 * @文件描述：
 */
public interface IDBRedEvenlopesDao {
    int insertRedEvenlopes(DBRedEvenlopes evenlopes);
    int updateRedEvenlopes(DBRedEvenlopes evenlopes);
    DBRedEvenlopes queryRedEvenlopesLastId();
    DBRedEvenlopes queryRedEvenlopesById(Integer id);
    List<DBRedEvenlopes> queryRedEvenlopesList(Integer id);
    int deleteRedEvenlopes(Integer id);
}
