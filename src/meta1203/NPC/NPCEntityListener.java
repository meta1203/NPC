package meta1203.NPC;

import net.minecraft.server.ItemStack;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.entity.EntityTargetEvent;

import com.aranai.virtualchest.VirtualChest;

import redecouverte.npcspawner.*;
import redecouverte.npcspawner.NpcEntityTargetEvent.NpcTargetReason;

public class NPCEntityListener extends EntityListener {
    private final NPC plugin;
    private final long lastHit = 0;

    public NPCEntityListener(NPC instance) {
        plugin = instance;
    }
    
    public void onEntityDamage(EntityDamageEvent event) {
    		if (plugin.HumanNPCList.containsBukkitEntity(event.getEntity())) {
    			if (System.currentTimeMillis() - lastHit >= 500) {
    				BasicHumanNpc npc = plugin.HumanNPCList.getBasicHumanNpc(event.getEntity());
        			npc.health = npc.health - event.getDamage();
        			npc.animateHurt();
        			event.setCancelled(true);
        			if (npc.health <= 0) {
        				VirtualChest vc = plugin.npci.getVirtualChest(npc);
        				/* for (ItemStack current : vc.getMcContents()) {
        					if (current != null) {
        						npc.getBukkitEntity().getWorld().dropItemNaturally(npc.getBukkitEntity().getLocation(), new org.bukkit.inventory.ItemStack(current.id, current.count));
        					}
        				} */
        				npc.getCHumanNpc().die();
        				plugin.HumanNPCList.remove(plugin.HumanNPCList.findIdByBNPC(npc));
        				NpcSpawner.RemoveBasicHumanNpc(npc);
        			}
    			}
    	}
    }
    
    public void onEntityTarget(EntityTargetEvent event) {

        if (event instanceof NpcEntityTargetEvent) {
            NpcEntityTargetEvent nevent = (NpcEntityTargetEvent)event;
            BasicHumanNpc npc = plugin.HumanNPCList.getBasicHumanNpc(event.getEntity());
            
            if (nevent.getNpcReason() == NpcTargetReason.NPC_RIGHTCLICKED) {
                Player p = (Player) event.getTarget();
                
                if (p.getItemInHand().getType().equals(Material.BOOK)) {
                	p.sendMessage(npc.getUniqueId());
                    event.setCancelled(true);
                }
                else if (p.getItemInHand().getType().equals(Material.CHEST)) {
                	plugin.npci.syncInventoriesFromNpc(npc);
                	plugin.npci.getVirtualChest(npc).openChest(p);
                }
            }
        }
    }
}