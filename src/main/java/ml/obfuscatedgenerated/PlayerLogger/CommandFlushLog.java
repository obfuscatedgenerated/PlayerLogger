package ml.obfuscatedgenerated.PlayerLogger;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
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
            logToFile("=== FLUSH ===");
            p.sendMessage("Added flush point.");
        }
        return true;
    }
}
