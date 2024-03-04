package cycling;

import java.util.ArrayList;

public class Team implements IDGenerator {
    private ArrayList<Rider> riders = new ArrayList<Rider>();
    private int nextID = 1;
    private int TeamID = GenerateID(nextID++);
    private String Name;
    private String Description;

    public Team(String name, String description) {
        Name = name;
        Description = description;
    }

    public ArrayList<Rider> getRiders() {
        return riders;
    }

    public int getTeamID() {
        return TeamID;
    }
}
