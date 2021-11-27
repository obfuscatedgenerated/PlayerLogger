package ml.obfuscatedgenerated.PlayerLogger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CommandFlushLog implements CommandExecutor {
    public void logToFile(String message)
    {
        try
        {
            File dataFolder = ((PlayerLogger) Bukkit.getPluginManager().getPlugin("PlayerLogger")).getDataFolder();
            if(!dataFolder.exists())
            {
                dataFolder.mkdir();
            }

            File saveTo = new File(((PlayerLogger) Bukkit.getPluginManager().getPlugin("PlayerLogger")).getDataFolder(), "PlayerLog.txt");
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
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("PlayerLogger.flush")) {
                logToFile("=== FLUSH ===");
                p.sendMessage("Added flush point.");
            } else {
                p.sendMessage(ChatColor.RED+"You don't have permission to do that!");
            }
        } else if (sender instanceof ConsoleCommandSender) {
            ConsoleCommandSender c = (ConsoleCommandSender) sender;
            logToFile("=== FLUSH ===");
            c.sendMessage("Added flush point.");
        } else if (sender instanceof RemoteConsoleCommandSender) {
            RemoteConsoleCommandSender r = (RemoteConsoleCommandSender) sender;
            logToFile("=== FLUSH ===");
            r.sendMessage("Added flush point.");
        }
        return true;
    }
}
