package cycling;

import java.io.Serializable;
import java.util.Dictionary;

public class Checkpoint implements IDGenerator, Serializable {
    protected int checkpointID = GenerateID();
    private int parentID;
    protected Double location;
    protected CheckpointType type;
    protected static int nextID;

    public Checkpoint(int parentID, Double location) {
        this.parentID = parentID;
        this.location = location;
    }

    public int getParentID() {
        return parentID;
    }

    public int getCheckpointID() {
        return checkpointID;
    }

    public void DELETE(Dictionary<Integer, Stage> AllStages){
        AllStages.get(parentID).Checkpoints.remove(this);
    }
}
