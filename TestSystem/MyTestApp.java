import cycling.*;

import java.time.LocalDateTime;

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
    public static void main(String[] args) {
        System.out.println("The system compiled and started the execution...");

        // TODO replace BadMiniCyclingPortalImpl by CyclingPortalImpl
        MiniCyclingPortal portal1 = new CyclingPortalImpl();
        try {
            int id = portal1.createRace("hmmm","first race");
            try {
                int stageID = portal1.addStageToRace(id,"FirstStage","first",8.0,LocalDateTime.now(),StageType.FLAT);
                portal1.addStageToRace(id,"SecondStage","second",9.0,LocalDateTime.now(),StageType.TT);
                int otherstage = portal1.addStageToRace(id,"ThirdStage","third",10.0,LocalDateTime.now(),StageType.TT);
                portal1.addIntermediateSprintToStage(otherstage,2.0);
                portal1.addIntermediateSprintToStage(stageID,2.0);
                int[] a = portal1.getStageCheckpoints(stageID);
                System.out.println(portal1.viewRaceDetails(id));
            } catch (IDNotRecognisedException e) {
                throw new RuntimeException(e);
            } catch (InvalidLengthException | InvalidLocationException | InvalidStageStateException |
                     InvalidStageTypeException e) {
                throw new RuntimeException(e);
            }
        } catch (IllegalNameException e) {
            throw new RuntimeException(e);
        } catch (InvalidNameException e) {
            throw new RuntimeException(e);
        }

    }

}
