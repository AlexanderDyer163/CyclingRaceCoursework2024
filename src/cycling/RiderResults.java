package cycling;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RiderResults {
    private int riderID;
    private int stageID;
    private LocalTime[] checkpointTimes;//this needs to be converted to Double[] before returning

    public RiderResults(int id, int stageID, LocalTime[] registerTime) {
        this.riderID = id;
        this.stageID = stageID;
        this.checkpointTimes = registerTime;
    }

    public LocalTime[] getCheckpointTimes() {
        return checkpointTimes;
    }
}
