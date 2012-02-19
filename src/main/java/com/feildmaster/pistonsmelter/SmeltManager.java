package com.feildmaster.pistonsmelter;

import java.util.Iterator;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;
import org.bukkit.inventory.ItemStack;

public class SmeltManager implements Listener {
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onEntityExplode(EntityExplodeEvent event) {
        Iterator i = event.blockList().iterator();
        while (i.hasNext()) {
            Block block = (Block) i.next();
            if (block.getData() == 15 && isBreakable(block)) {
                simulateBreak(block);
                i.remove();
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (block.getData() == 15 && isBreakable(block)) {
            simulateBreak(block);
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onBlockPhysics(BlockPhysicsEvent event) {
        Block block = event.getBlock();
        Block rBlock = block.getRelative(BlockFace.DOWN);
        Block rrBlock = rBlock.getRelative(BlockFace.DOWN);

        if ((rBlock.getType() == Material.LAVA // Lava
                || (rBlock.getType() == Material.GLASS
                    && (rrBlock.getType() == Material.LAVA || rrBlock.getType() == Material.STATIONARY_LAVA))) // Glass over Lava
                && next2Piston(block)) { // Must be next to piston ATM...
            smeltBlock(block);
        }
        if (block.getData() == 15 && rBlock.getType() == Material.WATER) {
            simulateBreak(block);
        }
    }
//
//    @EventHandler // TODO: This event is *slightly* broken
//    public void onBlockPistonExtend(BlockPistonExtendEvent event) {
//        for (Block block : event.getBlocks()) {
//            block.setData((byte) 15);
//            if (block.getRelative(event.getDirection()).getRelative(BlockFace.DOWN).getType() == Material.LAVA
//                    || (block.getRelative(event.getDirection()).getRelative(BlockFace.DOWN).getType() == Material.GLASS
//                    && (block.getRelative(event.getDirection()).getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getType() == Material.LAVA || block.getRelative(event.getDirection()).getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getType() == Material.STATIONARY_LAVA))) {
//                smeltBlock(block);
//            }
//            if (block.getData() == (byte) 15 || block.getType() == Material.COBBLESTONE && block.getRelative(event.getDirection()).getRelative(BlockFace.DOWN).getType() == Material.WATER) {
//                simulateBreak(block);
//            }
//        }
//    }
//
//    @EventHandler
//    public void onBlockPistonRetract(BlockPistonRetractEvent event) {
//        Block block = event.getBlock().getRelative(event.getDirection());
//        Block rblock = event.getBlock().getRelative(event.getDirection()).getRelative(BlockFace.DOWN);
//
//        if (block.getData() == (byte) 15 && rblock.getType() == Material.WATER) {
//            simulateBreak(block);
//        }
//
//        if ((rblock.getType() == Material.LAVA
//                || (rblock.getType() == Material.GLASS) && (rblock.getRelative(BlockFace.DOWN).getType() == Material.LAVA || rblock.getRelative(BlockFace.DOWN).getType() == Material.STATIONARY_LAVA))) {
//            smeltBlock(block);
//        }
//    }
//
//
    private boolean isBreakable(Block block) {
        switch (block.getType()) {
            case STONE:
            case IRON_BLOCK:
            case GOLD_BLOCK:
            case GLASS:
                return true;
        }
        return false;
    }

    private void smeltBlock(Block block) {
        int id = -1;
        byte data = 15;

        if (block.getType() == Material.COBBLESTONE) {
            id = Material.STONE.getId();
        } else if (block.getType() == Material.IRON_ORE) {
            id = Material.IRON_BLOCK.getId();
        } else if (block.getType() == Material.GOLD_ORE) {
            id = Material.GOLD_BLOCK.getId();
        } else if (block.getType() == Material.SAND) {
            id = Material.GLASS.getId();
        } else if (block.getType() == Material.CLAY) {
            id = Material.BRICK.getId();
        }

        if (id != -1) {
            block.setTypeIdAndData(id, data, true);
        }
        // Add Debug - System.out.println(block.getType().toString()+" "+(int)block.getData());
    }

    private Boolean next2Piston(Block block) {
        return block.getRelative(BlockFace.EAST).getType() == Material.PISTON_EXTENSION
                || block.getRelative(BlockFace.WEST).getType() == Material.PISTON_EXTENSION
                || block.getRelative(BlockFace.NORTH).getType() == Material.PISTON_EXTENSION
                || block.getRelative(BlockFace.SOUTH).getType() == Material.PISTON_EXTENSION
                || block.getRelative(BlockFace.UP).getType() == Material.PISTON_EXTENSION;
    }

    private void simulateBreak(Block block) {
        Material dropItem = null;
        if (block.getType() == Material.STONE) {
            dropItem = Material.STONE;
        } else if (block.getType() == Material.COBBLESTONE) {
            dropItem = Material.COBBLESTONE;
        } else if (block.getType() == Material.IRON_BLOCK) {
            dropItem = Material.IRON_INGOT;
        } else if (block.getType() == Material.GOLD_BLOCK) {
            dropItem = Material.GOLD_INGOT;
        } else if (block.getType() == Material.GLASS) {
            dropItem = Material.GLASS;
        } else if (block.getType() == Material.BRICK) {
            dropItem = Material.BRICK;
        }

        if (dropItem == null) {
            return;
        }

        block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(dropItem, 1));
        block.setType(Material.AIR);
    }
}
