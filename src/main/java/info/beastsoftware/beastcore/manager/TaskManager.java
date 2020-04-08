package info.beastsoftware.beastcore.manager;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.task.INoBroadcastableTask;
import info.beastsoftware.beastcore.beastutils.task.manager.ITaskManager;
import info.beastsoftware.beastcore.configuration.manager.IConfigManager;

import java.util.HashMap;


@Singleton
public class TaskManager implements ITaskManager {

    private HashMap<String, IConfig> configs;
    private HashMap<String, INoBroadcastableTask> noBroadcastableTaskHashMap;

    @Inject
    public TaskManager(IConfigManager configManager) {
        this.configs = configManager.getConfigs();
        this.startTasks();
    }

    @Override
    public void startTasks() {
        this.noBroadcastableTaskHashMap = new HashMap<>();
    }

    @Override
    public INoBroadcastableTask getNoBroadcastTableTask(String taskType) {
        return noBroadcastableTaskHashMap.get(taskType);
    }
}
