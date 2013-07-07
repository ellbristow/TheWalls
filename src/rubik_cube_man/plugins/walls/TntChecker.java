package rubik_cube_man.plugins.walls;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class TntChecker implements Listener {

	private Walls plugin;
	
	public TntChecker(Walls plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event) {
		List<Block> blockListCopy = new ArrayList<Block>();
        blockListCopy.addAll(event.blockList());
        Iterator<String> itr = plugin.arenas.keySet().iterator();
        while (itr.hasNext()){
        	Arena arena = plugin.arenas.get(itr.next());
        	Location start = arena.getLoc1();
    		Location end = arena.getLoc2();
    		Integer minX = (int) Math.min(start.getX() , end.getX());
    		Integer maxX = (int) Math.max(start.getX() , end.getX());
    		Integer minY = (int) Math.min(start.getY() , end.getY());
    		Integer maxY = (int) Math.max(start.getY() , end.getY());
    		Integer minZ = (int) Math.min(start.getZ() , end.getZ());
    		Integer maxZ = (int) Math.max(start.getZ() , end.getZ());
        	if (event.getEntity().getLocation().getX() <= maxX 
					&& event.getEntity().getLocation().getY() <= maxY 
					&& event.getEntity().getLocation().getZ() <= maxZ 
					&& event.getEntity().getLocation().getX() >= minX
					&& event.getEntity().getLocation().getY() >= minY 
					&& arena.getLoc1().getWorld() == event.getEntity().getWorld()
					&& event.getEntity().getLocation().getZ() >= minZ){
        		break;
        	}
        }
    	if (!itr.hasNext()){
    		return;
    	}
		event.blockList().clear();
        for (String arenaName : plugin.arenas.keySet()){
        	Arena arena = plugin.arenas.get(arenaName);
	        for (Block block : blockListCopy) {
				Iterator<Integer> loop = arena.getSaveregion().keySet().iterator();
				while (loop.hasNext()){
					if (loop != null){
						Integer num = loop.next();
						Location start = arena.getSaveregion().get(num);
			    		Location end = arena.getSaveregion1().get(num);
			    		Integer minX = (int) Math.min(start.getX() , end.getX());
			    		Integer maxX = (int) Math.max(start.getX() , end.getX());
			    		Integer minY = (int) Math.min(start.getY() , end.getY());
			    		Integer maxY = (int) Math.max(start.getY() , end.getY());
			    		Integer minZ = (int) Math.min(start.getZ() , end.getZ());
			    		Integer maxZ = (int) Math.max(start.getZ() , end.getZ());
						if (block.getLocation().getX() <= maxX
								&& block.getLocation().getY() <= maxY
								&& block.getLocation().getZ() <= maxZ 
								&& block.getLocation().getX() >= minX 
								&& block.getLocation().getY() >= minY
								&& block.getLocation().getWorld().getName() == arena.getSaveregion1().get(num).getWorld().getName() 
								&& block.getLocation().getZ() >= minZ){
							event.blockList().add(block);
							Bukkit.broadcastMessage("inside");
						}
					}
				}
		    }
        }
    }
}
