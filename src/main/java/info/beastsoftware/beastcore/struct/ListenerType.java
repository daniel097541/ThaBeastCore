package info.beastsoftware.beastcore.struct;

public enum ListenerType {


    COLOR_ANVIL("Color rename with anvils", ConfigType.COLOR_ANVIL, "enabled"),
    LAPIS_FILLER("Lapis lazuli filler", ConfigType.MAIN_CONFIG, "enabled"),
    TICK_COUNTER("Tick Counter", ConfigType.TICK_COUNTER, "enabled"),
    ITEMFILTER("ItemFilter", ConfigType.ITEMFILTER, "Item-Filter.Command.enabled"),
    EXP_WITHDRAW("Experience withdraw", ConfigType.EXP_BOTTLE, "Settings.enabled"),
    ITEM_BURN("No Item Burn", ConfigType.MAIN_CONFIG, "Anti-Item-burn.enabled"),
    SPAWNER_GUARD("No Spawner Guard", ConfigType.MAIN_CONFIG, "Anti-Spawner-Guard.enabled"),
    SPAWNER_BREAK("Spawner Raiding Protection", ConfigType.MAIN_CONFIG, "Anti-Spawner-Mine.enabled"),
    PEARL_GLITCH("No Pearl Giltching", ConfigType.MAIN_CONFIG, "Pearl-Glitch-Fix.enabled"),
    WB_PEARL("No WorldBorder Pearl", ConfigType.MAIN_CONFIG, "Deny-Enderpearl-Outside-Border.enabled"),
    BOAT_PLACE("No Boat Place", ConfigType.MAIN_CONFIG, "Anti-Boat-Place.enabled"),
    CREEPER_SPAWN("No creeper Glitching", ConfigType.MAIN_CONFIG, "Anti-Creeper-Glitch.enabled"),
    HORSE_MOUNT("No Horse Mount", ConfigType.MAIN_CONFIG, "Anti-Horse-Mount.enabled"),
    IRONGOLEM_BURN("IronGolem Fast Death", ConfigType.MAIN_CONFIG, "Irongolem-Fast-Death.enabled"),
    BOTTLE_RECYCLE("Bottle Recycle", ConfigType.MAIN_CONFIG, "Bottle-Recycling.enabled"),
    HIDE_STREAM("Hide Join Messages", ConfigType.MAIN_CONFIG, "Hide-Stream.enabled"),
    SAND_STACKING("Sand Stacking Fix", ConfigType.MAIN_CONFIG, "Sand-Stacking-Fix.enabled"),
    FACTION_LOGOUT("No Enemy LogOut", ConfigType.MAIN_CONFIG, "Faction-Logout.enabled"),
    NO_WATER_REDSTONE("No Water Redstone", ConfigType.MAIN_CONFIG, "Anti-Water-Redstone.enabled"),
    GAPPLE_COOLDOWN("Gapple BeastCooldown", ConfigType.MAIN_CONFIG, "Gapple-BeastCooldown.enabled"),
    NORMAL_GAPPLE_COOLDOWN("Normal Gapple BeastCooldown", ConfigType.MAIN_CONFIG, "Normal-Gapple-BeastCooldown.enabled"),
    ENDERPEARL_COOLDOWN("EnderPearl BeastCooldown", ConfigType.MAIN_CONFIG, "EnderPearl-BeastCooldown.enabled"),
    LAVA_SPONGE("Lava Sponge", ConfigType.MAIN_CONFIG, "Lava-Sponge.enabled"),
    WATER_SPONGE("Water Sponge", ConfigType.MAIN_CONFIG, "Water-Sponge.enabled"),
    EXPLODE_LAVA("Explode Lava", ConfigType.MAIN_CONFIG, "Explode-Lava.enabled"),
    ANTI_SCHEMATICA_BUG("No Schematica Bug", ConfigType.MAIN_CONFIG, "Anti-Schem-Bug.enabled"),
    ANTI_FALL_DAMAGE("JellyLegs", ConfigType.MAIN_CONFIG, "Commands.Jelly-Legs.enabled"),
    NO_WEATHER("No Weather", ConfigType.MAIN_CONFIG, "Anti-Weather.enabled"),
    NO_ITEM_SPAWN("No poppy flowers", ConfigType.MAIN_CONFIG, "Deny-Item-Spawn.enabled"),
    SERVER_PROTECTOR("Server Protector", ConfigType.MAIN_CONFIG, "Server-Protector.enabled"),
    AUTO_CANNONS_LIMITER("Auto Cannons Limiter", ConfigType.MAIN_CONFIG, "Auto-Cannons-Limiter.enabled"),
    NO_BOOM("No Explosions", ConfigType.MAIN_CONFIG, "No-Explosions.enabled"),
    NO_BORDER_STACK("No WorldBorder Stacking", ConfigType.MAIN_CONFIG, "Prevent-Sand-Stacking-In-Worldborder.enabled"),
    ANTI_VOID_FALL("Anti Void Fall", ConfigType.MAIN_CONFIG, "Anti-Void-Falling.enabled"),
    NO_EXPLOSIONS_DAMAGE("No Explosions Damage", ConfigType.MAIN_CONFIG, "No-Explosions-Damage.enabled"),
    CANNON_PROTECTOR("No cannon water", ConfigType.MAIN_CONFIG, "Cannon-Protector.enabled"),
    WATERPROOF_BLAZES("Water Proof Blazes", ConfigType.MAIN_CONFIG, "Water-Proof-Blazes.enabled"),
    NO_WATER_FLOW_OUTSIDE_BORDER("No Water Flow Outside Border", ConfigType.MAIN_CONFIG, "No-Water-Flow-Outside-Border.enabled"),
    IRONGOLEM_SPAWN_ON_FIRE("Iron Golem Spawn On Fire", ConfigType.MAIN_CONFIG, "Iron-Golem-Spawn-On-Fire.enabled"),
    NO_FLYING_PEARL("No Flying Pearl", ConfigType.MAIN_CONFIG, "Anti-fly-Pearl.enabled"),
    NO_MCMMO_PISTON("No mcmmo piston dupe", ConfigType.MAIN_CONFIG, "No-Piston-McMMO.enabled"),
    COMBAT("Combat", ConfigType.COMBAT_CONFIG, "Combat-Tag.enabled"),
    MERGER("Merger", ConfigType.MERGE_CONFIG, "Mob-Merger.enabled"),
    EDIT_DROPS("Drops", ConfigType.DROPS_CONFIG, "Edit-Drops.enabled"),
    ALERTS("Alerts", ConfigType.ALERTS_CONFIG, "Faction-Alerts.enabled"),
    LWANDS("Lwands", ConfigType.BEAST_TOOLS_CONFIG, "Lightning-Tools.enabled"),
    NOITEMCRAFT("NoItemCraft", ConfigType.ANTIITEMCRAFT_CONFIG, "Anti-Item-Craft.enabled"),
    HOPPERFILTER("HopperFilter", ConfigType.HOPPER_FILTER_CONFIG, "Hopper-Filter.enabled"),
    GRACE("Grace", ConfigType.GRACE, "Grace-Period.enabled"),
    C_HOPPERS("CropHoppers", ConfigType.CROP_HOPPERS, "Crop-Hoppers.enabled"),
    M_HOPPERS("MobHoppers", ConfigType.MOB_HOPPERS, "Mob-Hoppers.enabled"),
    CHUNKBUSTER("Chunkbuster", ConfigType.CHUNKBUSTER, "Chunk-Busters.enabled"),
    VOID_CHEST("VoidChest", ConfigType.VOID_CHESTS, "Void-Chests.enabled"),
    FPS_BOOSTER("FPS Booster", ConfigType.FPS_BOOSTER, "FPS-Booster.enabled"),
    PRINTER_MODE("Printer mode", ConfigType.PRINTER_CONFIG, "enabled"),
    WITHDRAW_MONEY("Money withdraw", ConfigType.WITHDRAW_CONFIG, "enabled"),
    WILDERNESS("Wilderness", ConfigType.MAIN_CONFIG, "Commands.Wilderness.enabled"),
    NO_ENEMY_TELEPORT("No enemy teleport", ConfigType.MAIN_CONFIG, "No-Enemy-Teleport.enabled");


    private String name;
    private ConfigType configType;
    private String enabledPath;

    ListenerType(String name, ConfigType type, String enabledPath) {
        this.name = name;
        this.configType = getConfigType();
        this.enabledPath = enabledPath;
        this.configType = type;
    }

    public String getName() {
        return name;
    }

    public ConfigType getConfigType() {
        return configType;
    }

    public String getEnabledPath() {
        return enabledPath;
    }
}
