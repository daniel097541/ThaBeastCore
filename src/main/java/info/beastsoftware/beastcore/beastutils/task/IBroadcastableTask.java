package info.beastsoftware.beastcore.beastutils.task;

public interface IBroadcastableTask {

    int getLeft();

    void startTask();

    void cancelTask();

    void endTask();

    void callUpdateEvent();

    void callCancelEvent();

    void callEndEvent();

}
