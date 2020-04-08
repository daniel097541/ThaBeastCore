package info.beastsoftware.beastcore.manager;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.annotations.features.Grace;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.task.GraceTask;

@Singleton
public class GraceManager implements IGraceManager {


    private final GraceTask task;


    @Inject
    public GraceManager(@Grace IConfig config, BeastCore plugin) {
        this.task = new GraceTask(config, plugin);
        if(this.task.isRunning()){
            this.task.startTask();
        }
    }


    @Override
    public void startGrace() {

        //start grace


        //broadcast everyone

    }

    @Override
    public void pauseGrace() {

        //pause garce


        //broadcast if enabled

    }

    @Override
    public void resumeGrace() {

        //resume

        //broadcast if enabled


    }

    @Override
    public void endGrace() {


    }

    @Override
    public void resetGrace() {

    }
}
