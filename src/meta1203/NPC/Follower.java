package meta1203.NPC;

import java.util.*;
import org.bukkit.entity.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import redecouverte.npcspawner.*;

public class Follower extends Thread {
	public HashMap<BasicHumanNpc, Player> followMap = new HashMap<BasicHumanNpc, Player>();
	
	public void run() {
		while (true) {
			for (Map.Entry<BasicHumanNpc, Player> entry : followMap.entrySet()) {
				entry.getKey().pathFindTo(entry.getValue().getLocation(), 10);
			}
			try {
				synchronized (this) {
				this.wait(50);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
