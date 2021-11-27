package ml.obfuscatedgenerated.PlayerLogger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CommandGetLog implements CommandExecutor {
    public String readLog (){
        StringBuilder content = new StringBuilder();
        try {
            File f = new File(((PlayerLogger) Bukkit.getPluginManager().getPlugin("PlayerLogger")).getDataFolder(), "PlayerLog.txt");
            Scanner reader = new Scanner(f);
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                content.append(data);
                content.append("\n");
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return content.toString();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("PlayerLogger.get")) {
                String log = readLog();
                if (log.contains("=== FLUSH ===")) {
                    log = log.substring(log.lastIndexOf("=== FLUSH ===") + 14);// add 14 to account for === FLUSH ===\n
                }
                p.sendMessage(log);
            } else {
                p.sendMessage(ChatColor.RED+"You don't have permission to do that!");
            }
        }
        return true;
    }
}
