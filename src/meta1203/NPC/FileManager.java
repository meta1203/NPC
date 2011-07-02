package meta1203.NPC;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

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
		if (npcData.exists()) {
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
    	      FileInputStream file = new FileInputStream(fileS);
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
}
