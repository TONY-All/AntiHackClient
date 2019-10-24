package top.minicraft.atc.Listeners;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitTask;
import top.minicraft.atc.AntiJoin;
import top.minicraft.atc.Anticheat;

import java.util.*;
import java.util.List;

public class JoinEvent implements Listener {
    private static List<Player> UnCheckedPlayers = new ArrayList<Player>();
    private static Map<Player,String> PlayerCheckers = new HashMap<Player, String>();
    public static List getUnCheckedPlayers() {
        return UnCheckedPlayers;
    }
    public static Map getPlayerCheckers(){
        return PlayerCheckers;
    }
    @EventHandler
    public void PlayerJoin(PlayerJoinEvent e){
        String s = getRandomString(20);
        Player p = e.getPlayer();
        UnCheckedPlayers.add(p);
        PlayerCheckers.put(p,s);
        AntiJoin AJ = new AntiJoin(p);
        BukkitTask task = AJ.runTaskTimer(Anticheat.getInstance(),0,60);
        while(true) {
            if (!UnCheckedPlayers.contains(p)) {
                Bukkit.getScheduler().cancelTask(task.getTaskId());
                break;
            }
        }
    }
    private static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
    @EventHandler
    public void PlayerChatEvent(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();
        p.sendMessage("e.called");
        if(UnCheckedPlayers.contains(p)){
            String sended = e.getMessage();
            if(sended.equals(PlayerCheckers.get(p))){
                p.sendMessage("QWQ");
                UnCheckedPlayers.remove(p);
                PlayerCheckers.remove(p);
                Bukkit.getBanList(BanList.Type.NAME).addBan(p.getName(),"您已被反作弊永久封号",null,null);
            }else if(sended.equals(".say "+PlayerCheckers.get(p))){
                p.sendMessage(ChatColor.GREEN+"[反作弊]您已通过验证");
                UnCheckedPlayers.remove(p);
                PlayerCheckers.remove(p);
            }else {
                p.sendMessage(sended);
                p.sendMessage("Correct is");
                p.sendMessage(".say "+sended);
                p.sendMessage(PlayerCheckers.get(p));
                p.sendMessage("未通过反作弊验证，GUN");
                p.kickPlayer("????为什么这么简单都做不到");
            }
        }
    }
    @EventHandler
    public void AntiMove(PlayerMoveEvent e){
        Player p = e.getPlayer();
        if(UnCheckedPlayers.contains(p)){
            e.setCancelled(true);
        }
    }
}
