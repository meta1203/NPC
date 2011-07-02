package redecouverte.npcspawner;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Entity;


public class BasicHumanNpcList extends HashMap<String, BasicHumanNpc> {

    public boolean containsBukkitEntity(Entity entity)
    {
        for(BasicHumanNpc bnpc : this.values())
        {
            if(bnpc.getBukkitEntity().getEntityId() == entity.getEntityId())
                return true;
        }

        return false;
    }

    public BasicHumanNpc getBasicHumanNpc(Entity entity)
    {
        for(BasicHumanNpc bnpc : this.values())
        {
            if(bnpc.getBukkitEntity().getEntityId() == entity.getEntityId())
                return bnpc;
        }

        return null;
    }

    public String findIdByBNPC(BasicHumanNpc npc) {
    	for (Map.Entry<String, BasicHumanNpc> current : this.entrySet()) {
    		if (current.getValue().equals(current)) {
    			return current.getKey();
    		}
    	}
    	return "";
    }
}
