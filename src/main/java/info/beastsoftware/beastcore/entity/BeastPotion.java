package info.beastsoftware.beastcore.entity;

import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

public class BeastPotion {

    private final int level;
    private final PotionType type;
    private final boolean extended;
    private int amount;


    public BeastPotion(int level, int amount, PotionType type, boolean extended) {
        this.level = level;
        this.amount = amount;
        this.type = type;
        this.extended = extended;
    }

    public static BeastPotion fromPotion(Potion potion) {
        return new BeastPotion(potion.getLevel(), 1, potion.getType(), potion.hasExtendedDuration());
    }

    public static BeastPotion fromItemStack(ItemStack itemStack) {
        BeastPotion beastPotion = fromPotion(Potion.fromItemStack(itemStack));
        beastPotion.setAmount(itemStack.getAmount());
        return beastPotion;
    }

    public boolean isExtended() {
        return extended;
    }

    public int getLevel() {
        return level;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public PotionType getType() {
        return type;
    }

    public ItemStack getItemStack() {
        Potion potion = new Potion(type, level);
        if (!potion.getType().isInstant())
            potion.setHasExtendedDuration(extended);
        return potion.toItemStack(amount);
    }


}
