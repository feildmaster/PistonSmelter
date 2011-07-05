package feildmaster.PistonSmelter;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityListener;

class DetectExplosion extends EntityListener {
    private final PistonSmelter plugin;

    public DetectExplosion(PistonSmelter plugin) {
        this.plugin = plugin;
    }

    public void onEntityExplode(EntityExplodeEvent event) {
        int id = 0;
        if(!event.blockList().isEmpty())
            for(Block block : event.blockList()) {
                if(block.getData() == (byte)15) {
                    if(block.getType() == Material.IRON_BLOCK
                            || block.getType() == Material.GOLD_BLOCK
                            || block.getType() == Material.STONE
                            || block.getType() == Material.GLASS) {
                        plugin.simulateBreak(block);
                        event.blockList().remove(id);
                    }
                }
                id++;
            }
    }
}
