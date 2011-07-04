package feildmaster.PistonSmelter;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityListener;

class DetectExplosion extends EntityListener {
    public void onEntityExplode(EntityExplodeEvent event) {
        int id = 0;
        for(Block block : event.blockList()) {
            if(block.getData() == (byte)15) {
                if(block.getType() == Material.IRON_BLOCK) {
                    event.blockList().remove(id);
                } else if (block.getType() == Material.GOLD_BLOCK) {
                    event.blockList().remove(id);
                }
            }
            id++;
        }
    }
}
