package cycling;

import java.util.Dictionary;

public class Checkpoint implements IDGenerator {
    protected int checkpointID = GenerateID(nextID++);
    private int parentID;
    protected Double location;
    protected CheckpointType type;
    protected static int nextID;

    public Checkpoint(int parentID, Double location) {
        this.parentID = parentID;
        this.location = location;
    }

    public int getCheckpointID() {
        return checkpointID;
    }

    public void DELETE(Dictionary<Integer, Stage> AllStages){
        AllStages.get(parentID).Checkpoints.remove(this);
    }
}
