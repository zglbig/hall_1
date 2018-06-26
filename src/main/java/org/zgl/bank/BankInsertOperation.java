package org.zgl.bank;

import org.zgl.dao.entity.DBBank;
import org.zgl.dao.mapper.IDBBankDao;
import org.zgl.error.AppErrorCode;
import org.zgl.error.GenaryAppError;
import org.zgl.jetty.operation.OperateCommandAbstract;
import org.zgl.jetty.session.SessionManager;
import org.zgl.logic.hall.weath.po.SQLWeathModel;
import org.zgl.player.UserMap;
import org.zgl.utils.SpringUtils;
import org.zgl.utils.builder_clazz.ann.Protocol;

/**
 * @作者： big
 * @创建时间： 2018/6/15
 * @文件描述：
 */
@Protocol("31")
public class BankInsertOperation extends OperateCommandAbstract {
    /**1 存钱 2 取钱*/
    private final int operationType;
    private final long gold;
    public BankInsertOperation(long uid, int operationType, long gold) {
        super(uid);
        this.operationType = operationType;
        this.gold = gold;
    }

    @Override
    public Object execute() {
        switch (operationType){
            case 1:
                return save();
            case 2:
                return draw();
        }
        return null;
    }
    private BankDto save(){
        IDBBankDao bankDao = SpringUtils.getBean(IDBBankDao.class);
        UserMap userMap = SessionManager.getSession(getAccount());
        DBBank bank = bankDao.queryDBBankByUid(userMap.getUid());
        if(bank == null){
            bank = new DBBank();
            bank.setUid(userMap.getUid());
            bank.setGold(0L);
            bankDao.insertDBBank(bank);
        }
        SQLWeathModel weath = userMap.getWeath();
        if(!weath.reduceGold(gold))
            new GenaryAppError(AppErrorCode.GOLD_NOT_ERR);
        userMap.updateWeath();
        bank.setGold(bank.getGold() + gold);
        bankDao.updateDBBank(bank);
        return new BankDto(weath.getGold());
    }
    //取钱
    private BankDto draw(){
        IDBBankDao bankDao = SpringUtils.getBean(IDBBankDao.class);
        UserMap userMap = SessionManager.getSession(getAccount());
        DBBank bank = bankDao.queryDBBankByUid(userMap.getUid());
        if(bank == null)
            new GenaryAppError(AppErrorCode.BANK_NOT_GOLD);
        if(bank.getGold() < gold)
            new GenaryAppError(AppErrorCode.BANK_GOLD_INSUFFICIENT);
        SQLWeathModel weath = userMap.getWeath();
        weath.addGoldOrDiamond(1,gold);
        userMap.updateWeath();
        bank.setGold(bank.getGold() - gold);
        bankDao.updateDBBank(bank);
        return new BankDto(weath.getGold());
    }
}
