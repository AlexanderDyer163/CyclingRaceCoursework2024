package cycling;

import java.time.LocalTime;
import java.util.Comparator;

public class FinishTimeComparator implements Comparator<RiderResults> {
    public int compare(RiderResults Check1, RiderResults Check2) {
        return Check1.FinishTime.compareTo(Check2.FinishTime);
    }
}

