package rubik_cube_man.plugins.walls;

import java.util.Iterator;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.kitteh.tag.TagAPI;

public class PlayerDisconnectListner implements Listener {
	private Walls plugin;

	public PlayerDisconnectListner(Walls walls) {
		this.plugin = walls;
	}
	@EventHandler
	public void OnPlayerDisconnect(PlayerQuitEvent event){
		Player player = event.getPlayer();
		String arena = plugin.playerarena.get(player);
		if (plugin.arenas.containsKey(plugin.playerarena.get(player))){
    		plugin.teleportable.put(player, true);
			if (plugin.arenas.get(arena).getCounter() != null && plugin.arenas.get(arena).getCounter() < 10){
				Iterator<Player> list = plugin.arenas.get(plugin.playerarena.get(player)).getPlayerList().keySet().iterator();
				while(list.hasNext()){
					Player name = list.next();
					if (plugin.arenas.get(plugin.playerarena.get(player)).getPlayerList().get(player) == "red"){
						name.sendMessage(ChatColor.RED + "" + player.getName() + ChatColor.AQUA + " has left the arena");
					}
					else if (plugin.arenas.get(plugin.playerarena.get(player)).getPlayerList().get(player) == "blue"){
						name.sendMessage(ChatColor.BLUE + "" + player.getName() + ChatColor.AQUA + " has left the arena");
					}
					else if (plugin.arenas.get(plugin.playerarena.get(player)).getPlayerList().get(player) == "green"){
						name.sendMessage(ChatColor.GREEN + "" + player.getName() + ChatColor.AQUA + " has left the arena");		
					}
					else if (plugin.arenas.get(plugin.playerarena.get(player)).getPlayerList().get(player) == "yellow"){
						name.sendMessage(ChatColor.YELLOW + "" + player.getName() + ChatColor.AQUA + " has left the arena!");	
					}
				}
				TagAPI.refreshPlayer(player);
			    plugin.arenas.get(plugin.playerarena.get(player)).setRandom(player);
			    plugin.arenas.get(plugin.playerarena.get(player)).Leave();
			    plugin.arenas.get(plugin.playerarena.get(player)).getPlayerList().remove(player);
			    player.setHealth(20);
			    player.setFoodLevel(20);
			    player.setFireTicks(0);
			    if (plugin.arenas.get(plugin.playerarena.get(player)).getTotal() == plugin.arenas.get(plugin.playerarena.get(player)).getRedplayers() || plugin.arenas.get(plugin.playerarena.get(player)).getTotal() == plugin.arenas.get(plugin.playerarena.get(player)).getBlueplayers() || plugin.arenas.get(plugin.playerarena.get(player)).getTotal() == plugin.arenas.get(plugin.playerarena.get(player)).getGreenplayers() || plugin.arenas.get(plugin.playerarena.get(player)).getTotal() == plugin.arenas.get(plugin.playerarena.get(player)).getYellowplayers()){
			    	Arena arenas = plugin.arenas.get(plugin.playerarena.get(player));
			    	arenas.broadcastWinner();
			    	for (Player play : plugin.arenas.get(plugin.playerarena.get(player)).getPlayerList().keySet()){
			    		plugin.teleportable.put(play, true);
			    		arenas.setRandom(play);
			    		arenas.Leave();
				    	plugin.playerarena.remove(play);
			    		play.teleport(arenas.getWin());
			    		play.setHealth(20);
			    		play.setFoodLevel(20);
			    		play.setFallDistance(0);
			    		play.setFireTicks(0);
				    }
			    	arenas.getPlayerList().clear();
				    plugin.arenas.get(plugin.playerarena.get(player)).setCountToEnd(0);
			    }
			    plugin.playerarena.remove(player);
			}
			else {
				plugin.arenas.get(arena).getLobbyPlayers().remove(player);
				String arena2 = plugin.playerarena.get(player);
				plugin.playerarena.remove(player);
			    plugin.arenas.get(arena).setRandom(player);
			    plugin.arenas.get(arena).Leave();
			    player.teleport(plugin.arenas.get(arena2).getLose());
			    plugin.arenas.get(arena2).getPlayerList().remove(player);
			    player.setHealth(20);
			    player.setFoodLevel(20);
			    player.setFireTicks(0);
				Iterator<Player> list = plugin.arenas.get(arena).getPlayerList().keySet().iterator();
				if (list != null){
					while(list.hasNext()){
					Player name = list.next();
						if (name != null){
							name.sendMessage(ChatColor.AQUA + "" + player.getName() + " has left the lobby!");
						}
					}
				}
			    if (plugin.arenas.get(arena).getTotal() <= 1){
			    	plugin.arenas.get(arena).setCounter(null);
					Iterator<Player> list2 = plugin.arenas.get(arena).getPlayerList().keySet().iterator();
					if (list2 != null){
						while(list2.hasNext()){
							Player names = list2.next();
							names.sendMessage(ChatColor.RED + "Game stopped because there are not enough people");
						}
					}
			    }
			    player.teleport(plugin.arenas.get(arena).getLose());
			    plugin.arenas.get(arena).playerJoin();
			}
		}
	}
}
