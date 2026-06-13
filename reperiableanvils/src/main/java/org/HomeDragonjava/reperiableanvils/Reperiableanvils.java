package org.HomeDragonjava.reperiableanvils;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public final class Reperiableanvils extends JavaPlugin implements Listener {

    private final Random random = new Random();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onAnvilClick(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Block block = event.getClickedBlock();
        if (block == null) return;

        Material type = block.getType();
        // Prevent repairing, if it's already a perfect ANVIL....
        if (type == Material.ANVIL) return;
        if (type != Material.CHIPPED_ANVIL && type != Material.DAMAGED_ANVIL) return;

        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.getType() != Material.IRON_INGOT) return;

        event.setCancelled(true);

        if (player.getGameMode() != org.bukkit.GameMode.CREATIVE) {
            item.setAmount(item.getAmount() - 1);
        }

        block.getWorld().playSound(block.getLocation(), Sound.BLOCK_ANVIL_USE, 1.0f, 1.0f);

        if (random.nextDouble() < 0.3) {
            // Save current rotation,
            BlockData oldData = block.getBlockData();

            if (type == Material.DAMAGED_ANVIL) {
                block.setType(Material.CHIPPED_ANVIL);
            } else if (type == Material.CHIPPED_ANVIL) {
                block.setType(Material.ANVIL);
            }

            // rotationa well we did save it???>
            if (oldData instanceof Directional && block.getBlockData() instanceof Directional) {
                ((Directional) block.getBlockData()).setFacing(((Directional) oldData).getFacing());
                block.setBlockData(block.getBlockData());
            }
        }
    }
}
//short and easy