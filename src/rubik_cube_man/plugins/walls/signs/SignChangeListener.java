package rubik_cube_man.plugins.walls.signs;

import java.util.Iterator;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import rubik_cube_man.plugins.walls.Walls;
import rubik_cube_man.plugins.walls.kits.KitFile;

public class SignChangeListener implements Listener {

	private Walls plugin;


	public SignChangeListener(Walls walls) {
		this.plugin = walls;
	}
	
	@EventHandler
	public void onSignChangeEvent(SignChangeEvent event){
		Player player = event.getPlayer();
		if (player.hasPermission("walls.signwall")){
			if (event.getLine(0).equalsIgnoreCase("[walls]")){
				if (event.getBlock().getType() == Material.WALL_SIGN){
					if (event.getLine(1).equalsIgnoreCase("join")){
						if (event.getLine(2) != null){
							if (plugin.arenas.containsKey(event.getLine(2).toLowerCase())){
								Sign sign = (Sign) event.getBlock().getState();
								String arena = event.getLine(2);
								Location loc = sign.getLocation();
								SignWall signWall = new SignWall(loc,sign.getRawData(),arena,plugin.arenas.get(arena.toLowerCase()).getMin(), "Waiting");
								Integer wall = 0;
								Integer num = 0;
								int totals = plugin.arenas.get(arena.toLowerCase()).getMin() * 4;
								event.setLine(0, ChatColor.BOLD + "The Walls");
								event.setLine(1, ChatColor.DARK_GRAY + "Click to Join");
								event.setLine(2, ChatColor.UNDERLINE + arena);
								event.setLine(3, ChatColor.DARK_GRAY + "0/" + totals);
								Iterator<Integer> itr = plugin.arenas.get(arena.toLowerCase()).getSignWalls().keySet().iterator();
								while (itr.hasNext()){
									num = itr.next();
									if (num > wall){
										wall = num;
									}
								}
								Integer total = num + 1;
								plugin.arenas.get(arena.toLowerCase()).getSignWalls().put(total, signWall);
								plugin.arenas.get(arena.toLowerCase()).getSignWalls().get(total).Create();
							}
							else{
								player.sendMessage(ChatColor.RED + "The arena could not be found!");	
							}
						}
						else{
							player.sendMessage(ChatColor.RED + "You need to have put an arena name on line 3");
						}
					}
					else if (event.getLine(1).equalsIgnoreCase("kit")){
						if (KitFile.kitsList.containsKey(event.getLine(2))){
							event.setLine(0, ChatColor.BOLD + "The Walls");
							event.setLine(1, ChatColor.DARK_GRAY + "Kit");
							event.setLine(2, ChatColor.UNDERLINE + "" + event.getLine(2));
							event.setLine(3, "");
						}
						else{
							player.sendMessage(ChatColor.RED + "Kit not found!");
							String list = "";
							for (String string : KitFile.kitsList.keySet()){
								list = list + string + ", ";
							}
							player.sendMessage(ChatColor.RED + "Choose from: " + list);
						}
					}
					else{
						player.sendMessage(ChatColor.RED + "Unknown second line on the sign!");
					}
				}
				else{
					player.sendMessage(ChatColor.RED + "You sign must be put on the walls!");
				}
			}
		}
	}
}
