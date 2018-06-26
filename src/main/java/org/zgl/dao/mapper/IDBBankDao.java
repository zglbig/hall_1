package org.zgl.dao.mapper;

import org.zgl.dao.entity.DBBank;

/**
 * @作者： big
 * @创建时间： 2018/6/15
 * @文件描述：
 */
public interface IDBBankDao {
    DBBank queryDBBankByUid(Long uid);
    int insertDBBank(DBBank bank);
    int updateDBBank(DBBank bank);
    int deleteDBBankByUid(Long uid);
}
