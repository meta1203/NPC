package meta1203.NPC;
import java.util.*;

import net.minecraft.server.EntityLiving;

import org.bukkit.entity.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import redecouverte.npcspawner.*;

public class Attacker extends Thread {
public HashSet<BasicHumanNpc> guardList = new HashSet<BasicHumanNpc>();
public NPC plugin;

public Attacker(NPC npc) {
	this.plugin = npc;
}
	
	public void run() {
		while (true) {
			for (BasicHumanNpc current : guardList) {
				List<Entity> toAttack = current.getBukkitEntity().getNearbyEntities(4, 4, 4);
				for (Entity currentE : toAttack) {
					if (currentE instanceof LivingEntity) {
						current.attackLivingEntity((LivingEntity)currentE);
					}
				}
			} 
			
			for (BasicHumanNpc current : plugin.npci.keySet()) {
				plugin.npci.syncInventoriesToNpc(current);
			}
			
			try {
				synchronized (this) {
				this.wait(500);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
