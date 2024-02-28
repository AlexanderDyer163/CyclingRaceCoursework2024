package cycling;

public class Climb extends Checkpoint{
    private Double length;
    private Double averageGradient;
    public Climb(int parentID, double location,CheckpointType type,Double length, Double averageGradient){
        super(parentID,location);
        this.type = type;
        this.length = length;
        this.averageGradient = averageGradient;
    }
}
