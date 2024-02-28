package cycling;

public class Sprint extends Checkpoint{
    public Sprint(int parentID,Double location) {
        super(parentID, location);
        this.type = CheckpointType.SPRINT;
    }
}
