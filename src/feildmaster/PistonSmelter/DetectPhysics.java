package feildmaster.PistonSmelter;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.inventory.ItemStack;

class DetectPhysics extends BlockListener {

    public DetectPhysics() {}

    protected Boolean next2Piston(Block block) {
    if (block.getFace(BlockFace.EAST).getType() == Material.PISTON_EXTENSION
            || block.getFace(BlockFace.WEST).getType() == Material.PISTON_EXTENSION
            || block.getFace(BlockFace.NORTH).getType() == Material.PISTON_EXTENSION
            || block.getFace(BlockFace.SOUTH).getType() == Material.PISTON_EXTENSION)
      return true;
    
    return false;
  }

    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (block.getData() == (byte)15) {
            if(block.getType() == Material.STONE) {
                event.getPlayer().getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.STONE, 1));
                block.setType(Material.AIR);
                event.setCancelled(true);
            } else if (block.getType() == Material.IRON_BLOCK) {
                event.getPlayer().getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.IRON_INGOT, 1));
                block.setType(Material.AIR);
                event.setCancelled(true);
            } else if (block.getType() == Material.GOLD_BLOCK) {
                event.getPlayer().getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.GOLD_INGOT, 1));
                block.setType(Material.AIR);
                event.setCancelled(true);
            } else if (block.getType() == Material.GLASS) {
                event.getPlayer().getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.GLASS, 1));
                event.setCancelled(true);
            }
        }
    }
    public void onBlockPhysics(BlockPhysicsEvent event) {
        Block block = event.getBlock();
        if (block.getFace(BlockFace.DOWN, 1).getType() == Material.LAVA
                && next2Piston(block)) {
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
            }
        }
    }
}
