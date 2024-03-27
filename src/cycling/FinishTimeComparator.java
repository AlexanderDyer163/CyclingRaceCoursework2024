package cycling;

import java.util.Comparator;

public class FinishTimeComparator implements Comparator<RiderResults> {

    @Override
    public int compare(RiderResults o1, RiderResults o2) {
        return o1.FinishTime.compareTo(o2.FinishTime);
    }
}
