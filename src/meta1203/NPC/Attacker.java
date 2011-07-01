package meta1203.NPC;
import java.util.*;

import net.minecraft.server.EntityLiving;

import org.bukkit.entity.*;
import org.martin.bukkit.npclib.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class Attacker extends Thread {
public HashSet<NPCEntity> guardList = new HashSet<NPCEntity>();
	
	public void run() {
		while (true) {
			for (NPCEntity current : guardList) {
				List<Entity> toAttack = current.getBukkitEntity().getNearbyEntities(4, 4, 4);
				for (Entity currentE : toAttack) {
					if (currentE instanceof LivingEntity) {
						current.attackLivingEntity((LivingEntity)currentE);
					}
				}
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
