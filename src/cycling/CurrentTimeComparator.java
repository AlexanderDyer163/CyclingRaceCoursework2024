package cycling;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;

public class CurrentTimeComparator implements Comparator<RiderResults>{

    @Override
    public int compare(RiderResults o1, RiderResults o2) {
        return o1.checkpointTimes[0].compareTo(o2.checkpointTimes[0]);
    }
}
