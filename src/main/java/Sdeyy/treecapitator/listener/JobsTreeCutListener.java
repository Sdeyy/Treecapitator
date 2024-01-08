package Sdeyy.treecapitator.listener;

import com.gamingmesh.jobs.Jobs;
import com.gamingmesh.jobs.actions.BlockActionInfo;
import com.gamingmesh.jobs.container.ActionType;
import com.gamingmesh.jobs.container.JobsPlayer;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import Sdeyy.treecapitator.Treecapitator;

import java.util.ArrayList;
import java.util.List;

public class JobsTreeCutListener implements Listener {

    final boolean diagonal_logs = Treecapitator.getInstance().getConfig().getBoolean("options.diagonal_logs");
    final BlockFace[] faces = diagonal_logs
            ? new BlockFace[] { BlockFace.UP, BlockFace.DOWN, BlockFace.NORTH, BlockFace.NORTH_EAST, BlockFace.EAST, BlockFace.SOUTH_EAST, BlockFace.SOUTH, BlockFace.SOUTH_WEST, BlockFace.WEST, BlockFace.NORTH_WEST }
            : new BlockFace[] { BlockFace.UP, BlockFace.DOWN, BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST };

    @EventHandler
    public void treeCut(BlockBreakEvent event) {

        Player player = event.getPlayer();
        String permission = Treecapitator.getInstance().getConfig().getString("options.permission_to_decapitate");

        if(!permission.equals("")) {
            if(!player.hasPermission(permission)) {
                return;
            }
        }

        if(Treecapitator.getInstance().isLog(event.getBlock())) {

            List<Block> logsLeft = new ArrayList<>();
            logsLeft.add(event.getBlock());

            if(Treecapitator.getInstance().getConfig().isSet("options.shift_to_activate")) {
                if(Treecapitator.getInstance().getConfig().getBoolean("options.shift_to_activate") && !player.isSneaking()) {
                    return;
                }
            }

            new BukkitRunnable() {

                int logCounter = 0;

                @Override
                public void run() {

                    if(logsLeft.isEmpty() || logCounter > Treecapitator.getInstance().getConfig().getInt("options.max_logs")) {
                        cancel();
                        return;
                    }

                    Block log = logsLeft.get(0);


                    JobsPlayer jobsPlayer = Jobs.getPlayerManager().getJobsPlayer(player.getUniqueId());

                    if(jobsPlayer != null) {
                        Jobs.action(jobsPlayer, new BlockActionInfo(log, ActionType.BREAK), log);
                    }

                    if(Treecapitator.getInstance().getConfig().getBoolean("options.auto_pickup_drops")) {
                        for(ItemStack item : log.getDrops(player.getInventory().getItemInMainHand())) {
                            player.getInventory().addItem(item);
                        }
                        log.setType(Material.AIR);
                    } else {
                        log.breakNaturally(player.getInventory().getItemInMainHand());
                    }

                    for(BlockFace face : faces) {

                        BlockFace[] relatives = new BlockFace[] { BlockFace.SELF, BlockFace.UP, BlockFace.DOWN };

                        for(BlockFace relative : relatives) {
                            Block block = log.getRelative(face).getRelative(relative);
                            if(Treecapitator.getInstance().isLog(block) && !logsLeft.contains(block)) {
                                logsLeft.add(block);
                            }
                        }

                    }

                    logsLeft.remove(0);
                    logCounter++;

                }

            }.runTaskTimer(Treecapitator.getInstance(), 0L, 1L);
        }

    }


}
