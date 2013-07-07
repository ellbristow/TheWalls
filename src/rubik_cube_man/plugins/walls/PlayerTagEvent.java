package rubik_cube_man.plugins.walls;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kitteh.tag.PlayerReceiveNameTagEvent;

public class PlayerTagEvent implements Listener {

	private Walls plugin;

	public PlayerTagEvent(Walls walls) {
		this.plugin = walls;
	}
	
	@EventHandler
	public void onNameTag(PlayerReceiveNameTagEvent event) {
		if (plugin.playerarena.get(event.getPlayer()) != null){
			if (plugin.arenas.get(plugin.playerarena.get(event.getPlayer())).getPlayerList().get(event.getNamedPlayer()) == null){
				event.setTag(ChatColor.RESET + event.getNamedPlayer().getName());
			}
			else if (plugin.arenas.get(plugin.playerarena.get(event.getPlayer())).getPlayerList().get(event.getNamedPlayer()) == "red"){
				event.setTag(ChatColor.RED + event.getNamedPlayer().getName());
			}
			else if (plugin.arenas.get(plugin.playerarena.get(event.getPlayer())).getPlayerList().get(event.getNamedPlayer()) == "blue"){
				event.setTag(ChatColor.BLUE + event.getNamedPlayer().getName());
			}
			else if (plugin.arenas.get(plugin.playerarena.get(event.getPlayer())).getPlayerList().get(event.getNamedPlayer()) == "green"){
				event.setTag(ChatColor.GREEN + event.getNamedPlayer().getName());
			}
			else if (plugin.arenas.get(plugin.playerarena.get(event.getPlayer())).getPlayerList().get(event.getNamedPlayer()) == "yellow"){
				event.setTag(ChatColor.YELLOW + event.getNamedPlayer().getName());
			}
		}
	}
}
