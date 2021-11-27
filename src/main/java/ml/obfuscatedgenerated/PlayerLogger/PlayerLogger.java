package ml.obfuscatedgenerated.PlayerLogger;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PlayerLogger extends JavaPlugin implements Listener {
    public void logToFile(String message)
    {
        try
        {
            File dataFolder = getDataFolder();
            if(!dataFolder.exists())
            {
                dataFolder.mkdir();
            }

            File saveTo = new File(getDataFolder(), "PlayerLog.txt");
            if (!saveTo.exists())
            {
                saveTo.createNewFile();
            }


            FileWriter fw = new FileWriter(saveTo, true);

            PrintWriter pw = new PrintWriter(fw);

            pw.println(message);

            pw.flush();

            pw.close();

        } catch (IOException e)
        {

            e.printStackTrace();

        }
    }

    @Override
    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        this.getCommand("getplayerlog").setExecutor(new CommandGetLog());
        this.getCommand("flushplayerlog").setExecutor(new CommandFlushLog());
    }

    @Override
    public void onDisable(){

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        String timeStamp = new SimpleDateFormat("dd/MM/yy HH:mm:ss").format(new Date());
        if (p.hasPlayedBefore()) {
            logToFile(ChatColor.BLUE+"("+timeStamp+"): "+ChatColor.AQUA+p.getName()+ChatColor.GREEN+" joined "+ChatColor.WHITE+"the server. "+ChatColor.LIGHT_PURPLE+"[UUID: "+p.getUniqueId().toString()+"]");
        } else {
            logToFile(ChatColor.BLUE+"("+timeStamp+"): "+ChatColor.AQUA+p.getName()+ChatColor.GREEN+" joined "+ChatColor.WHITE+" the server for the "+ChatColor.YELLOW+"first time. "+ChatColor.LIGHT_PURPLE+"[UUID: "+p.getUniqueId().toString()+"]");
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        String timeStamp = new SimpleDateFormat("dd/MM/yy HH:mm:ss").format(new Date());
        logToFile(ChatColor.BLUE+"("+timeStamp+"): "+ChatColor.AQUA+p.getName()+ChatColor.RED+" left "+ChatColor.WHITE+"the server. "+ChatColor.LIGHT_PURPLE+"[UUID: "+p.getUniqueId().toString()+"]");
    }
}
