package cycling;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Dictionary;

public class Stage implements IDGenerator{
    private int stageID = GenerateID(nextID++);
    protected String stageName;
    protected StageType type;
    protected Double length;
    private static int nextID;
    protected LocalDateTime startTime;
    protected ArrayList<Checkpoint> Checkpoints = new ArrayList<Checkpoint>();
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

    public int getStageID() {
        return stageID;
    }

    public void setState(String state) {
        State = state;
    }

    public void addCheckpoint(Checkpoint a){
        Checkpoints.add(a);
    }
    public void DELETE(Dictionary<Integer,Race> AllRaces,Dictionary<Integer,Stage> AllStages){
        for(int i = 0; i < Checkpoints.size();i++){
            Checkpoints.get(i).DELETE(AllStages);
        }
        AllRaces.get(ParentID).getStages().remove(this);
        AllStages.remove(stageID);
    }
}
