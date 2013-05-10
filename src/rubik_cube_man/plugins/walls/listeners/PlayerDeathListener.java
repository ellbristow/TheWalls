package rubik_cube_man.plugins.walls.listeners;

import java.util.Iterator;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.kitteh.tag.TagAPI;
import rubik_cube_man.plugins.walls.Arena;
import rubik_cube_man.plugins.walls.Walls;

public class PlayerDeathListener implements Listener {

    private Walls plugin;

    public PlayerDeathListener(Walls walls) {
        plugin = walls;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void PlayerDeathEvent(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (plugin.playerarena.get(player) != null) {
                if (player.getHealth() <= event.getDamage() && plugin.arenas.get(plugin.playerarena.get(player)).getStarted() == true && plugin.arenas.get(plugin.playerarena.get(player)).getCounter() <= 0) {
                    if (plugin.arenas.containsKey(plugin.playerarena.get(player))) {
                        plugin.teleportable.put(player, true);
                        Iterator<Player> list = plugin.arenas.get(plugin.playerarena.get(player)).getPlayerList().keySet().iterator();
                        while (list.hasNext()) {
                            Player name = list.next();
                            if ("red".equals(plugin.arenas.get(plugin.playerarena.get(player)).getPlayerList().get(player))) {
                                name.sendMessage(ChatColor.RED + "" + player.getName() + ChatColor.AQUA + " has died!");
                            } else if ("blue".equals(plugin.arenas.get(plugin.playerarena.get(player)).getPlayerList().get(player))) {
                                name.sendMessage(ChatColor.BLUE + "" + player.getName() + ChatColor.AQUA + " has died!");
                            } else if ("green".equals(plugin.arenas.get(plugin.playerarena.get(player)).getPlayerList().get(player))) {
                                name.sendMessage(ChatColor.GREEN + "" + player.getName() + ChatColor.AQUA + " has died!");
                            } else if ("yellow".equals(plugin.arenas.get(plugin.playerarena.get(player)).getPlayerList().get(player))) {
                                name.sendMessage(ChatColor.YELLOW + "" + player.getName() + ChatColor.AQUA + " has died!");
                            }
                        }
                        PlayerInventory inv = player.getInventory();
                        Location loc = player.getLocation();
                        for (ItemStack i : inv.getContents()) {
                            if (i != null) {
                                loc.getWorld().dropItemNaturally(loc, i);
                            }
                        }
                        if (player.getInventory().getHelmet() != null) {
                            loc.getWorld().dropItemNaturally(loc, player.getInventory().getHelmet());
                        }
                        if (player.getInventory().getChestplate() != null) {
                            loc.getWorld().dropItemNaturally(loc, player.getInventory().getChestplate());
                        }
                        if (player.getInventory().getLeggings() != null) {
                            loc.getWorld().dropItemNaturally(loc, player.getInventory().getLeggings());
                        }
                        if (player.getInventory().getBoots() != null) {
                            loc.getWorld().dropItemNaturally(loc, player.getInventory().getBoots());
                        }
                        TagAPI.refreshPlayer(player);
                        plugin.arenas.get(plugin.playerarena.get(player)).setRandom(player);
                        plugin.arenas.get(plugin.playerarena.get(player)).Leave();
                        plugin.arenas.get(plugin.playerarena.get(player)).getPlayerList().remove(player);
                        player.teleport(plugin.arenas.get(plugin.playerarena.get(player)).getLose());
                        event.setCancelled(true);
                        player.setHealth(20);
                        player.setFoodLevel(20);
                        player.setFireTicks(0);
                        plugin.arenas.get(plugin.playerarena.get(player)).playerJoin();
                    }
                    if (plugin.arenas.get(plugin.playerarena.get(player)).getTotal() == plugin.arenas.get(plugin.playerarena.get(player)).getRedplayers() || plugin.arenas.get(plugin.playerarena.get(player)).getTotal() == plugin.arenas.get(plugin.playerarena.get(player)).getBlueplayers() || plugin.arenas.get(plugin.playerarena.get(player)).getTotal() == plugin.arenas.get(plugin.playerarena.get(player)).getGreenplayers() || plugin.arenas.get(plugin.playerarena.get(player)).getTotal() == plugin.arenas.get(plugin.playerarena.get(player)).getYellowplayers()) {
                        Arena arena = plugin.arenas.get(plugin.playerarena.get(player));
                        arena.broadcastWinner();
                        for (Player play : plugin.arenas.get(plugin.playerarena.get(player)).getPlayerList().keySet()) {
                            plugin.teleportable.put(play, true);
                            arena.setRandom(play);
                            arena.Leave();
                            plugin.playerarena.remove(play);
                            play.teleport(arena.getWin());
                            play.setHealth(20);
                            play.setFoodLevel(20);
                            play.setFallDistance(0);
                            play.setFireTicks(0);
                        }
                        arena.getPlayerList().clear();
                        plugin.arenas.get(plugin.playerarena.get(player)).setCountToEnd(0);
                    }
                    plugin.playerarena.remove(player);
                }
            }
            if (plugin.arenas.containsKey(plugin.playerarena.get(player))) {
                if (plugin.arenas.get(plugin.playerarena.get(player)).getCounter() == null || plugin.arenas.get(plugin.playerarena.get(player)).getCounter() >= 0) {
                    event.setCancelled(true);
                }
            }
        }
    }
}