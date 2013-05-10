package rubik_cube_man.plugins.walls.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import rubik_cube_man.plugins.walls.Walls;

public class PlayerPVPListener implements Listener {

    private Walls plugin;

    public PlayerPVPListener(Walls walls) {
        plugin = walls;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerPVPEvent(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            if (event.getDamager() instanceof Player) {
                Player player = (Player) event.getEntity();
                Player attacker = (Player) event.getDamager();
                if (plugin.playerarena.get(player) != null) {
                    if (event.getCause() == DamageCause.POISON || event.getCause() == DamageCause.PROJECTILE || event.getCause() == DamageCause.ENTITY_ATTACK || event.getCause() == DamageCause.CONTACT) {
                        if (plugin.arenas.get(plugin.playerarena.get(player)).getPlayerList().get(attacker) != null) {
                            if (plugin.arenas.get(plugin.playerarena.get(player)).getPlayerList().get(attacker).equals(plugin.arenas.get(plugin.playerarena.get(player)).getPlayerList().get(player))) {
                                event.setCancelled(true);
                            }
                        }
                    }
                }
            }
        }
    }
}
