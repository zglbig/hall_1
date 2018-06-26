package org.zgl.bank;

import org.zgl.dao.entity.DBBank;
import org.zgl.dao.mapper.IDBBankDao;
import org.zgl.error.AppErrorCode;
import org.zgl.error.GenaryAppError;
import org.zgl.jetty.operation.OperateCommandAbstract;
import org.zgl.utils.SpringUtils;
import org.zgl.utils.builder_clazz.ann.Protocol;

/**
 * @作者： big
 * @创建时间： 2018/6/15
 * @文件描述：
 */
@Protocol("30")
public class BankInfoOperation extends OperateCommandAbstract {
    public BankInfoOperation(long uid) {
        super(uid);
    }

    @Override
    public Object execute() {
        IDBBankDao bankDao = SpringUtils.getBean(IDBBankDao.class);
        DBBank bank = bankDao.queryDBBankByUid(getAccount());
        if(bank == null)
            return new BankDto(0);
        return new BankDto(bank.getGold());
    }
}
