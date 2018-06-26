package org.zgl.player;


import org.zgl.dao.mapper.IUserDao;
import org.zgl.logic.hall.frineds.po.SQLFrinedsModel;
import org.zgl.logic.hall.giftBag.po.SQLGiftBagModel;
import org.zgl.logic.hall.siginin.po.SQLSignInModel;
import org.zgl.logic.hall.task.po.SQLTaskModel;
import org.zgl.logic.hall.weath.po.ResourceModel;
import org.zgl.logic.hall.weath.po.SQLWeathModel;
import org.zgl.logic.login.po.SQLUserBaseInfo;
import org.zgl.orm.core.Query;
import org.zgl.orm.core.QueryFactory;
import org.zgl.dao.entity.DBUser;
import org.zgl.utils.DateUtils;
import org.zgl.utils.JsonUtils;
import org.zgl.utils.SpringUtils;
import org.zgl.utils.executer.Worker;

import java.util.ArrayList;
import java.util.List;

public class UserMap {
    private int scenesId;
    private int id;
    private long uid;
    private String account;
    private String password;
    private long generalizeUid;
    private SQLUserBaseInfo baseInfo;
    private SQLSignInModel signIn;
    private SQLWeathModel weath;
    private SQLFrinedsModel friends;
    private SQLGiftBagModel giftBag;
    private SQLTaskModel task;
    private long loginTime;
    private Worker worker;
    public UserMap() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public SQLUserBaseInfo getBaseInfo() {
        return baseInfo;
    }

    public long getGeneralizeUid() {
        return generalizeUid;
    }

    public void setGeneralizeUid(long generalizeUid) {
        this.generalizeUid = generalizeUid;
    }

    public void setBaseInfo(SQLUserBaseInfo baseInfo) {
        this.baseInfo = baseInfo;
    }

    public SQLGiftBagModel getGiftBag() {
        return giftBag;
    }

    public void setGiftBag(SQLGiftBagModel giftBag) {
        this.giftBag = giftBag;
    }

    public SQLSignInModel getSignIn() {
        return signIn;
    }

    public void setSignIn(SQLSignInModel signIn) {
        this.signIn = signIn;
    }

    public SQLWeathModel getWeath() {
        return weath;
    }
    private void checkAutos(){
        //检查座驾是否过期
        List<ResourceModel> autos = weath.getAutos();
        if(autos != null || autos.size() != 0){
            List<ResourceModel> index = new ArrayList<>(autos.size());
            long current = DateUtils.currentTime();
            for(ResourceModel r:autos){
                long createTime = r.getCreateTime();
                int day = (int) ((current - createTime)/(1000 * 60 * 60 * 24));
                if(day >= 30){
                    index.add(r);
                }
            }
            if(index.size() > 0){
                for(ResourceModel r : index){
                    if(weath.getAuto() == r.getId()){
                        weath.setAuto(0);
                    }
                    autos.remove(r);
                }
                if(weath.getAuto() == 0 && autos != null && autos.size() > 0){
                    weath.setAuto(autos.get(0).getId());
                }
                updateWeath();
            }
        }
    }
    public void setWeath(SQLWeathModel weath) {
        this.weath = weath;
    }

    public SQLFrinedsModel getFriends() {
        return friends;
    }

    public void setFriends(SQLFrinedsModel friends) {
        this.friends = friends;
    }

    public SQLTaskModel getTask() {
        return task;
    }

    public void setTask(SQLTaskModel task) {
        this.task = task;
    }

    public long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(long loginTime) {
        this.loginTime = loginTime;
    }

    public int getScenesId() {
        return scenesId;
    }

    public void setScenesId(int scenesId) {
        this.scenesId = scenesId;
    }

    public Worker getWorker() {
        return worker;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    /**
     * 实体转换成映射类
     * @return 数据库存储类
     */
    public DBUser entity2map(){
        DBUser u = new DBUser();
        u.setId(id);
        u.setUid(uid);
        u.setAccount(account);
        u.setPassword(password);
        u.setGeneralizeUid(generalizeUid);
        u.setBaseInfo(JsonUtils.jsonSerialize(baseInfo));
        u.setWeath(JsonUtils.jsonSerialize(weath));
        u.setFriends(JsonUtils.jsonSerialize(friends));
        u.setSignIn(JsonUtils.jsonSerialize(signIn));
        u.setGiftBag(JsonUtils.jsonSerialize(giftBag));
        u.setTask(JsonUtils.jsonSerialize(task));
        return u;
    }

    /**
     * 将数据库类换成实体类
     * @return
     */
    public UserMap map2entity(DBUser u){
        this.id = u.getId();
        this.account = u.getAccount();
        this.password = u.getPassword();
        this.uid = u.getUid();
        if(u.getGeneralizeUid() != null) {
            this.generalizeUid = u.getGeneralizeUid();
        }
        this.baseInfo = JsonUtils.jsonDeserialization(u.getBaseInfo(),SQLUserBaseInfo.class);
        this.signIn = JsonUtils.jsonDeserialization(u.getSignIn(),SQLSignInModel.class);
        this.weath = JsonUtils.jsonDeserialization(u.getWeath(),SQLWeathModel.class);
        this.friends = JsonUtils.jsonDeserialization(u.getFriends(),SQLFrinedsModel.class);
        this.giftBag = JsonUtils.jsonDeserialization(u.getGiftBag(),SQLGiftBagModel.class);
        this.task = JsonUtils.jsonDeserialization(u.getTask(),SQLTaskModel.class);
        this.loginTime = DateUtils.currentTime();
        checkAutos();
        return this;
    }
    public UserMap systemUser(){
        this.id = -999999999;
        this.account = "-999999999";
        this.password = "-999999999";
        this.baseInfo = new SQLUserBaseInfo(0,"房间账号链接","a","妹子","个人签名","联系方式",0,0);
        return this;
    }
    public void update(DBUser user){
        IUserDao iUserDao = SpringUtils.getBean(IUserDao.class);
        iUserDao.updateDBUser(user);
    }
    public void updateWeath(){
        IUserDao iUserDao = SpringUtils.getBean(IUserDao.class);
        DBUser dbUser = new DBUser();
        dbUser.setId(this.id);
        dbUser.setWeath(JsonUtils.jsonSerialize(this.weath));
        iUserDao.updateDBUser(dbUser);
    }
}
