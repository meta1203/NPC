package meta1203.NPC;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.Material;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPhysicsEvent;

/**
 * NPC block listener
 * @author meta1203
 */
public class NPCBlockListener extends BlockListener {
    private final NPC plugin;

    public NPCBlockListener(final NPC plugin) {
        this.plugin = plugin;
    }

    //put all Block related code here
}
