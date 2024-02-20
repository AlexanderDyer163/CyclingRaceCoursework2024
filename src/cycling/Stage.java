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

    public Double getLength() {
        return length;
    }

    public Stage(String stageName, StageType type, String description, Double length, LocalDateTime startTime) {
        this.stageName = stageName;
        this.type = type;
        this.length = length;
        this.startTime = startTime;
        this.stageID = GenerateID(nextID);
    }
    public void DELETE(){
        //Will continue the chain to delete checkpoints as well. its a fairly simple process
    }
}
