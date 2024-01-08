package Sdeyy.treecapitator.Util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import java.util.ArrayList;
import java.util.List;

public class CC {

    public static String translate(String in) {
        return ChatColor.translateAlternateColorCodes('&', in);
    }

    public static void log(String in) {
        Bukkit.getConsoleSender().sendMessage(translate(in));
    }

    public static List<String> translate(List<String> lines) {
        List<String> toReturn = new ArrayList<>();
        for (String line : lines)
            toReturn.add(ChatColor.translateAlternateColorCodes('&', line));
        return toReturn;
    }

    public static List<String> translate(String[] lines) {
        List<String> toReturn = new ArrayList<>();
        for (String line : lines) {
            if (line != null)
                toReturn.add(ChatColor.translateAlternateColorCodes('&', line));
        }
        return toReturn;
    }
}
