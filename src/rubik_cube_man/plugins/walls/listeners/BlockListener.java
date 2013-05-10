package rubik_cube_man.plugins.walls.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockEvent;

public class BlockListener implements Listener {

    @EventHandler
    public void onBlockEvent(BlockEvent event) {
        Bukkit.broadcastMessage("Hi");
    }
}
