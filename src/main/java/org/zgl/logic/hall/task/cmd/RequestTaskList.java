package org.zgl.logic.hall.task.cmd;

import org.zgl.dao.mapper.IUserDao;
import org.zgl.jetty.operation.OperateCommandAbstract;
import org.zgl.jetty.session.SessionManager;
import org.zgl.logic.hall.task.TaskListDto;
import org.zgl.logic.hall.task.po.SQLTaskModel;
import org.zgl.logic.hall.task.po.TaskModel;
import org.zgl.dao.entity.DBUser;
import org.zgl.player.UserMap;
import org.zgl.utils.DateUtils;
import org.zgl.utils.JsonUtils;
import org.zgl.utils.SpringUtils;
import org.zgl.utils.builder_clazz.ann.Protocol;

import java.util.ArrayList;
import java.util.List;

@Protocol("14")
public class RequestTaskList extends OperateCommandAbstract {
    public RequestTaskList(long uid) {
        super(uid);
    }

    @Override
    public Object execute() {
        UserMap userMap = SessionManager.getSession(getAccount());
        SQLTaskModel task = userMap.getTask();
        //每日任务清空
        if(DateUtils.currentDay() != task.getTime()) {
            task.getEveryDayTask().clear();
            task.setTime(DateUtils.currentDay());
            IUserDao userDao = SpringUtils.getBean(IUserDao.class);
            DBUser user = new DBUser();
            user.setUid(userMap.getUid());
            user.setTask(JsonUtils.jsonSerialize(userMap.getTask()));
            userDao.updateDBUser(user);
        }
        List<TaskModel> systm = new ArrayList<>(task.getSystemTask().values());
        List<TaskModel> everyDay = new ArrayList<>(task.getEveryDayTask().values());
        return new TaskListDto(systm,everyDay);
    }
}
