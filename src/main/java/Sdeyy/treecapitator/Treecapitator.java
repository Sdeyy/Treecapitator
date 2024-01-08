package Sdeyy.treecapitator;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;

import Sdeyy.treecapitator.Util.CC;
import Sdeyy.treecapitator.listener.JobsTreeCutListener;
import Sdeyy.treecapitator.listener.TestListener;
import Sdeyy.treecapitator.listener.TreeCutListener;

public final class Treecapitator extends JavaPlugin {

    private static Treecapitator instance;

    @Override
    public void onEnable() {
        
        CC.log(" &8| &aLoading Plugin.");
        CC.log("");
        CC.log("            &3&lTreeCapitator &8- [&aSdeyy&8]");
        CC.log("");
        CC.log(" &8| &bAuthor&8: &f Sdeyy Services");
        CC.log(" &8| &bVersion&8: &f1.0.0-SNAPSHOT");
        CC.log(" &8| &bDiscord&8: &fhttps://discord.gg/4KvwQHpqbq");
        CC.log("");
        
        /* 
        saveDefaultConfig();

        instance = this;

        if(getServer().getPluginManager().getPlugin("Jobs") != null) {
            getServer().getPluginManager().registerEvents(new JobsTreeCutListener(), this);
        } else {
            getServer().getPluginManager().registerEvents(new TreeCutListener(), this);
        }
        */
        getServer().getPluginManager().registerEvents(new TestListener(), this);

    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public static Treecapitator getInstance() {
        return instance;
    }

    public boolean isLog(Material material) {
        String name = material.name();
        if(getConfig().getBoolean("options.mangrove_roots") && name.equals("MANGROVE_ROOTS")) {
            return true;
        }
        return (name.endsWith("_LOG") || name.equals("CRIMSON_STEM") || name.equalsIgnoreCase("WARPED_STEM")) && !name.startsWith("STRIPPED");
    }

    public boolean isLog(Block block) {
        return isLog(block.getType());
    }

}
