package feildmaster.PistonSmelter;

import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class PistonSmelter extends JavaPlugin {
    private final DetectPhysics blockListener = new DetectPhysics();
    private final DetectExplosion entityListener = new DetectExplosion();

    public void onDisable() {}

    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.BLOCK_PHYSICS, blockListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.BLOCK_BREAK, blockListener, Event.Priority.Lowest, this);
        pm.registerEvent(Event.Type.ENTITY_EXPLODE, entityListener, Event.Priority.Low, this);

        System.out.println("Piston Smelter v"+getDescription().getVersion()+": Enabled");
    }
    
}
