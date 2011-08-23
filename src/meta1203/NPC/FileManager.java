package meta1203.NPC;

import java.io.*;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.World;

import redecouverte.npcspawner.BasicHumanNpc;

import com.aranai.virtualchest.VirtualChest;

public class FileManager {
	public NPC plugin;
	public void writeToFile(Object o, String fileS) {
    	try{
    	      //use buffering
    	      FileOutputStream file = new FileOutputStream( fileS );
    	      BufferedOutputStream buffer = new BufferedOutputStream( file );
    	      ObjectOutputStream output = new ObjectOutputStream( buffer );
    	      try{
    	        output.writeObject(o);
    	      }
    	      finally{
    	        output.close();
    	      }
    	    }
    	catch(IOException ex){
    			plugin.logger.log(Level.SEVERE, "Cannot perform output.", ex);
    	    }
    }
	
	public File filesInDataFolder() {
		File npcData = new File("plugins/NPC/data/npcData.dat");
		File npcInv = new File("plugins/NPC/data/npcInv.dat");
		if (npcData.exists() && npcInv.exists()) {
			return npcData;
		}
		return null;
	}
	
	public boolean doesPFolderExist() {
		try {
			File mainfolder = new File("plugins/NPC");
			if (mainfolder.exists()) {
				File dataFolder = new File("plugins/NPC/data");
				File config = new File("plugins/NPC/config.yml");
				if (dataFolder.exists() && config.exists()) {
					return true;
				}
				else {
					if (!dataFolder.exists()) {
						dataFolder.mkdir();
					}
					else {
						config.createNewFile();
					}
				}
			}
			else {
				mainfolder.mkdir();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
    
    public Object readFromFile(String fileS) {
    	try{
    	      //use buffering
    	      FileInputStream file = new FileInputStream("plugins/NPC/data/npcData.dat");
    	      BufferedInputStream buffer = new BufferedInputStream( file );
    	      ObjectInputStream input = new ObjectInputStream ( buffer );
    	      Object returnish;
    	      try{
    	        returnish = input.readObject();
    	        }
    	      finally{
    	        input.close();
    	      }
    	      return returnish;
    	    }
    	    catch(ClassNotFoundException ex){
    	      plugin.logger.log(Level.SEVERE, "Cannot perform input. Class not found.", ex);
    	      return null;
    	    }
    	    catch(IOException ex){
    	      plugin.logger.log(Level.SEVERE, "Cannot perform input.", ex);
    	      return null;
    	    }
    	  }

    public FileManager(NPC tada) {
    	plugin = tada;
    }
    
    public void reinstateNpc() {
    	try{
  	      //use buffering
  	      FileInputStream file = new FileInputStream("plugins/NPC/data/npcData.dat");
  	      BufferedInputStream buffer = new BufferedInputStream( file );
  	      ObjectInputStream input = new ObjectInputStream ( buffer );
  	      try{
  	    	  while (input.readUTF() != null) {
  	    		  String id = input.readUTF();
  	    		  double x = input.readDouble();
  	    		double y = input.readDouble();
  	    		double z = input.readDouble();
  	    		float pitch = input.readFloat();
  	    		float yaw = input.readFloat();
  	    		String name = input.readUTF();
  	    		String worldname = input.readUTF();
  	    		World world = plugin.getServer().getWorld(worldname);
  	    		  BasicHumanNpc bhn = plugin.npcs.SpawnBasicHumanNpc(id, name, world, x, y, z, yaw, pitch);
  	    		  plugin.HumanNPCList.put(id, bhn);
  	    		  // plugin.npci.put(bhn, (VirtualChest) readFromFile("plugins/NPC/data/npcInv.dat"));
  	    	  }
  	    	  
  	      }
  	      finally{
  	        input.close();
  	      }
  	    }
    	catch(EOFException ex) {
    		return;
    	}
  	    catch(IOException ex){
  	      plugin.logger.log(Level.SEVERE, "Cannot perform input.", ex);
  	    }
    }
    
    public void writeToReinstate() {
    	for (Map.Entry<String, BasicHumanNpc> current : plugin.HumanNPCList.entrySet()) {
    		try{
      	      //use buffering
    			File file2 = new File("plugins/NPC/data/npcData.dat");
    			file2.delete();
      	      FileOutputStream file = new FileOutputStream( "plugins/NPC/data/npcData.dat" );
      	      BufferedOutputStream buffer = new BufferedOutputStream( file );
      	      ObjectOutputStream output = new ObjectOutputStream( buffer );
      	      try{
      	    	output.writeUTF("");
      	    	output.writeUTF(current.getKey());
        		output.writeDouble(current.getValue().getCHumanNpc().getBukkitEntity().getLocation().getX());
        		output.writeDouble(current.getValue().getCHumanNpc().getBukkitEntity().getLocation().getY());
        		output.writeDouble(current.getValue().getCHumanNpc().getBukkitEntity().getLocation().getZ());
        		output.writeFloat(current.getValue().getCHumanNpc().getBukkitEntity().getLocation().getPitch());
        		output.writeFloat(current.getValue().getCHumanNpc().getBukkitEntity().getLocation().getYaw());
        		output.writeUTF(current.getValue().getName());
        		output.writeUTF(current.getValue().getBukkitEntity().getWorld().getName());
      	      }
      	      finally{
      	        output.close();
      	      }
      	    }
      	catch(IOException ex){
      			plugin.logger.log(Level.SEVERE, "Cannot perform output.", ex);
      	    }
    		
    		
    	}
    	
    	for (VirtualChest vc : plugin.npci.values()) {
    		writeToFile(vc, "plugins/NPC/data/npcInv.dat");
    	}
    }
}
