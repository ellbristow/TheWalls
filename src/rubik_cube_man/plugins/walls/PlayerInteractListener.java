package rubik_cube_man.plugins.walls;

import java.util.Iterator;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {
	
	private Walls plugin;

	public PlayerInteractListener(Walls walls) {
		this.plugin = walls;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler (priority=EventPriority.HIGHEST)
	public void PlayerInteractEvent(PlayerInteractEvent event) {
		if (plugin.playerarena.get(event.getPlayer()) == null){
			if (event.getPlayer().getItemInHand().getType().equals(Material.CLAY_BRICK)){
				if (event.getPlayer().hasPermission("walls.create")){
					if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
						event.setCancelled(true);
						plugin.location1.put(event.getPlayer(), event.getClickedBlock().getLocation());
						event.getPlayer().sendMessage(ChatColor.DARK_GREEN + "Point 1 set!");
					}
					else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
						event.setCancelled(true);
						plugin.location2.put(event.getPlayer(), event.getClickedBlock().getLocation());
						event.getPlayer().sendMessage(ChatColor.DARK_GREEN + "Point 2 set!");
					}
				}
			}
		}
		if (event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_BLOCK){
			if (event.getClickedBlock().getType() == Material.WALL_SIGN){
				boolean aSign  = false;
				String arenaName = null;
				Integer thing = 0;
				Iterator<String> arenas = plugin.arenas.keySet().iterator();
				while (arenas.hasNext() && aSign == false){
					String arena = arenas.next();
					Iterator<Integer> wallsSigns = plugin.arenas.get(arena).getSignWalls().keySet().iterator();
					while (wallsSigns.hasNext() && aSign == false){
						Integer num = wallsSigns.next();
						if (event.getClickedBlock().getLocation().getX() == plugin.arenas.get(arena).getSignWalls().get(num).getClickSign().getX()){
							if (event.getClickedBlock().getLocation().getY() == plugin.arenas.get(arena).getSignWalls().get(num).getClickSign().getY()){
								if (event.getClickedBlock().getLocation().getZ() == plugin.arenas.get(arena).getSignWalls().get(num).getClickSign().getZ()){
									aSign = true;
									thing = num;
									arenaName = arena;
								}
							}
						}
					}
				}
				if (aSign == true){
					Player player = event.getPlayer();
					event.setCancelled(true);
					if (event.getPlayer().getItemInHand().getType() == Material.CLAY_BRICK && event.getPlayer().hasPermission("walls.signwall")){
						plugin.arenas.get(arenaName).getSignWalls().remove(thing);
						event.getPlayer().sendMessage(ChatColor.RED + "Sign Walls has been removed!");
					}
					else if (player.hasPermission("walls.signjoin")){
						if (plugin.playerarena.get(player) == null){
							if (plugin.arenas.get(arenaName).getStarted() == false){
								if (plugin.arenas.get(arenaName).getLose() != null && plugin.arenas.get(arenaName).getWin() != null && plugin.arenas.get(arenaName).getLobby() != null){
									if(plugin.arenas.get(arenaName).getMin() > 0){
										if (plugin.arenas.get(arenaName).getDropTime() != null){
										Integer num = plugin.arenas.get(arenaName).getMin() + plugin.arenas.get(arenaName).getMin() + plugin.arenas.get(arenaName).getMin() + plugin.arenas.get(arenaName).getMin();
											if (plugin.arenas.get(arenaName).getTotal() < num){
												player.teleport(plugin.arenas.get(arenaName).getLobby());
												plugin.playerarena.put(player, arenaName);
										    	plugin.teleportable.put(player, false);
												plugin.arenas.get(arenaName).setTotal(plugin.arenas.get(arenaName).getTotal() + 1);
												player.sendMessage(ChatColor.GRAY + "Joined arena " + arenaName + "!");
												plugin.arenas.get(arenaName).getPlayerList().put(player , "lobby");
												plugin.arenas.get(plugin.playerarena.get(player)).getPlayerList().put(player, "lobby");
												plugin.arenas.get(arenaName).getPlayerInventories().put(player, player.getInventory().getContents());
												plugin.arenas.get(arenaName).getAurmor().put(player , player.getInventory().getArmorContents());
												Integer tempExp = (Integer)player.getLevel();
												plugin.arenas.get(arenaName).getExp().put(player , tempExp);
												player.getInventory().clear();
												player.getInventory().setArmorContents(null);
												player.updateInventory();
											    player.setHealth(20);
											    player.setFoodLevel(20);
											    plugin.arenas.get(arenaName).getLobbyPlayers().add(player);
												player.setLevel(0);
												player.setExp(0);
												player.setGameMode(GameMode.ADVENTURE);
												plugin.arenas.get(arenaName).playerJoin();
												plugin.arenas.get(arenaName).playerAdd(player.getName());
												Iterator<Player> list2 = plugin.arenas.get(arenaName).getPlayerList().keySet().iterator();
												while(list2.hasNext()){
													Player names = list2.next();
													Integer min4 = plugin.arenas.get(arenaName).getMin() * 4;
													names.sendMessage(ChatColor.DARK_PURPLE + player.getName() + " has joined the lobby. " + plugin.arenas.get(arenaName).getTotal() + "/" + min4);
												}
												if (plugin.arenas.get(plugin.playerarena.get(player)).getTotal() == 2){
													plugin.arenas.get(arenaName).setCounter(80);
													Iterator<Player> list = plugin.arenas.get(arenaName).getPlayerList().keySet().iterator();
													while(list.hasNext()){
													Player names = list.next();
													names.sendMessage(ChatColor.BLUE + "Game starting soon!");
												}
											}
										}
										else{
											player.sendMessage(ChatColor.RED + "This arena is full. Try again later!");
										}
									}
									else{
										player.sendMessage(ChatColor.RED + "This arena does not have a set Time yet!");
									}
								}
								else{
									player.sendMessage(ChatColor.RED + "This arena has not got any spawns for teams");
								}
							}
							else{
								player.sendMessage(ChatColor.RED + "This arena has not been set up yet!");
							}
						}
						else{
								player.sendMessage(ChatColor.RED + "That Game has already started!");
						}
					}
					else{
						player.sendMessage(ChatColor.RED + "You can't join 2 arenas!");
					}
				}
				else{
					player.sendMessage(ChatColor.RED + "You do not have permission to join the walls like this!");
				}
			}
		}
	}
	}
}