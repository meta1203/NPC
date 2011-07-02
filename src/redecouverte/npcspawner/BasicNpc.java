package redecouverte.npcspawner;

import java.io.Serializable;


public class BasicNpc implements Serializable{

    private String uniqueId;
    private String name;

    public BasicNpc(String uniqueId, String name)
    {
        this.uniqueId = uniqueId;
        this.name = name;
    }

    public void setName(String newName)
    {
        this.name = newName;
    }

    public String getName()
    {
        return this.name;
    }


    public String getUniqueId()
    {
        return this.uniqueId;
    }

}