package meta1203.NPC;

import java.util.HashMap;

import org.bukkit.inventory.ItemStack;

import com.aranai.virtualchest.VirtualChest;
import redecouverte.npcspawner.BasicHumanNpc;

public class NpcInventories extends HashMap<BasicHumanNpc, VirtualChest> {
	private static final long serialVersionUID = 3971314932644965153L;
	
	public VirtualChest getVirtualChest(BasicHumanNpc npc) {
		return this.get(npc);
	}
	
	public void syncInventoriesToNpc(BasicHumanNpc npc) {
		VirtualChest vc = this.getVirtualChest(npc);
		npc.getBukkitEntity().getInventory().clear();
		if (!vc.isEmpty()) {
			for (ItemStack current : vc.getContents()) {
				if (current != null) {
					npc.getBukkitEntity().getInventory().addItem(current);
				}
			}
		}
	}
	
	public void syncInventoriesFromNpc(BasicHumanNpc npc) {
		VirtualChest vc = this.getVirtualChest(npc);
		vc.emptyChest();
		if (npc.getBukkitEntity().getInventory().firstEmpty() != 0) {
			for (ItemStack current : npc.getBukkitEntity().getInventory().getContents()) {
				if (current != null) {
					vc.addItem(current);
				}
			}
		}
	}
}
