package info.beastsoftware.beastcore.placeholder;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.utils.StrUtils;
import info.beastsoftware.beastcore.entity.APIAccessor;
import info.beastsoftware.beastcore.util.IStringUtil;
import info.beastsoftware.beastcore.util.StringUtils;
import info.beastsoftware.hookcore.entity.BeastPlayer;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class Papi extends PlaceholderExpansion implements APIAccessor {

    private final IStringUtil stringUtil;
    private final IConfig config;


    public Papi(IConfig config) {
        this.config = config;
        stringUtil = new StringUtils();
        this.register();
    }

    @Override
    public String getIdentifier() {
        return "btf";
    }

    @Override
    public String getPlugin() {
        return null;
    }

    @Override
    public String getAuthor() {
        return "BrutalFisting";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public String onPlaceholderRequest(Player player, String s) {

        BeastPlayer beastPlayer = this.getPlayer(player);

        //ENDERPEARL COOLDOWN PLACEHOLDERS
        if (s.equals("pearl_cooldown")) {
            int time = BeastCore.getInstance().getApi().getPearlCDOfPlayer(this.getPlayer(player));
            String placeholder;
            if (time <= 0) {
                placeholder = config.getConfig().getString("Placeholders.Pearl-Cooldown.Finished");
            } else {
                placeholder = config.getConfig().getString("Placeholders.Pearl-Cooldown.Running");
            }

            placeholder = StrUtils.replacePlaceholder(placeholder, "{time}", time + "");

            return placeholder;
        }

        //GAPPLE COOLDOWN PLACEHOLDERS

        if (s.equals("gapple_cooldown")) {
            int time = BeastCore.getInstance().getApi().getGappleCDOfPlayer(beastPlayer);
            String placeholder;
            if (time <= 0) {
                placeholder = config.getConfig().getString("Placeholders.Gapple-Cooldown.Finished");
            } else {
                placeholder = config.getConfig().getString("Placeholders.Pearl-Cooldown.Running");
            }

            placeholder = StrUtils.replacePlaceholder(placeholder, "{time}", time + "");

            return placeholder;
        }

        //COMBAT TAG PLACEHOLDERS
        if (s.equals("combat_tag")) {
            int time = BeastCore.getInstance().getApi().getCombatTimeOfPlayer(this.getPlayer(player));
            String placeholder;
            if (time <= 0) {
                placeholder = config.getConfig().getString("Placeholders.Combat.Finished");
            } else {
                placeholder = config.getConfig().getString("Placeholders.Combat.Running");
            }

            placeholder = StrUtils.replacePlaceholder(placeholder, "{time}", time + "");

            return placeholder;
        }


        //GRACE PERIOD PLACEHOLDERS
        if (s.equals("grace_formatted"))
            if (!BeastCore.getInstance().getApi().isOnGrace()) {
                return config.getConfig().getString("Placeholders.Grace.Disabled");
            } else
                return stringUtil.formatCooldown(BeastCore.getInstance().getApi().getGraceTimeLeft());
        if (s.equals("grace_in_seconds"))
            return BeastCore.getInstance().getApi().getGraceTimeLeft() + "";


        return null;
    }
}
