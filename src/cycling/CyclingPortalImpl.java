package cycling;

import java.util.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class CyclingPortalImpl implements MiniCyclingPortal{

    private Dictionary<Integer,Race> AllRaces = new Hashtable<>();//gets race by id
    private Dictionary<Integer,Stage> AllStages = new Hashtable<>();//gets stage by id
    private Dictionary<Integer,Checkpoint> AllCheckpoints = new Hashtable<>();
    private Dictionary<Integer,Team> AllTeams = new Hashtable<>();
    @Override
    public int[] getRaceIds() {
        int[] Races = Collections.list(AllRaces.keys()).stream().mapToInt(Integer::intValue).toArray();//I hate working with lists, so im using ArrayLists :)
        return Races;
    }

    @Override
    public int createRace(String name, String description) throws IllegalNameException, InvalidNameException {
        Race newRace = new Race(name,description);
        if(newRace.name == "" || newRace.name.contains("\s") || newRace.name.length()>30){
            throw new InvalidNameException("-Race Name Cannot Contain Spaces, Be Greater Than 30 Letters Or Left Empty.-");
        }

        Collections.list(AllRaces.keys()).forEach(x -> {
            if (AllRaces.get(x).name == name){
                try {
                    throw new IllegalNameException("-Race Already Exists With Name-");
                } catch (IllegalNameException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        AllRaces.put(newRace.getRaceID(), newRace);
        return newRace.getRaceID();
    }

    @Override
    public String viewRaceDetails(int raceId) throws IDNotRecognisedException {
        return AllRaces.get(raceId).GetDetails();
    }

    @Override
    public void removeRaceById(int raceId) throws IDNotRecognisedException {
        AllRaces.get(raceId).DELETE(AllRaces,AllStages);
        AllRaces.remove(raceId);
    }

    @Override
    public int getNumberOfStages(int raceId) throws IDNotRecognisedException {
        return AllRaces.get(raceId).getStages().size();
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
        int[] stageIDList = new int[getNumberOfStages(raceId)];
        for (int i = 0; i < stageIDList.length;i++){
            stageIDList[i] = AllRaces.get(raceId).getStages().get(i).getStageID();
        }
        return stageIDList;
    }

    @Override
    public double getStageLength(int stageId) throws IDNotRecognisedException {
        return AllStages.get(stageId).length;
    }

    @Override
    public void removeStageById(int stageId) throws IDNotRecognisedException {
        AllStages.get(stageId).DELETE(AllRaces,AllStages);//Passes all the data of the platform
        AllRaces.get(AllStages.get(stageId)).getStages().remove(stageId);
    }

    @Override
    public int addCategorizedClimbToStage(int stageId, Double location, CheckpointType type, Double averageGradient, Double length) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
        Checkpoint climb = new Climb(stageId,location,type,length,averageGradient);
        AllStages.get(stageId).addCheckpoint(climb);
        AllCheckpoints.put(climb.getCheckpointID(),climb);
        return climb.getCheckpointID();
    }

    @Override
    public int addIntermediateSprintToStage(int stageId, double location) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
        Sprint sprint = new Sprint(stageId,location);
        AllStages.get(stageId).addCheckpoint(sprint);
        AllCheckpoints.put(sprint.getCheckpointID(),sprint);
        return sprint.getCheckpointID();
    }

    @Override
    public void removeCheckpoint(int checkpointId) throws IDNotRecognisedException, InvalidStageStateException {
        AllCheckpoints.get(checkpointId).DELETE(AllStages);
        AllCheckpoints.remove(checkpointId);
    }

    @Override
    public void concludeStagePreparation(int stageId) throws IDNotRecognisedException, InvalidStageStateException {
        AllStages.get(stageId).setState("waiting for results");
    }

    @Override
    public int[] getStageCheckpoints(int stageId) throws IDNotRecognisedException {
        ArrayList<Checkpoint> checks = AllStages.get(stageId).Checkpoints;
        CheckpointLocationComparator comparator = new CheckpointLocationComparator();
        Collections.sort(checks,comparator);
        int[] CheckList = new int[AllStages.get(stageId).Checkpoints.size()];
        for (int i = 0; i < checks.size();i++){
            CheckList[i] = checks.get(i).checkpointID;
        }
        return CheckList;
    }

    @Override
    public int createTeam(String name, String description) throws IllegalNameException, InvalidNameException {
        Team team = new Team(name,description);
        AllTeams.put(team.getTeamID(),team);
        return team.getTeamID();
    }

    @Override
    public void removeTeam(int teamId) throws IDNotRecognisedException {
        AllTeams.remove(teamId);
    }

    @Override
    public int[] getTeams() {
        int[] Teams = Collections.list(AllTeams.keys()).stream().mapToInt(Integer::intValue).toArray();//I hate working with lists, so im using ArrayLists :)
        return Teams;
    }

    @Override
    public int[] getTeamRiders(int teamId) throws IDNotRecognisedException {
        int[] ridersID = new int[AllTeams.get(teamId).getRiders().size()];
        for (int a = 0; a < ridersID.length; a++){
           ridersID[a] = AllTeams.get(teamId).getRiders().get(a).getId();
        }
        return ridersID;
    }

    @Override
    public int createRider(int teamID, String name, int yearOfBirth) throws IDNotRecognisedException, IllegalArgumentException {
        Rider newRider = new Rider(AllTeams.get(teamID),name,yearOfBirth);
        return newRider.getId();
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