package cycling;

import java.io.Serializable;
import java.util.ArrayList;

public class Team implements IDGenerator, Serializable {
    private ArrayList<Integer> riders = new ArrayList<>();

    private int TeamID = GenerateID();
    private String Name;
    private String Description;

    public Team(String name, String description) {
        Name = name;
        Description = description;
    }

    public ArrayList<Integer> getRiders() {
        return riders;
    }
    public void removeRider(int riderID){
        for (int x = 0; x < riders.size();x++){
            if (riders.get(x) == riderID){
                riders.remove(x);
            }
        }
    }

    public int getTeamID() {
        return TeamID;
    }
}
