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
            portal1.createRace("race1","the first race");
            portal1.addStageToRace(1,"Stage1","afa",3.1, LocalDateTime.now(),StageType.FLAT);
            portal1.addStageToRace(1,"Stage1","afa",3.1, LocalDateTime.now(),StageType.FLAT);
            portal1.createRace("race2","second race");
            portal1.createRace("race3","the first race");
            portal1.createRace("race4","second race");
            System.out.println(portal1.viewRaceDetails(1));
            for (int i = 0; i < portal1.getRaceIds().length;i++){
                System.out.println(portal1.getRaceIds()[i]);
            }
        } catch (IllegalNameException e) {
            throw new RuntimeException(e);
        } catch (InvalidNameException | IDNotRecognisedException | InvalidLengthException e) {
            throw new RuntimeException(e);
        }

    }

}
