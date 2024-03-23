package cycling;

import java.util.EnumMap; /**
 * This enum is used to represent the stage types  on road races.
 * 
 * @author Diogo Pacheco
 * @version 1.0
 *
 */
public enum StageType {


	
	/**
	 * Used for mostly flat stages.
	 */
	FLAT,
	
	/**
	 * Used for hilly finish or stages with moderate amounts of mountains.
	 */
	MEDIUM_MOUNTAIN,
	
	/**
	 * Used for high mountain finish or stages with multiple categorised climbs.
	 */
	HIGH_MOUNTAIN,
	
	/**
	 * Used for time trials. 
	 */
	TT;
	public static Integer[] getPoints(StageType stage) {
		EnumMap<StageType, Integer[]> stagePoints = new EnumMap<>(StageType.class);
		stagePoints.put(FLAT, new Integer[]{50,30,20,18,16,14,12,10,8,7,6,5,4,3,2});
		stagePoints.put(MEDIUM_MOUNTAIN, new Integer[]{30,25,22,19,17,15,13,11,9,7,6,5,4,3,2});
		stagePoints.put(HIGH_MOUNTAIN, new Integer[]{20,17,15,13,11,10,9,8,7,6,5,4,3,2,1});
		stagePoints.put(TT, stagePoints.get(StageType.HIGH_MOUNTAIN));//they have the same points
		return stagePoints.get(stage);
	}
}

