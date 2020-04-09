package info.beastsoftware.beastcore.struct;

public enum FeatureType {


    NO_BABY_ZOMBIES("enabled"),

    NO_ENEMY_HOMES("enabled"),

    NO_ENEMY_TELEPORT("enabled"),

    CREEPER("Commands.BtfCommand.enabled"),

    NV("enabled"),
    NO_ENDERMAN_TELEPORT("enabled"),


    ENCHANTMENTS("enabled"),

    DEBUFF("enabled"),

    NO_RESPAWN_SCREEN("enabled"),

    LOOT_PROTECTION("enabled"),


    NO_WITHER("Anti-Wither.enabled"),

    PLACEHOLDERS("enabled"),
    MAIN_COMMAND("Commands.BtfCommand.enabled"),
    MAIN_MENU("enabled"),
    POTION("enabled"),
    POTSTACKER("enabled"),
    TNTUNFILL("TnT-Unfill.enabled"),
    TNTFILL("TnT-Fill.enabled"),

    NORMAL_GAPPLE_CD("Normal-Gapple-BeastCooldown.enabled"),
    NO_MCMMO("No-Piston-McMMO.enabled"),
    NO_ITEM_BURN("Anti-Item-burn.enabled"),
    NO_EXPLOSIONS("No-Explosions.enabled"),
    NO_SCHEM("Anti-Schem-Bug.enabled"),
    NO_SPAWNER_GUARD("Anti-Spawner-Guard.enabled"),
    PEARL_GLITCH("Pearl-Glitch-Fix.enabled"),

    PRINTER("enabled"),
    SAND_STACKER("Sand-Stacking-Fix.enabled"),
    SERVER_PROTECTOR("Server-Protector.enabled"),
    SPAWNER_MINE("Anti-Spawner-Mine.enabled"),
    TICK_COUNTER("enabled"),
    VOID_CHESTS("enabled"),
    VOID_FALL("Anti-Void-Falling.enabled"),
    WATER_BLAZES("Water-Proof-Blazes.enabled"),
    WATER_BORDER("No-Water-Flow-Outside-Border.enabled"),
    WATER_REDSTONE("Anti-Water-Redstone.enabled"),
    WATER_SPONGE("Water-Sponge.enabled"),
    WEATHER("Anti-Weather.enabled"),
    WILDERNESS("enabled"),
    WORLD_BORDER_PEARL("Deny-Enderpearl-Outside-Border.enabled"),
    MONEY_WITHDRAW("enabled"),
    MOB_MERGER("enabled"),
    MOB_HOPPERS("enabled"),
    LAVA_SPONGE("Lava-Sponge.enabled"),
    LAPIS_FILLER("enabled"),
    JELLY_LEGS("Commands.Jelly-Legs.enabled"),
    ITEM_SPAWN("Deny-Item-Spawn.enabled"),
    ITEM_FILTER("enabled"),
    ITEM_BURN("Anti-Item-burn.enabled"),
    HORSE_MOUNT("Anti-Horse-Mount.enabled"),
    HOPPER_FILTER("enabled"),
    HIDE_STREAM("Hide-Stream.enabled"),
    GRACE("enabled"),
    GOLEM_FIRE("Iron-Golem-Spawn-On-Fire.enabled"),
    GOLEM_DEATH("enabled"),
    GAPPLE_CD("Gapple-BeastCooldown.enabled"),
    FPS("enabled"),
    FLYING_PEARL("No-Flying-Pearl.enabled"),
    FACTIONS_LOGOUT("Faction-Logout.enabled"),
    EXP_WITHDRAW("enabled"),
    EXPLODE_LAVA("Explode-Lava.enabled"),
    ENDER_PEARL_COOLDOWN("EnderPearl-BeastCooldown.enabled"),
    EDIT_DROPS("Edit-Drops.enabled"),
    CROP_HOPPERS("enabled"),
    NO_CREEPER_GLITCH("Anti-Creeper-Glitch.enabled"),
    COMMAND_COOLWON("enabled"),
    COMBAT("Combat-Tag.enabled"),
    CHUNK_BUSTERS("Chunk-Busters.enabled"),
    CANNON_PROTECTOR("Cannon-Protector.enabled"),
    BOTTLE_RECYCLE("Bottle-Recycling.enabled"),
    BORDER_STACK("Prevent-Sand-Stacking-In-Worldborder.enabled"),
    BEAST_TOOLS("Lightning-Tools.enabled"),
    AUTO_CANNONS_LIMITER("Auto-Cannons-Limiter.enabled"),
    ANVIL_COLOR("enabled"),
    ANTI_ITEM_PLACE("Anti-Boat-Place.enabled"),
    ANTI_ITEM_CRAFT("Anti-Item-Craft.enabled"),
    ANTI_EXPLOSION_DAMAGE("No-Explosions-Damage.enabled"),
    ALERTS("Faction-Alerts.enabled");

    private String path;

    FeatureType(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
