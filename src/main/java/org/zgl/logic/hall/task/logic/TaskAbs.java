package org.zgl.logic.hall.task.logic;

import org.zgl.error.AppErrorCode;
import org.zgl.error.GenaryAppError;
import org.zgl.logic.hall.task.TaskDataTable;
import org.zgl.logic.hall.task.manager.ITask;
import org.zgl.logic.hall.task.po.SQLTaskModel;
import org.zgl.logic.hall.task.po.TaskModel;
import org.zgl.logic.hall.weath.po.SQLWeathModel;
import org.zgl.orm.core.Query;
import org.zgl.orm.core.QueryFactory;
import org.zgl.dao.entity.DBUser;
import org.zgl.player.UserMap;
import org.zgl.utils.JsonUtils;

public abstract class TaskAbs implements ITask {
    @Override
    public void update(UserMap userMap) {
        DBUser user = new DBUser();
        user.setId(userMap.getId());
        user.setTask(JsonUtils.jsonSerialize(userMap.getTask()));
        userMap.update(user);
    }
    @Override
    public void getAward(UserMap userMap, int taskId) {
        TaskDataTable dataTable = TaskDataTable.get(taskId);
        if(dataTable == null)
            new GenaryAppError(AppErrorCode.SERVER_ERR);
        SQLTaskModel task = userMap.getTask();
        TaskModel model = task.getTaskModel(dataTable.getTaskType(),taskId+"");
        if(model.isHasDone() && !model.isHasAward()){
            SQLWeathModel weath = userMap.getWeath();
            weath.addGoldOrDiamond(1,dataTable.getAward());
            model.setHasAward(true);//领取奖励成功
            userMap.updateWeath();
        }else {
            new GenaryAppError(AppErrorCode.TASK_NOT_DONE);
        }
    }
}
