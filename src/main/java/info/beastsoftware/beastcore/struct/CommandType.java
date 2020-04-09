package info.beastsoftware.beastcore.struct;

public enum CommandType {
    THROWABLE_EGGS("throwableggs", "enabled", "command.permission", "command.no-permission-message", null),
    NV("nv", "enabled", "permission", "no-permission-message", null),
    DEBUFF("debuff", "enabled", "permission", "no-permission-message", null),
    TOGGLE_DEATHSCREEN("autospawn", "enabled", "Command.permission", "Command.no-permission-message", null),
    POTION_STACKER("potstack", "Command.enabled", "Command.permission", "Command.no-permission-message", ConfigType.POTION_STACKER),
    TICK_COUNTER("tickcounter", "Command.enabled", "Command.permission", "Command.no-permission-message", ConfigType.TICK_COUNTER),
    ITEMFILTER("itemfilter", "Item-Filter.Command.enabled", "Item-Filter.Command.permission", "Item-Filter.Command.no-permission-message", ConfigType.ITEMFILTER),
    CROPCONVERT("cropconvert", "Crop-Hoppers.Convert-Command.enabled", "Crop-Hoppers.Convert-Command.permission", "Crop-Hoppers.Convert-Command.no-permission-message", ConfigType.CROP_HOPPERS),
    WITHDRAW("withdraw", "command.enabled", "command.permission", "command.no-permission-msg", ConfigType.WITHDRAW_CONFIG),
    PRINTER("printer", "command.enabled", "command.permission", "command.no-permission-msg", ConfigType.PRINTER_CONFIG),
    DISENCHANT("disenchant", "Commands.Disenchant.enabled", "Commands.Disenchant.permission", "Commands.Disenchant.no-permission-msg", ConfigType.ENCHANT_CONFIG),
    ENCHANT("enchant", "Commands.Enchant.enabled", "Commands.Enchant.permission", "Commands.Enchant.no-permission-msg", ConfigType.ENCHANT_CONFIG),
    EXP_WITHDRAW("expwithdraw", "command.enabled", "command.permission", "command.no-permission", ConfigType.EXP_BOTTLE),
    DROPS("drops", "command.enabled", "command.permission", "command.no-permission", ConfigType.DROPS_CONFIG),
    SMELT("smelt", "Commands.Smelt.enabled", "Commands.Smelt.permission", "Commands.Smelt.no-permission-msg", ConfigType.MAIN_CONFIG),
    CREEPER("creeper", "Commands.Creeper.enabled", "Commands.Creeper.permission", "Commands.Creeper.no-permission-msg", ConfigType.MAIN_CONFIG),
    BTF("btf", "Commands.BtfCommand.enabled", "Commands.BtfCommand.permission", "Commands.BtfCommand.no-permission-msg", ConfigType.MAIN_CONFIG),
    CT("ct", "Combat-Tag.command.enabled", "Combat-Tag.command.Permission", "Combat-Tag.command.No-Permission", ConfigType.COMBAT_CONFIG),
    WILD("wild", "Command.Wilderness.enabled", "Command.Wilderness.permission", "Command.Wilderness.no-permission-msg", ConfigType.WILD),
    GRACE("grace", "Grace-Period.command-enabled", "Grace-Period.permission", "Grace-Period.no-permission", ConfigType.MAIN_CONFIG),
    HOPPERFILTER("hopperfilter", "Hopper-Filter.command.enabled", "Hopper-Filter.command.permission", "Hopper-Filter.command.no-permission-msg", ConfigType.HOPPER_FILTER_CONFIG),
    ANTIITEMCRAFT("antiitemcraft", "Anti-Item-Craft.command.enabled", "Anti-Item-Craft.command.permission", "Anti-Item-Craft.command.no-permission-msg", ConfigType.ANTIITEMCRAFT_CONFIG),
    POTION("pot", "Commands.Potions.enabled", "Commands.Potions.base-permission", "Commands.Potions.no-permission-message", ConfigType.POTION),
    CHUNKBUSTERS("chunkbust", "Chunk-Busters.command.enabled", "Chunk-Busters.command.permission", "Chunk-Busters.command.no-permission-msg", ConfigType.CHUNKBUSTER),
    FACTION_ALERTS("f-alerts", "Faction-Alerts.command.enabled", "Faction-Alerts.command.permission", "Faction-Alerts.command.no-permission", ConfigType.ALERTS_CONFIG),
    VOID_SPAWN_COMMAND("setvoidspawn", "Anti-Void-Falling.command-enabled", "Anti-Void-Falling.command-permission", "Anti-Void-Falling.no-permission", ConfigType.MAIN_CONFIG),
    LIGHTING_TOOLS("lightningtools", "Lightning-Tools.command.enabled", "Lightning-Tools.command.permission", "Lightning-Tools.command.no-permission", ConfigType.BEAST_TOOLS_CONFIG),
    KILL_MERGED("killmerged", "command.enabled", "command.permission", "command.no-permission-msg", ConfigType.MERGE_CONFIG),
    CROP_HOPPERS("crophoppers", "Crop-Hoppers.command.enabled", "Crop-Hoppers.command.permission", "Crop-Hoppers.command.no-permission-msg", ConfigType.CROP_HOPPERS),
    JELLY_LEGS("Jellylegs", "Commands.Jelly-Legs.enabled", "Commands.Jelly-Legs.permission", "Commands.Jelly-Legs.no-permission-msg", ConfigType.MAIN_CONFIG),
    VOID_CHESTS("voidchest", "Void-Chests.command.enabled", "Void-Chests.command.permission", "Void-Chests.command.no-permission-msg", ConfigType.VOID_CHESTS),
    MOB_HOPPER("mobhoppers", "Mob-Hoppers.command.enabled", "Mob-Hoppers.command.permission", "Mob-Hoppers.command.no-permission-msg", ConfigType.MOB_HOPPERS),
    TNTFILL("tntfill", "TnT-Fill.command.enabled", "TnT-Fill.command.permission", "TnT-Fill.command.no-permission-msg", ConfigType.TNTFILL),
    TNTUNFILL("tntunfill", "TnT-Unfill.command.enabled", "TnT-Unfill.command.permission", "TnT-Unfill.command.no-permission-msg", ConfigType.TNTFILL),
    FPS_BOOSTER("fpsboost", "FPS-Booster.command.enabled", "FPS-Booster.command.permission", "FPS-Booster.command.no-permission-msg", ConfigType.FPS_BOOSTER),
    ;
    private String name;
    private String permissionPath;
    private String noPermissionMsgPath;
    private ConfigType configType;
    private String enabledPath;

    CommandType(String name, String enabledPath, String permissionPath, String noPermissionMsgPath, ConfigType configType) {
        this.name = name;
        this.enabledPath = enabledPath;
        this.permissionPath = permissionPath;
        this.noPermissionMsgPath = noPermissionMsgPath;
        this.configType = configType;
    }

    public String getEnabledPath() {
        return enabledPath;
    }

    public ConfigType getConfigType() {
        return configType;
    }

    public String getName() {
        return name;
    }

    public String getPermissionPath() {
        return permissionPath;
    }

    public String getNoPermissionMsgPath() {
        return noPermissionMsgPath;
    }
}
