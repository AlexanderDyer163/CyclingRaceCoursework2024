package cycling;

import java.util.ArrayList;
public class Race {
    protected String name;
    protected String description;
    protected ArrayList<Stage> Stages;
    private static int raceID;
    private static int nextID = 1;

    public Race(String name, String description) {
        this.name = name;
        this.description = description;
        this.raceID = GenerateID();
    }

    protected int getRaceID() {
        return raceID;
    }

    private int GenerateID(){
        return nextID++;
    }
}
