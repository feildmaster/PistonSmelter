package feildmaster.PistonSmelter;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class PistonSmelter extends JavaPlugin {
    private final DetectPhysics blockListener = new DetectPhysics(this);
    private final DetectExplosion entityListener = new DetectExplosion(this);

    public void onDisable() {}

    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.BLOCK_PHYSICS, blockListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.BLOCK_BREAK, blockListener, Event.Priority.Lowest, this);
        pm.registerEvent(Event.Type.ENTITY_EXPLODE, entityListener, Event.Priority.Low, this);

        System.out.println("Piston Smelter v"+getDescription().getVersion()+": Enabled");
    }


    public void simulateBreak(Block block) {
        Material dropItem = null;
        if(block.getType() == Material.STONE) dropItem = Material.STONE;
        else if (block.getType() == Material.IRON_BLOCK) dropItem = Material.IRON_INGOT;
        else if (block.getType() == Material.GOLD_BLOCK) dropItem = Material.GOLD_INGOT;
        else if (block.getType() == Material.GLASS) dropItem = Material.GLASS;
        else if (block.getType() == Material.BRICK) dropItem = Material.BRICK;
        else return;
        block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(dropItem, 1));
        block.setType(Material.AIR);
    }
}
