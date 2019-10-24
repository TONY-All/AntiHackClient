package top.minicraft.atc;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import top.minicraft.atc.Listeners.JoinEvent;

public class Anticheat extends JavaPlugin {
    private static Anticheat instance;
    public static Anticheat getInstance() {
        return instance;
    }
    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getPluginManager().registerEvents(new JoinEvent(),this);
    }

}