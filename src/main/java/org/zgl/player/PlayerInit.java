package org.zgl.player;


import org.zgl.logic.hall.frineds.po.SQLFrinedsModel;
import org.zgl.logic.hall.giftBag.po.SQLGiftBagModel;
import org.zgl.logic.hall.siginin.po.SQLSignInModel;
import org.zgl.logic.hall.task.po.SQLTaskModel;
import org.zgl.logic.hall.weath.po.SQLWeathModel;
import org.zgl.logic.login.dto.LoginCmdBody;
import org.zgl.logic.login.po.SQLUserBaseInfo;
import org.zgl.dao.entity.DBUser;
import org.zgl.utils.IDFactory;
import org.zgl.utils.JsonUtils;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

/**
 * 初始化玩家数据
 */
public final class PlayerInit {
    private static final Properties pro = new Properties();
    static {
        try {
            pro.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("player_init.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public PlayerInit() {

    }
    public static final DBUser getUserInitInfo(LoginCmdBody cmdBody){
        DBUser u = new DBUser();
        u.setAccount(cmdBody.getAccount());
        u.setPassword(cmdBody.getPassword());
        u.setUserName(cmdBody.getUsername());
        Date date = new Date();
        u.setCreateTime(new java.sql.Date(date.getTime()));
        u.setLastEditTime(new java.sql.Date(date.getTime()));
        SQLUserBaseInfo baseInfo = new SQLUserBaseInfo(cmdBody.getLoginType(),cmdBody.getUsername(),cmdBody.getHeadIcon(),cmdBody.getGender(),"个人签名","联系方式",0,0);
        u.setBaseInfo(JsonUtils.jsonSerialize(baseInfo));
        u.setSignIn(JsonUtils.jsonSerialize(new SQLSignInModel()));
        u.setWeath(JsonUtils.jsonSerialize(new SQLWeathModel()));
        u.setFriends(JsonUtils.jsonSerialize(new SQLFrinedsModel()));
        u.setGiftBag(JsonUtils.jsonSerialize(new SQLGiftBagModel()));
        u.setTask(JsonUtils.jsonSerialize(new SQLTaskModel()));
        return u;
    }

    public static UserMap initUserMap(DBUser u){
        UserMap um = new UserMap();
        um.map2entity(u);
        return um;
    }
    public static void main(String[] args) {
//        User u = getUserInitInfo();
    }
}
