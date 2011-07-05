package feildmaster.PistonSmelter;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPhysicsEvent;

class DetectPhysics extends BlockListener {
    private final PistonSmelter plugin;

    public DetectPhysics(PistonSmelter plugin) {
        this.plugin = plugin;
    }

    protected Boolean next2Piston(Block block) {
        if (block.getFace(BlockFace.EAST).getType() == Material.PISTON_EXTENSION
            || block.getFace(BlockFace.WEST).getType() == Material.PISTON_EXTENSION
            || block.getFace(BlockFace.NORTH).getType() == Material.PISTON_EXTENSION
            || block.getFace(BlockFace.SOUTH).getType() == Material.PISTON_EXTENSION
            || block.getFace(BlockFace.UP).getType() == Material.PISTON_EXTENSION)
                return true;
    
        return false;
    }

    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (block.getData() == (byte)15) {
            if(block.getType() == Material.STONE
                    || block.getType() == Material.IRON_BLOCK
                    || block.getType() == Material.GOLD_BLOCK
                    || block.getType() == Material.GLASS) {
                plugin.simulateBreak(block);
                event.setCancelled(true);
            }
        }
    }

    public void onBlockPhysics(BlockPhysicsEvent event) {
        Block block = event.getBlock();
        if ((block.getFace(BlockFace.DOWN).getType() == Material.LAVA
                || (block.getFace(BlockFace.DOWN).getType() == Material.GLASS
                    && (block.getRelative(BlockFace.DOWN).getFace(BlockFace.DOWN).getType() == Material.LAVA || block.getRelative(BlockFace.DOWN).getFace(BlockFace.DOWN).getType() == Material.STATIONARY_LAVA))
             ) && next2Piston(block)) {
            if(block.getType() == Material.COBBLESTONE) {
                block.setType(Material.STONE);
                block.setData((byte)15);
            } else if (block.getType() == Material.IRON_ORE){
                block.setType(Material.IRON_BLOCK);
                block.setData((byte)15);
            } else if (block.getType() == Material.GOLD_ORE){
                block.setType(Material.GOLD_BLOCK);
                block.setData((byte)15);
            } else if (block.getType() == Material.SAND) {
                block.setType(Material.GLASS);
                block.setData((byte)15);
            } else if (block.getType() == Material.CLAY) {
                block.setType(Material.BRICK);
                block.setData((byte)15);
            }
        }
        if ((block.getData() == (byte)15)
                && block.getFace(BlockFace.DOWN).getType() == Material.WATER) {
            plugin.simulateBreak(block);
        }
    }
}
