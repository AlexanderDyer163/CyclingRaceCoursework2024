package cycling;

import java.io.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class CyclingPortalImpl implements MiniCyclingPortal{

    private Dictionary<Integer,Race> AllRaces = new Hashtable<>();//gets race by id
    private Dictionary<Integer,Stage> AllStages = new Hashtable<>();//gets stage by id
    private Dictionary<Integer,Checkpoint> AllCheckpoints = new Hashtable<>();
    private Dictionary<Integer,Team> AllTeams = new Hashtable<>();

    private Dictionary<Integer, Rider> AllRiders = new Hashtable<>();
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
        Collections.sort(checks,comparator);//orders the checkpoints based on their location
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
           ridersID[a] = AllTeams.get(teamId).getRiders().get(a);
        }
        return ridersID;
    }

    @Override
    public int createRider(int teamID, String name, int yearOfBirth) throws IDNotRecognisedException, IllegalArgumentException {
        Rider newRider = new Rider(teamID,name,yearOfBirth);
        AllRiders.put(newRider.getId(),newRider);
        return newRider.getId();
    }

    @Override
    public void removeRider(int riderId) throws IDNotRecognisedException {
        AllRiders.remove(riderId);
        AllTeams.get(AllRiders.get(riderId).getCurrentTeamID()).removeRider(riderId);
    }

    @Override
    public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpointTimes) throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointTimesException, InvalidStageStateException {
        AllStages.get(stageId).registerResults(riderId,new RiderResults(stageId,riderId,checkpointTimes));//First entry is start time, last entry is finish time
    }

    @Override
    public LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
        LocalTime[] ToReturn = new LocalTime[AllStages.get(stageId).getRiderResults(riderId).checkpointTimes.length + 1];
        for (int i = 0; i < ToReturn.length; i++) {
            ToReturn[i] = AllStages.get(stageId).getRiderResults(riderId).checkpointTimes[i];
        }
        ToReturn[ToReturn.length] = AllStages.get(stageId).getRiderResults(riderId).ElapsedTime;
        return ToReturn;//Should work, not tested
    }

    @Override
    public LocalTime getRiderAdjustedElapsedTimeInStage(int riderId, int stageId) throws IDNotRecognisedException {//CAN BE ADJUSTED TO USE THE ABOVE METHOD AND ITS ELAPSED TIME
        ArrayList<RiderResults> StageResults = Collections.list(AllStages.get(stageId).AllRidersResults.elements());//Retrieves the elements of the dictionary
        ElapsedTimeComparator a = new ElapsedTimeComparator();//Creates comparator
        Collections.sort(StageResults, a);//Sorts the riderresults for the stage by ElapsedTime
        boolean Solved = false;
        long x = 0;
        long c = 0;
        LocalTime CurrentSmallestTime = StageResults.get(0).ElapsedTime;//Holds the smallest time to compare for consecutives,
        LocalTime PrevTime = StageResults.get(0).ElapsedTime;//Holds the previous time for comparison
        LocalTime RiderTime = LocalTime.parse("00:00");//Elapsed time cannot be 0 so this is a good start point for the first comparison
        long diff = 0;
        while(Solved == false){
            LocalTime TempTime = StageResults.get((int) x).ElapsedTime;
            if(x == 0){
                diff = (CurrentSmallestTime.until(TempTime,ChronoUnit.NANOS));
            }else{
                diff = (PrevTime.until(TempTime,ChronoUnit.NANOS));
            }
            long adjustedTime = 1000000000;
            if (!((diff <= adjustedTime) && diff >= 0)){
                CurrentSmallestTime = TempTime;
            }
            if(StageResults.get((int) x).RiderID == riderId){
                RiderTime = CurrentSmallestTime;
                Solved = true;
            }
            x++;
            PrevTime = TempTime;
        }
        return RiderTime;//needs to be adjusted for elapsed time not finish time (Should be done now, not tested)


    }

    @Override
    public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
        AllRiders.get(riderId).deleteResults();
    }

    @Override
    public int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException {//should be checked next, went to get lunch :)
        ElapsedTimeComparator a = new ElapsedTimeComparator();
        ArrayList<RiderResults> OrderedResults = Collections.list(AllStages.get(stageId).AllRidersResults.elements());
        int[] end = new int[OrderedResults.size()];
        Collections.sort(OrderedResults,a);
        for (int i = 0; i < OrderedResults.size();i++){
            end[i] = OrderedResults.get(i).RiderID;//needs to be sorted by elapsed time rather than finish time, (Finish time - start time)
        }
        return end;
    }

    @Override
    public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException {
        List<RiderResults> stageresults = Collections.list(AllStages.get(stageId).AllRidersResults.elements());
        List<LocalTime> adjuststageresults = new ArrayList<>();
        for (int i = 0; i < stageresults.size();i++){
            adjuststageresults.add(getRiderAdjustedElapsedTimeInStage(stageresults.get(i).RiderID,stageId));
        }
        Collections.sort(adjuststageresults);
        return adjuststageresults.toArray(new LocalTime[adjuststageresults.size()]);
    }

    @Override
    public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException {
        Stage TheStage = AllStages.get(stageId);
        int[] RidersInRank = getRidersRankInStage(stageId);//NEED TO USE THIS TO ORDER THE RIDERS FOR OUTPUT
        ArrayList<RiderResults> stageResults = Collections.list(AllStages.get(stageId).AllRidersResults.elements());
        ArrayList<RiderResults> CurrentCheckpointTimes = new ArrayList<>();
        FinishTimeComparator a = new FinishTimeComparator();
        Collections.sort(stageResults,a);
        Dictionary<Integer,ArrayList<Integer>> Standings = new Hashtable<>();
        //By making standings a dictionary, we can keep track of the riderID
        for (int o = 0; o < RidersInRank.length;o++){//NOT FINISHED 21/3
            Standings.put(RidersInRank[o],new ArrayList<Integer>());//initialises all the standings,
        }
        int[] end = new int[stageResults.size()];
        int placeCount = 0;
        Integer[] points = StageType.getPoints(AllStages.get(stageId).type);
        LocalTime TiePlace = LocalTime.parse("00:00:00");
        //First gather the checkpoints of a stage and seperate into an arraylist of checkpoint array lists,with the riders in order
        ArrayList<Integer> Places = new ArrayList<>();
        for (int j = 0; j < TheStage.Checkpoints.size();j++) {//loops through the checkpoints

            for (int i = 0; i < stageResults.size(); i++) {
                CurrentCheckpointTimes.add(new RiderResults(TheStage.getStageID(),stageResults.get(i).RiderID,new LocalTime[]{stageResults.get(i).checkpointTimes[j]}));
            }//adds a rider with one time, which is the currently observed time and the same id, therefore we can use the comparator to sort it
            Collections.sort(CurrentCheckpointTimes,a);//Sorts the current checkpoints times


            for (int i = 0; i < CurrentCheckpointTimes.size();i++){

                if(CurrentCheckpointTimes.get(i).checkpointTimes[0].equals(TiePlace)){
                    Standings.get(CurrentCheckpointTimes.get(i).RiderID).add(placeCount);//Gives the riderID its rankings
                    TiePlace = CurrentCheckpointTimes.get(i).checkpointTimes[0];//used for riders that finish at the same time, they are given the same positons and points
                }else{
                    placeCount++;//if there is not a tie it increases the position place
                    Standings.get(CurrentCheckpointTimes.get(i).RiderID).add(placeCount);
                    TiePlace = CurrentCheckpointTimes.get(i).checkpointTimes[0];
                }
            }
            // need to sort currentcheckpointtimes with riderrankinstage first
            CurrentCheckpointTimes.clear();
            placeCount = 0;
            TiePlace = LocalTime.parse("00:00:00");
        }
        //this for loop should loop through the standings and convert it to points, the main issue is those with multiple standings for the checkpoints
        for (int b = 0; b < Standings.size();b++){//loops through all the standings
            for (int s = 0; s < RidersInRank.length;s++){//loops through the Riders in their ranks of elapsed time
                if (Collections.list(Standings.keys()).get(s) == RidersInRank[b]){//gets the riders by their standings
                    for (int p = 0; p < Standings.get(RidersInRank[b]).size();p++) {//loops through the Places for each checkpoint for tht rider
                        if(p != Standings.get(RidersInRank[b]).size()-1) {
                            end[s] = end[s] + StageType.getPoints(StageType.HIGH_MOUNTAIN)[Standings.get(RidersInRank[b]).get(p)-1];//combines the points of multiple sprints NEEDS TO BE CHANGED AS YOU GET POINTS FOR FINISHING SPRINTS AND THE WHOLE RACE//I DO HIGH MOUNTAIN STAGE TYPE BECAUSE THE SCORES ARE THE SAME AS THE SPRINT CHECKPOINT
                        }else{
                            end[s] = end[s] + StageType.getPoints(TheStage.type)[Standings.get(RidersInRank[b]).get(p)-1];//For the stage finish points
                        }//also needs to be adjusted for if there are more riders than 20
                    }
                }
            }
        }
        return end;//might need to order with rider id not sure yet I BELIEVE IT WORKS :) NEEDS TO BE ADJUSTED TO ONLY INCLUDE SPRINTS AND FINIHS
    }

    @Override
    public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException {
        Stage TheStage = AllStages.get(stageId);
        int[] RidersInRank = getRidersRankInStage(stageId);
        ArrayList<RiderResults> AllRidersResults = Collections.list(TheStage.AllRidersResults.elements());
        FinishTimeComparator s = new FinishTimeComparator();
        Collections.sort(AllRidersResults,s);
        Dictionary<Integer, ArrayList<Integer>> RidersStandings = new Hashtable<>();
        LocalTime TiePlace = LocalTime.parse("00:00");
        int PlaceCount = 0;
        int[] end = new int[AllRidersResults.size()];
        ArrayList<RiderResults> CurrentCheckpointTimes = new ArrayList<>();

        for (int o = 0; o < RidersInRank.length;o++){
            RidersStandings.put(RidersInRank[o], new ArrayList<>());
        }

        for(int j = 0; j < getStageCheckpoints(stageId).length;j++){//ignores finish time

            if (TheStage.Checkpoints.get(j).type != CheckpointType.SPRINT) {//ignores sprints
                for (int i = 0; i < AllRidersResults.size(); i++) {
                    CurrentCheckpointTimes.add(new RiderResults(TheStage.getStageID(), AllRidersResults.get(i).RiderID, new LocalTime[]{AllRidersResults.get(i).checkpointTimes[j]}));
                }
                CurrentTimeComparator p = new CurrentTimeComparator();
                Collections.sort(CurrentCheckpointTimes,p);

                for (int a = 0; a < CurrentCheckpointTimes.size(); a++) {
                    if (CurrentCheckpointTimes.get(a).checkpointTimes[0].equals(TiePlace)) {
                        RidersStandings.get(CurrentCheckpointTimes.get(a).RiderID).add(PlaceCount);
                        TiePlace = CurrentCheckpointTimes.get(a).checkpointTimes[0];
                    } else {
                        PlaceCount++;
                        RidersStandings.get(CurrentCheckpointTimes.get(a).RiderID).add(PlaceCount);
                        TiePlace = CurrentCheckpointTimes.get(a).checkpointTimes[0];
                    }
                }
            }

            CurrentCheckpointTimes.clear();
            PlaceCount = 0;
            TiePlace = LocalTime.parse("00:00:00");
        }

        for (int b = 0; b < RidersStandings.size();b++){//loops through all the standings
            for (int u = 0; u < RidersInRank.length;u++){//loops through the Riders
                if (Collections.list(RidersStandings.keys()).get(u) == RidersInRank[b]){//gets the riders by their standings
                    for (int p = 0; p < RidersStandings.get(RidersInRank[b]).size();p++) {//loops through the Places for each checkpoint for tht rider
                        if(p != RidersStandings.get(RidersInRank[b]).size()-1) {
                            end[u] = end[u] + CheckpointType.getPoints(TheStage.Checkpoints.get(p).type)[RidersStandings.get(RidersInRank[b]).get(p)-1];//combines the points of multiple sprints NEEDS TO BE CHANGED AS YOU GET POINTS FOR FINISHING SPRINTS AND THE WHOLE RACE//I DO HIGH MOUNTAIN STAGE TYPE BECAUSE THE SCORES ARE THE SAME AS THE SPRINT CHECKPOINT
                        }
                    }
                }
            }
        }
        return end;
    }

    @Override
    public void eraseCyclingPortal() {
        AllRaces = null;
        AllCheckpoints = null;
        AllStages = null;
        AllTeams = null;
        AllRiders=null;
    }

    @Override
    public void saveCyclingPortal(String filename) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(filename);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        CyclingPortalImpl a = this;
        objectOutputStream.writeObject(a);

        objectOutputStream.flush();
        objectOutputStream.close();
    }

    @Override
    public void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(filename);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        CyclingPortalImpl a = (CyclingPortalImpl) objectInputStream.readObject();
        objectInputStream.close();
        AllRiders = a.AllRiders;
        AllTeams = a.AllTeams;
        AllStages = a.AllStages;
        AllCheckpoints = a.AllCheckpoints;
        AllRaces = a.AllRaces;
    }
}