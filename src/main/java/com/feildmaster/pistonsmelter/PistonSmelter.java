package com.feildmaster.pistonsmelter;

public class PistonSmelter extends org.bukkit.plugin.java.JavaPlugin {
    private final SmeltManager eventListener = new SmeltManager();
//    public Boolean autoBreak;

    public void onEnable() {
        getServer().getPluginManager().registerEvents(eventListener, this);
    }
}
