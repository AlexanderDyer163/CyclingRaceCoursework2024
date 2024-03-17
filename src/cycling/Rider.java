package cycling;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public class Rider implements IDGenerator{

    private int id = GenerateID();
    private int CurrentTeamID;
    private int YearOfBirth;
    private String name;

    private RiderResults StageResults;

    public Rider(int CurrentTeamID, String name,int yearOfBirth) {
        this.CurrentTeamID = CurrentTeamID;
        this.YearOfBirth = yearOfBirth;
        this.name = name;
    }

    public int getCurrentTeamID() {
        return CurrentTeamID;
    }


    public int getId() {
        return id;
    }



}
