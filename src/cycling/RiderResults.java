package cycling;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;

public class RiderResults implements Serializable {
    public LocalTime[] checkpointTimes;//In the same order as the checkpoints in stage
    public LocalTime FinishTime;
    public LocalTime ElapsedTime;
    public int StageID;
    public int RiderID;
    public RiderResults(int stageID, int riderID,LocalTime[] checkpointTimes) {
        StageID = stageID;
        RiderID = riderID;
        this.checkpointTimes = checkpointTimes;
        FinishTime = checkpointTimes[checkpointTimes.length-1];
        ElapsedTime = FinishTime.minusHours(checkpointTimes[0].getHour())
                .minusMinutes(checkpointTimes[0].getMinute())
                .minusSeconds(checkpointTimes[0].getSecond())
                .minusNanos(checkpointTimes[0].getNano());
    }
}
