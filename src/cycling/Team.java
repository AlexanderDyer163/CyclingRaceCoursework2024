package cycling;

import java.util.ArrayList;

public class Team implements IDGenerator {
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
        riders.remove(riderID);
    }

    public int getTeamID() {
        return TeamID;
    }
}
