package info.beastsoftware.beastcore.beastutils.task;

public interface INoBroadcastableTask {


    void initTask();

    void cancelTask();

    int getLeft();

    void endTask();

    void toggle();

    boolean isRunning();

    void callEndEvent();

    void callCancelEvent();

    void callStartEvent();
}
