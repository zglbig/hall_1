package org.zgl.jetty.session;

import org.zgl.player.UserMap;
import org.zgl.utils.DateUtils;
import org.zgl.utils.logger.LoggerUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 作者： 白泽
 * 时间： 2017/12/1.
 * 描述：
 */
public class SessionManager {
    private static final ConcurrentHashMap<Long, UserMap> onlineSessions = new ConcurrentHashMap<>();
    public static boolean putSession(long uid,UserMap userMap){
        boolean success = false;
        if(!onlineSessions.containsKey(uid))
            success = onlineSessions.putIfAbsent(uid,userMap) == null ? true : false;
        return success;
    }
    public static UserMap removeSession(long uid){
        if(!onlineSessions.containsKey(uid)) return null;
        UserMap session = onlineSessions.remove(uid);
        return session;
    }
    public static UserMap getSession(long uid){
        return onlineSessions.getOrDefault(uid,null);
    }
    /**
     * 是否在线
     * @param
     * @return
     */
    public static boolean isOnlinePlayer(long uid){
        return onlineSessions.containsKey(uid);
    }

    /**
     * 获取所有在线玩家
     * @return
     */
    public static Set<Long> onlinePlayers() {
        return Collections.unmodifiableSet(onlineSessions.keySet());
    }
    public static int onLinePlayerNum(){
        return onlineSessions == null ? 0 : onlineSessions.size();
    }
    public static Map<Long,UserMap> map(){
        return onlineSessions;
    }
    static class FutureThread implements Runnable{
        @Override
        public void run() {
            Set<Long> accounts = new HashSet<>();
            for(Map.Entry<Long,UserMap> e : onlineSessions.entrySet()){
                if(DateUtils.currentDay() - e.getValue().getLoginTime() >= 1800000){
                    accounts.add(e.getKey());
                }
            }
            //长时间不在线系统自动删除
            Iterator<Long> iterator = accounts.iterator();
            while (iterator.hasNext()){
                long account = iterator.next();
                onlineSessions.remove(account);
            }
        }
    }
    static {
        FutureThread future = new FutureThread();
        Thread t = new Thread(future);
        t.setDaemon(true);//设置为守护线程
        t.start();
    }
}
