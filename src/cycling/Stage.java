package cycling;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;

public class Stage implements IDGenerator, Serializable {
    private int stageID = GenerateID();
    protected String stageName;
    protected StageType type;
    protected Double length;
    protected LocalDateTime startTime;
    protected ArrayList<Checkpoint> Checkpoints = new ArrayList<Checkpoint>();
    public Dictionary<Integer,RiderResults> AllRidersResults = new Hashtable<>();//holds the riders results for this stage
    protected int ParentID;
    private String State = "Preparing";
    public Double getLength() {
        return length;
    }

    public Stage(String stageName, StageType type, String description, Double length, LocalDateTime startTime,int parentID) {
        this.stageName = stageName;
        this.type = type;
        this.length = length;
        this.startTime = startTime;
        this.ParentID = parentID;
    }

    public RiderResults getRiderResults(int RiderID) {
        return AllRidersResults.get(RiderID);
    }

    public int getStageID() {
        return stageID;
    }

    public void setState(String state) {
        State = state;
    }

    public void addCheckpoint(Checkpoint a){
        Checkpoints.add(a);
    }
    public void registerResults(int RiderID,RiderResults a){
        AllRidersResults.put(RiderID,a);
    }
    public void DELETE(Dictionary<Integer,Race> AllRaces,Dictionary<Integer,Stage> AllStages){
        for(int i = 0; i < Checkpoints.size();i++){
            Checkpoints.get(i).DELETE(AllStages);
        }
        AllRaces.get(ParentID).getStages().remove(this);
        AllStages.remove(stageID);
    }

}
