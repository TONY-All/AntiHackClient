package top.minicraft.atc;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import top.minicraft.atc.Listeners.JoinEvent;

public class AntiJoin extends BukkitRunnable {
    Player player;
    public AntiJoin(Player p) {
        player = p;
    }
    private int times =0;
    @Override
    public void run() {
        times++;
        if(times == 10){
            player.kickPlayer(org.bukkit.ChatColor.RED+"?????开瓜了？");
        }
        TextComponent tc = new TextComponent("[反作弊]点我进入游戏");
        TextComponent tch = new TextComponent("点击通过反作弊");
        tch.setColor(net.md_5.bungee.api.ChatColor.GOLD);
        HoverEvent he = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[]{tch});
        String s = (String) JoinEvent.getPlayerCheckers().get(player);
        ClickEvent ce = new ClickEvent(ClickEvent.Action.RUN_COMMAND,".say "+s);
        tc.setClickEvent(ce);
        tc.setHoverEvent(he);
        player.spigot().sendMessage(tc);
        PotionEffect pe = new PotionEffect(PotionEffectType.BLINDNESS,1,1);
        player.addPotionEffect(pe,true);

    }
}
