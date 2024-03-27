import cycling.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * A short program to illustrate an app testing some minimal functionality of a
 * concrete implementation of the CyclingPortal interface -- note you
 * will want to increase these checks, and run it on your CyclingPortalImpl class
 * (not the BadCyclingPortal class).
 *
 *
 * @author Diogo Pacheco
 * @version 2.0
 */
public class MyTestApp {

    /**
     * Test method.
     *
     * @param args not used
     */
    public static void main(String[] args) throws InvalidNameException, IllegalNameException, IDNotRecognisedException, InvalidLengthException, InvalidStageStateException, DuplicatedResultException, InvalidCheckpointTimesException, InvalidLocationException, InvalidStageTypeException, IOException, ClassNotFoundException {
        System.out.println("The system compiled and started the execution...");

        // TODO replace BadMiniCyclingPortalImpl by CyclingPortalImpl
        MiniCyclingPortal portal1 = new CyclingPortalImpl();
        int teamId = portal1.createTeam("FirstTeam!","The first team created");
        int rider1 = portal1.createRider(teamId,"barry1",2004);
        int rider2 = portal1.createRider(teamId,"barry2",2004);
        int rider3 = portal1.createRider(teamId,"barry3",2004);
        int race1 = portal1.createRace("TheRace","first race");
        int stage1 = portal1.addStageToRace(race1,"firststage", "the first race",7,LocalDateTime.now(),StageType.FLAT);
        int climb1 = portal1.addCategorizedClimbToStage(stage1,3.2,CheckpointType.C2,6.5,1.2);
        int climb2 = portal1.addCategorizedClimbToStage(stage1,3.9,CheckpointType.C2,6.5,1.2);
        int climb3 = portal1.addIntermediateSprintToStage(stage1,5.9);
        int climb4 = portal1.addIntermediateSprintToStage(stage1,6.8);
        portal1.concludeStagePreparation(stage1);
        portal1.registerRiderResultsInStage(stage1,rider1,new LocalTime[]{LocalTime.parse("00:00:00"),LocalTime.parse("10:00:00"),LocalTime.parse("20:00:10"),LocalTime.parse("20:01:00"),LocalTime.parse("23:00:00"),LocalTime.parse("23:30:00")});
        portal1.registerRiderResultsInStage(stage1,rider3,new LocalTime[]{LocalTime.parse("00:00:00"),LocalTime.parse("10:00:00"),LocalTime.parse("20:00:00"),LocalTime.parse("20:01:00"),LocalTime.parse("23:00:02"),LocalTime.parse("23:30:02")});
        portal1.registerRiderResultsInStage(stage1,rider2,new LocalTime[]{LocalTime.parse("00:00:00"),LocalTime.parse("10:00:00"),LocalTime.parse("20:00:00"),LocalTime.parse("20:01:00"),LocalTime.parse("23:00:00"),LocalTime.parse("23:30:00")});
        System.out.println(portal1.getRidersMountainPointsInStage(stage1)[0]);
    }

}
