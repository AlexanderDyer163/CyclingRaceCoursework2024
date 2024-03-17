package cycling;

import java.time.LocalTime;
import java.util.ArrayList;

public class RiderResults {
    public LocalTime[] checkpointTimes;
    public LocalTime FinishTime;
    public int StageID;
    public int RiderID;
    public RiderResults(int stageID, int riderID,LocalTime[] checkpointTimes) {
        StageID = stageID;
        RiderID = riderID;
        this.checkpointTimes = checkpointTimes;
        FinishTime = checkpointTimes[checkpointTimes.length-1];
    }
}
