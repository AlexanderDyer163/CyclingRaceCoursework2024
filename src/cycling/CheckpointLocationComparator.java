package cycling;

import java.util.Comparator;

public class CheckpointLocationComparator implements Comparator<Checkpoint>{
    @Override
    public int compare(Checkpoint Check1, Checkpoint Check2) {
        return Double.compare(Check1.location,Check2.location);
    }
}
