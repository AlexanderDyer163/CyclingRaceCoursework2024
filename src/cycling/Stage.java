package cycling;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Stage implements IDGenerator{
    protected int stageID;
    protected String stageName;
    protected StageType type;
    protected Double length;
    private static int nextID = 1;
    protected LocalDateTime startTime;
    protected ArrayList<Checkpoint> Checkpoints;
    protected int ParentID;
    public Double getLength() {
        return length;
    }

    public Stage(String stageName, StageType type, String description, Double length, LocalDateTime startTime,int parentID) {
        this.stageName = stageName;
        this.type = type;
        this.length = length;
        this.startTime = startTime;
        this.stageID = GenerateID(nextID);
        this.ParentID = parentID;

    }

    public void addCheckpoint(Checkpoint a){
        Checkpoints.add(a);
    }
    public void DELETE(){
        for(int i = 0; i < Checkpoints.size();i++){
            Checkpoints.remove(i);
        }
    }
}
