package rubik_cube_man.plugins.walls;

import java.util.Iterator;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

public class PlayerPlaceBlock implements Listener {
	private Walls plugin;

	public PlayerPlaceBlock(Walls walls) {
		this.plugin = walls;
	}
	@EventHandler
	public void onPlayerPlaceBlockEvent(BlockPlaceEvent event){
		Player player = event.getPlayer();
		if (plugin.playerarena.get(player) != null){
			boolean brek = true;
			Iterator<Integer> itr = plugin.arenas.get(plugin.playerarena.get(player)).getSaveregion().keySet().iterator();
				while (itr.hasNext()){
				if (itr != null){
					Integer num = itr.next();
					if (event.getBlock().getLocation().getX() <= plugin.arenas.get(plugin.playerarena.get(player)).getSaveregion().get(num).getX() 
							&& event.getBlock().getLocation().getY() <= plugin.arenas.get(plugin.playerarena.get(player)).getSaveregion().get(num).getY() 
							&& event.getBlock().getLocation().getZ() <= plugin.arenas.get(plugin.playerarena.get(player)).getSaveregion().get(num).getZ() 
							&& event.getBlock().getLocation().getX() >= plugin.arenas.get(plugin.playerarena.get(player)).getSaveregion1().get(num).getX() 
							&& event.getBlock().getLocation().getY() >= plugin.arenas.get(plugin.playerarena.get(player)).getSaveregion1().get(num).getY() 
							&& event.getBlock().getLocation().getZ() >= plugin.arenas.get(plugin.playerarena.get(player)).getSaveregion1().get(num).getZ()){
						brek = false;
					}
				}
			}
			event.setCancelled(brek);
		}
	}
	
	@EventHandler
	public void onPlayerEmptyWaterEvent(PlayerBucketEmptyEvent event){
		Player player = event.getPlayer();
		if (plugin.playerarena.get(player) != null){
			boolean brek = true;
			Iterator<Integer> itr = plugin.arenas.get(plugin.playerarena.get(player)).getSaveregion().keySet().iterator();
				while (itr.hasNext()){
				if (itr != null){
					Integer num = itr.next();
					if (event.getBlockClicked().getRelative(event.getBlockFace()).getLocation().getX() <= plugin.arenas.get(plugin.playerarena.get(player)).getSaveregion().get(num).getX() 
							&& event.getBlockClicked().getRelative(event.getBlockFace()).getLocation().getY() <= plugin.arenas.get(plugin.playerarena.get(player)).getSaveregion().get(num).getY() 
							&& event.getBlockClicked().getRelative(event.getBlockFace()).getLocation().getZ() <= plugin.arenas.get(plugin.playerarena.get(player)).getSaveregion().get(num).getZ() 
							&& event.getBlockClicked().getRelative(event.getBlockFace()).getLocation().getX() >= plugin.arenas.get(plugin.playerarena.get(player)).getSaveregion1().get(num).getX() 
							&& event.getBlockClicked().getRelative(event.getBlockFace()).getLocation().getY() >= plugin.arenas.get(plugin.playerarena.get(player)).getSaveregion1().get(num).getY() 
							&& event.getBlockClicked().getRelative(event.getBlockFace()).getLocation().getZ() >= plugin.arenas.get(plugin.playerarena.get(player)).getSaveregion1().get(num).getZ()){
						brek = false;
					}
				}
			}
			event.setCancelled(brek);
		}
	}
}
