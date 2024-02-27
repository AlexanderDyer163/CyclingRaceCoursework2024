package cycling;

public class Checkpoint implements IDGenerator {
    protected int checkpointID;
    protected Double location;
    protected CheckpointType type;
    protected Double length;
    protected static int nextID;

    public Checkpoint(Double location, CheckpointType type, Double length) {
        this.location = location;
        this.type = type;
        this.length = length;
        this.checkpointID = GenerateID(nextID);
    }

    public int getCheckpointID() {
        return checkpointID;
    }
}
