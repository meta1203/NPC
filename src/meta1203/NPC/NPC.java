package meta1203.NPC;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.logging.*;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;

import com.aranai.virtualchest.VirtualChest;

import redecouverte.npcspawner.*;
/**
 * NPC for Bukkit
 *
 * @author meta1203
 */
public class NPC extends JavaPlugin {
    private final NPCPlayerListener playerListener = new NPCPlayerListener(this);
    private final NPCEntityListener entityListener = new NPCEntityListener(this);
    private final NPCBlockListener blockListener = new NPCBlockListener(this);
    public final NpcSpawner npcs = new NpcSpawner();
    public final BasicHumanNpcList HumanNPCList = new BasicHumanNpcList();
    public final Logger logger = Logger.getLogger("Minecraft");
    //Follower followThread = new Follower();
    public NpcInventories npci = new NpcInventories();
    Attacker attacker = new Attacker(this);

    public NPC() {
        // TODO: Place any custom initialisation code hered
    	//followThread.start();
    	attacker.start();
        // NOTE: Event registration should be done in onEnable not here as all events are unregistered when a plugin is disabled
    }

    public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
    	if (!(sender instanceof Player)) {
    		return false;
    	}
    	
    	Player player = (Player)sender;
    	Location l = player.getLocation();
    	
    	try{
    	if (arg1.getName().equalsIgnoreCase("/spawn") && args.length >= 1) {
    		this.SpawnNPC(args[0], args[0], l);
			return true;
    	}
    	
    	/* if (arg1.getName().equalsIgnoreCase("/follow") && args.length >= 1) {
    		BasicHumanNpc spawned = npcm.spawnNPC(args[0], ((Player)sender).getLocation());
			followThread.followMap.put(spawned, player);
			sender.sendMessage("NPC created and following you.");
			return true;
    	} */
    	
    	if (arg1.getName().equalsIgnoreCase("clearnpcs")) {
    		for (BasicHumanNpc current : HumanNPCList.values()) {
    			NpcSpawner.RemoveBasicHumanNpc(current);
    		}
    		sender.sendMessage("All NPC's cleared");
    		return true;
    	}
    	
    	if (arg1.getName().equalsIgnoreCase("delnpc")) {
    		if (args.length == 1) {
    			NpcSpawner.RemoveBasicHumanNpc(HumanNPCList.get(args[0]));
    			sender.sendMessage("NPC deleted");
    			return true;
    		}
    	}
    	
    	/* if (arg1.getName().equalsIgnoreCase("givenpc")) {
    		if (args.length == 2) {
    			((HumanEntity)npcm.getNPC(args[0]).getBukkitEntity()).getInventory().addItem(new ItemStack(Integer.parseInt(args[1])));
    			sender.sendMessage("NPC given selected item");
    			return true;
    		}
    		if (args.length == 3) {
    			((HumanEntity)npcm.getNPC(args[0]).getBukkitEntity()).getInventory().addItem(new ItemStack(Integer.parseInt(args[1]), Integer.parseInt(args[2])));
    			sender.sendMessage("NPC given selected item");
    			return true;
    		}
    	} */
    	
    	if (arg1.getName().equalsIgnoreCase("/clone") && args.length == 1) {
    		for(int x = 0; x < Integer.parseInt(args[0]); x++) {
    				Location loc = locWithinArea(((Player)sender).getLocation(), Integer.parseInt(args[0])/2);
    				SpawnNPC(((Player)sender).getName(), ((Player)sender).getName(), loc);
    			}
    		
			sender.sendMessage("Cloned " + args[0] + " times.");
			return true;
    	}
    	}
		catch (NumberFormatException e) {
			   sender.sendMessage("One of the arguments needs to be a number!");
			   return false;
		}
    	return false;
    }

    public void onEnable() {
        // TODO: Place any custom enable code here including the registration of any events

        // Register our events
        PluginManager pm = getServer().getPluginManager();
       
        pm.registerEvent(Event.Type.PLAYER_QUIT, playerListener, Priority.Highest, this);
        pm.registerEvent(Event.Type.ENTITY_DAMAGE, entityListener, Priority.Highest, this);
        pm.registerEvent(Event.Type.ENTITY_TARGET, entityListener, Priority.Highest, this);
        // EXAMPLE: Custom code, here we just output some info so we can check all is well
        PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
    }
    
    public Location locWithinArea(Location start, int area) {
    	double x = start.getX();
    	double y = start.getBlockY();
    	double z = start.getZ();
    	Random randomGenerator = new Random();
    	boolean xneg = randomGenerator.nextBoolean();
    	boolean zneg = randomGenerator.nextBoolean();
    	double randomX;
    	if (xneg) {
    		randomX = randomGenerator.nextDouble() * (-area + 1);
    	}
    	else {
    		randomX = randomGenerator.nextDouble() * (area + 1);
    	}
    	double randomZ;
    	if (zneg) {
    		randomZ = randomGenerator.nextDouble() * (-area + 1);
    	}
    	else {
    		randomZ = randomGenerator.nextDouble() * (area + 1);
    	}
    	
    	x = x + randomX;
    	z = z + randomZ;
    	y = start.getWorld().getHighestBlockYAt((int)x, (int)z);
    	
    	return new Location(start.getWorld(),x,y,z);
    }
    public void onDisable() {
        // TODO: Place any custom disable code here
    	
    	for (BasicHumanNpc current : HumanNPCList.values()) {
			NpcSpawner.RemoveBasicHumanNpc(current);
		}
        // NOTE: All registered events are automatically unregistered when a plugin is disabled

        // EXAMPLE: Custom code, here we just output some info so we can check all is well
        System.out.println("NPC has been disabled.");
    }
    
    public void removeSpecial(BasicHumanNpc current) {
    	
    	if (attacker.guardList.contains(current)) {
    		attacker.guardList.remove(current);
    	}
    	
    	/* if (followThread.followMap.containsKey(current)) {
    		followThread.followMap.remove(current);
    	} */
    }
    
    public BasicHumanNpc SpawnNPC(String id, String name, Location l) {
    	int i = 0;
    	while (this.HumanNPCList.get(id) != null) {
            id = id + i;
			i++;
        }
    	
    	BasicHumanNpc hnpc = NpcSpawner.SpawnBasicHumanNpc(id, name, l.getWorld(), l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch());
        this.HumanNPCList.put(id, hnpc);
        VirtualChest vc = new VirtualChest(id);
        npci.put(hnpc, vc);
        return hnpc;
    }
    
    public void respawnAllNpcs(BasicHumanNpcList hnpcl) {
    	for (Map.Entry<String,BasicHumanNpc> current : hnpcl.entrySet()) {
    		npcs.respawnNpc(current.getValue().getCHumanNpc(), current.getKey(), current.getValue().getName());
    		
    	}
    }
    
}

