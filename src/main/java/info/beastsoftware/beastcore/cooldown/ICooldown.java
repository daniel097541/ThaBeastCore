package info.beastsoftware.beastcore.cooldown;

public interface ICooldown {

    void decrementCooldowns();

    void addCooldown(String player, int cooldown);

    void endCooldown(String player);

    boolean isOnCooldown(String player);

    int getCooldown(String player);

    void reload();

}
