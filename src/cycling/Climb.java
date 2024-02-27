package cycling;

public class Climb extends Checkpoint{

    protected Double location;

    protected CheckpointType type;
    protected Double length;
    protected Double averageGradient;
    public Climb(Double location, CheckpointType type, Double length, Double averageGradient){
        super(location,type,length);
        this.averageGradient = averageGradient;
        this.checkpointID = GenerateID(nextID);

    }
}
