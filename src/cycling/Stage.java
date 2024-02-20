package cycling;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.SplittableRandom;

public class Stage {
    protected int stageID;
    protected String stageName;
    protected StageType type;
    protected Double length;
    protected LocalDateTime startTime;
    protected ArrayList<Checkpoint> Checkpoints;
}
