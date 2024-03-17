import cycling.*;

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
    public static void main(String[] args) throws InvalidNameException, IllegalNameException, IDNotRecognisedException, InvalidLengthException, InvalidStageStateException, DuplicatedResultException, InvalidCheckpointTimesException {
        System.out.println("The system compiled and started the execution...");

        // TODO replace BadMiniCyclingPortalImpl by CyclingPortalImpl
        MiniCyclingPortal portal1 = new CyclingPortalImpl();
        int teamId = portal1.createTeam("First Team!","The first team created");
        int rider1 = portal1.createRider(teamId,"barry1",2004);
        int race1 = portal1.createRace("TheRace","first race");
        int stage1 = portal1.addStageToRace(race1,"first stage", "the first race",8.4,LocalDateTime.now(),StageType.FLAT);
        portal1.registerRiderResultsInStage(stage1,rider1,new LocalTime[]{LocalTime.parse("22:22:19"),LocalTime.parse("22:22:19"),LocalTime.parse("22:22:19"),LocalTime.parse("22:22:19"),LocalTime.parse("22:22:19")});
        int rider2 = portal1.createRider(teamId,"mike2",2003);
        int rider3 = portal1.createRider(teamId,"jimmy3",2001);
        int rider4 = portal1.createRider(teamId,"jimmy4",2001);
        int rider5 = portal1.createRider(teamId,"jimmy5",2001);
        int rider6 = portal1.createRider(teamId,"jimmy1",2001);
        int rider7 = portal1.createRider(teamId,"jimmy9",2001);
        int rider8 = portal1.createRider(teamId,"jimmy10",2001);
        portal1.registerRiderResultsInStage(stage1,rider2, new LocalTime[]{LocalTime.parse("22:22:19"),LocalTime.parse("22:22:19"),LocalTime.parse("22:22:19"),LocalTime.parse("22:22:19"),LocalTime.parse("22:22:20")});
        portal1.registerRiderResultsInStage(stage1,rider3,new LocalTime[]{LocalTime.parse("22:22:19"),LocalTime.parse("22:22:19"),LocalTime.parse("22:22:19"),LocalTime.parse("22:22:19"),LocalTime.parse("22:22:21")});
        portal1.registerRiderResultsInStage(stage1,rider7,new LocalTime[]{LocalTime.parse("22:22:19"),LocalTime.parse("22:22:19"),LocalTime.parse("22:22:19"),LocalTime.parse("22:22:19"),LocalTime.parse("22:22:21")});
        portal1.registerRiderResultsInStage(stage1,rider4,new LocalTime[]{LocalTime.parse("22:22:19"),LocalTime.parse("22:22:19"),LocalTime.parse("22:22:19"),LocalTime.parse("22:22:19"),LocalTime.parse("22:22:21.5")});
        portal1.registerRiderResultsInStage(stage1,rider5,new LocalTime[]{LocalTime.parse("22:22:19"),LocalTime.parse("22:22:19"),LocalTime.parse("22:22:19"),LocalTime.parse("22:22:19"),LocalTime.parse("22:22:23.5")});
        portal1.registerRiderResultsInStage(stage1,rider6,new LocalTime[]{LocalTime.parse("22:22:19"),LocalTime.parse("22:22:19"),LocalTime.parse("22:22:19"),LocalTime.parse("22:22:19"),LocalTime.parse("22:22:28.5")});
        portal1.registerRiderResultsInStage(stage1,rider8,new LocalTime[]{LocalTime.parse("22:22:19"),LocalTime.parse("22:22:19"),LocalTime.parse("22:22:19"),LocalTime.parse("22:22:19"),LocalTime.parse("22:22:29.5")});
        LocalTime[] Results = portal1.getRiderResultsInStage(stage1,rider8);


        System.out.println(portal1.getRiderAdjustedElapsedTimeInStage(rider8,stage1));
    }

}
