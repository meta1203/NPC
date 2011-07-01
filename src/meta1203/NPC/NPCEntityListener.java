package meta1203.NPC;

import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;
import org.martin.bukkit.npclib.NPCEntity;

public class NPCEntityListener extends EntityListener {
    private final NPC plugin;

    public NPCEntityListener(NPC instance) {
        plugin = instance;
    }
    
    public void onEntityDeath(EntityDeathEvent event) {
    	for (NPCEntity current : plugin.attacker.guardList) {
    		if (event.getEntity().equals(current.getBukkitEntity())) {
    			plugin.attacker.guardList.remove(current);
    			System.out.println("NPC killed!");
    		}
    	}
    	for (NPCEntity current : plugin.followThread.followMap.keySet()) {
    		if (event.getEntity().equals(current.getBukkitEntity())) {
    			plugin.followThread.followMap.remove(current);
    			System.out.println("NPC killed!");
    		}
    	}
    	
    }
}