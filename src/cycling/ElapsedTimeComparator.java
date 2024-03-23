package cycling;

import java.time.LocalTime;
import java.util.Comparator;

public class ElapsedTimeComparator implements Comparator<RiderResults> {
    public int compare(RiderResults Check1, RiderResults Check2) {
        return Check1.ElapsedTime.compareTo(Check2.ElapsedTime);
    }
}

