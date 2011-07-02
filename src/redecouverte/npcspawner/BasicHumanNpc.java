package redecouverte.npcspawner;

import java.util.logging.Logger;
import net.minecraft.server.EntityLiving;

import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;

public class BasicHumanNpc extends BasicNpc {

    private CHumanNpc mcEntity;
    public int health = 20;
    private static final Logger logger = Logger.getLogger("Minecraft");

    public CHumanNpc getCHumanNpc() {
    	return mcEntity;
    }
    public BasicHumanNpc(CHumanNpc entity, String uniqueId, String name) {
        super(uniqueId, name);

        this.mcEntity = entity;
    }

    public HumanEntity getBukkitEntity() {
        return (HumanEntity) this.mcEntity.getBukkitEntity();
    }

    protected CHumanNpc getMCEntity() {
        return this.mcEntity;
    }

    public void moveTo(double x, double y, double z, float yaw, float pitch) {
        this.mcEntity.setLocation(x, y, z, yaw, pitch);
    }

    public void attackLivingEntity(LivingEntity ent) {
        try {
            this.mcEntity.animateArmSwing();
            this.mcEntity.damageEntity((((CraftEntity)ent).getHandle()),2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void animateArmSwing()
    {
        this.mcEntity.animateArmSwing();
    }
    
    public void animateHurt() {
    	this.mcEntity.animateHurt();
    }
}
