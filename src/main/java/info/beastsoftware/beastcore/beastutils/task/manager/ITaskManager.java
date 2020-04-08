package info.beastsoftware.beastcore.beastutils.task.manager;

import info.beastsoftware.beastcore.beastutils.task.INoBroadcastableTask;

public interface ITaskManager {

    void startTasks();

    INoBroadcastableTask getNoBroadcastTableTask(String taskType);


}
