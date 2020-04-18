package info.beastsoftware.beastcore.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class StackedMobDiedResponse {

    private final List<ItemStack> drops;
    private final int exp;
    private final boolean stacked;

}
