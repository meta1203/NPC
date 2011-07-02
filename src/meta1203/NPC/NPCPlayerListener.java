package meta1203.NPC;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Handle events for all Player related events
 * @author meta1203
 */
public class NPCPlayerListener extends PlayerListener {
    private final NPC plugin;

    public NPCPlayerListener(NPC instance) {
        plugin = instance;
    }

    //Insert Player related code here
    /* public void onPlayerQuit(PlayerQuitEvent event) {
    	 if (plugin.followThread.followMap.containsValue(event.getPlayer())) {
    		plugin.followThread.followMap.remove(event.getPlayer());
    	} 
    } */
}