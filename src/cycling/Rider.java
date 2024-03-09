package cycling;

import java.time.LocalTime;
import java.util.Dictionary;
import java.util.Hashtable;

public class Rider implements IDGenerator{
    private int nextID = 0;
    private int id = GenerateID(nextID++);
    private Dictionary<Integer, RiderResults> StageResults = new Hashtable<>();
    private int CurrentTeamID;
    private int YearOfBirth;
    private String name;

    public Rider(int CurrentTeamID, String name,int yearOfBirth) {
        this.CurrentTeamID = CurrentTeamID;
        this.YearOfBirth = yearOfBirth;
        this.name = name;
    }

    public int getCurrentTeamID() {
        return CurrentTeamID;
    }
    public RiderResults getStageResults(int stageID){
        return StageResults.get(stageID);
    }

    public int getId() {
        return id;
    }
    public void registerResults(int stageID, LocalTime... RegisterTime){
        RiderResults results = new RiderResults(id,stageID,RegisterTime);
    }


}
