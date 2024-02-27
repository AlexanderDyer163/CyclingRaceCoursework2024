package cycling;

import java.util.Dictionary;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

public class CyclingPortalImpl implements MiniCyclingPortal{

    public ArrayList<Integer> RacesArrList = new ArrayList<>();
    private Dictionary<Integer,Race> AllRaces = new Hashtable<>();//gets race by id
    private Dictionary<Integer,Stage> AllStages = new Hashtable<>();//gets stage by id
    @Override
    public int[] getRaceIds() {
        int[] Races = RacesArrList.stream().mapToInt(Integer::intValue).toArray();//I hate working with lists, so im using ArrayLists :)
        return Races;
    }

    @Override
    public int createRace(String name, String description) throws IllegalNameException, InvalidNameException {
        Race newRace = new Race(name,description);
        RacesArrList.add(newRace.getRaceID());
        AllRaces.put(newRace.getRaceID(), newRace);
        return newRace.getRaceID();
    }

    @Override
    public String viewRaceDetails(int raceId) throws IDNotRecognisedException {
        return AllRaces.get(raceId).GetDetails();
    }

    @Override
    public void removeRaceById(int raceId) throws IDNotRecognisedException {
        RacesArrList.remove(raceId);
        AllRaces.get(raceId).DELETE();
        AllRaces.remove(raceId);
    }

    @Override
    public int getNumberOfStages(int raceId) throws IDNotRecognisedException {
        return AllRaces.get(raceId).Stages.size();
    }

    @Override
    public int addStageToRace(int raceId, String stageName, String description, double length, LocalDateTime startTime, StageType type) throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException {
        Stage b;
        int a = AllRaces.get(raceId).AddStage(b = new Stage(stageName,type,description,length,startTime,raceId));
        AllStages.put(a,b);
        return a;
    }

    @Override
    public int[] getRaceStages(int raceId) throws IDNotRecognisedException {
        return new int[0];
    }

    @Override
    public double getStageLength(int stageId) throws IDNotRecognisedException {
        return AllStages.get(stageId).length;
    }

    @Override
    public void removeStageById(int stageId) throws IDNotRecognisedException {
        AllStages.get(stageId).DELETE();
        AllRaces.get(AllStages.get(stageId).ParentID);
    }

    @Override
    public int addCategorizedClimbToStage(int stageId, Double location, CheckpointType type, Double averageGradient, Double length) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
        Checkpoint climb = new Climb(location,type,length,averageGradient);
        AllStages.get(stageId).addCheckpoint(climb);
        return climb.getCheckpointID();
    }

    @Override
    public int addIntermediateSprintToStage(int stageId, double location) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
        return 0;
    }

    @Override
    public void removeCheckpoint(int checkpointId) throws IDNotRecognisedException, InvalidStageStateException {

    }

    @Override
    public void concludeStagePreparation(int stageId) throws IDNotRecognisedException, InvalidStageStateException {

    }

    @Override
    public int[] getStageCheckpoints(int stageId) throws IDNotRecognisedException {
        return new int[0];
    }

    @Override
    public int createTeam(String name, String description) throws IllegalNameException, InvalidNameException {
        return 0;
    }

    @Override
    public void removeTeam(int teamId) throws IDNotRecognisedException {

    }

    @Override
    public int[] getTeams() {
        return new int[0];
    }

    @Override
    public int[] getTeamRiders(int teamId) throws IDNotRecognisedException {
        return new int[0];
    }

    @Override
    public int createRider(int teamID, String name, int yearOfBirth) throws IDNotRecognisedException, IllegalArgumentException {
        return 0;
    }

    @Override
    public void removeRider(int riderId) throws IDNotRecognisedException {

    }

    @Override
    public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpointTimes) throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointTimesException, InvalidStageStateException {

    }

    @Override
    public LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
        return new LocalTime[0];
    }

    @Override
    public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) throws IDNotRecognisedException {
        return null;
    }

    @Override
    public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {

    }

    @Override
    public int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException {
        return new int[0];
    }

    @Override
    public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException {
        return new LocalTime[0];
    }

    @Override
    public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException {
        return new int[0];
    }

    @Override
    public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException {
        return new int[0];
    }

    @Override
    public void eraseCyclingPortal() {

    }

    @Override
    public void saveCyclingPortal(String filename) throws IOException {

    }

    @Override
    public void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException {

    }
}