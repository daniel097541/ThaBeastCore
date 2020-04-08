package info.beastsoftware.beastcore.command;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.utils.StrUtils;
import info.beastsoftware.beastcore.entity.BeastPotion;
import info.beastsoftware.beastcore.struct.CommandType;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class PotionStackerCommand extends BeastCommand {

    public PotionStackerCommand(BeastCore plugin, IConfig config) {
        super(plugin, config, CommandType.POTION_STACKER, FeatureType.POTSTACKER);
        addAlias("stackpots");
        addAlias("potstacks");
    }

    @Override
    public void run(CommandSender sender, String[] args) {


        if (!(sender instanceof Player)) return;

        Inventory inventory = playerSender.getInventory();

        //no potions in the inventory
        if (!inventory.contains(Material.POTION)) {
            String err = config.getConfig().getString("Command.no-potions");
            playerSender.sms(err);
            return;
        }


        int cont = 0;


        List<BeastPotion> potions = new ArrayList<>();

        for (ItemStack itemStack : inventory) {

            if (itemStack == null || !itemStack.getType().equals(Material.POTION)) {
                cont++;
                continue;
            }

            Potion potion = Potion.fromItemStack(itemStack);

            if (potion.isSplash() && config.getConfig().getBoolean("stack-only-drinkable")) {
                cont++;
                continue;
            }

            PotionType type = potion.getType();

            if (!playerSender.hasPermission(config.getConfig().getString("Configuration.master-permission")) &&
                    !playerSender.hasPermission(config.getConfig().getString("Configuration." + type.name() + ".permission"))) {
                cont++;
                continue;
            }


            BeastPotion beastPotion = BeastPotion.fromItemStack(itemStack);

            potions.add(beastPotion);

            inventory.setItem(cont, null);
            cont++;
        }


        //group by potion types
        Map<PotionType, List<BeastPotion>> grouped = potions
                .parallelStream()
                .collect(Collectors.groupingBy(BeastPotion::getType));

        //for each potion type
        for (Map.Entry<PotionType, List<BeastPotion>> entry : grouped.entrySet()) {

            PotionType type = entry.getKey();
            List<BeastPotion> pots = entry.getValue();


            //max amount of this sort of potion
            int maxAmount = config.getConfig().getInt("Configuration." + type.name() + ".stack-size");


            //group by level
            Map<Integer, List<BeastPotion>> mapLevels = pots
                    .parallelStream()
                    .collect(Collectors.groupingBy(BeastPotion::getLevel));


            //for each grouped level
            for (Map.Entry<Integer, List<BeastPotion>> levelEntry : mapLevels.entrySet()) {

                int level = levelEntry.getKey();
                List<BeastPotion> mappedByLevel = levelEntry.getValue();

                Map<Boolean, List<BeastPotion>> mappedByExtended = mappedByLevel
                        .parallelStream()
                        .collect(Collectors.groupingBy(BeastPotion::isExtended));

                for (Map.Entry<Boolean, List<BeastPotion>> extendedEntry : mappedByExtended.entrySet()) {

                    boolean extended = extendedEntry.getKey();
                    List<BeastPotion> extendedList = extendedEntry.getValue();


                    //get total amount of potions with this level
                    int totalAmount = extendedList.
                            parallelStream()
                            .mapToInt(BeastPotion::getAmount)
                            .sum();


                    //divide in multiple stacks
                    if (totalAmount > maxAmount) {

                        //calculate how many stacks we have to add
                        int stacks = totalAmount / maxAmount;
                        int rest = totalAmount % maxAmount;

                        //add all stacks
                        for (int i = 0; i < stacks; i++) {
                            addPot(inventory, new BeastPotion(level, maxAmount, type, extended));
                        }

                        //add rest in case is more than 0
                        if (rest > 0) {
                            addPot(inventory, new BeastPotion(level, rest, type, extended));
                        }

                    }

                    //we dont have more than 1 stack, add the actual potion to the inventory
                    else {
                        addPot(inventory, new BeastPotion(level, totalAmount, type, extended));
                    }

                }
            }

        }


        String success = config.getConfig().getString("Command.potions-stacked");
        playerSender.sms(success);
    }


    private void addPot(Inventory inventory, BeastPotion potion) {
        inventory.addItem(potion.getItemStack());
    }


}



