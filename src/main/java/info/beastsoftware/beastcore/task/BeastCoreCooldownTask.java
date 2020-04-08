package info.beastsoftware.beastcore.task;

public abstract class BeastCoreCooldownTask implements IBeastCoreCooldownTask {


    private int left;

    public BeastCoreCooldownTask(int left) {
        this.left = left;
        this.iterate();
    }

    @Override
    public int getLeft() {
        return left;
    }

    @Override
    public void reduce() {
        this.left--;
    }

}
