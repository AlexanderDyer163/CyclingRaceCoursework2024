package cycling;

import java.io.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class CyclingPortalImpl implements MiniCyclingPortal{

    private Dictionary<Integer,Race> AllRaces = new Hashtable<>();//gets race by id
    private Dictionary<Integer,Stage> AllStages = new Hashtable<>();//gets stage by id
    private Dictionary<Integer,Checkpoint> AllCheckpoints = new Hashtable<>();//gets checkpoint by id
    private Dictionary<Integer,Team> AllTeams = new Hashtable<>();//gets team by id
    private Dictionary<Integer, Rider> AllRiders = new Hashtable<>();//gets rider by id
    @Override
    public int[] getRaceIds() {
        int[] Races = Collections.list(AllRaces.keys()).stream().mapToInt(Integer::intValue).toArray();
        return Races;
    }

    @Override
    public int createRace(String name, String description) throws IllegalNameException, InvalidNameException {
        Race newRace = new Race(name,description);
        if(newRace.name == "" || newRace.name.contains("\s") || newRace.name.length()>30){
            throw new InvalidNameException("-Race Name Cannot Contain Spaces, Be Greater Than 30 Letters Or Left Empty.-");
        }

        Collections.list(AllRaces.keys()).forEach(x -> {//Checks All Existing Names
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
        if(!Collections.list(AllRaces.keys()).contains(raceId)){
            throw new IDNotRecognisedException("Race ID not recognized");
        }
        return AllRaces.get(raceId).GetDetails();
    }

    @Override
    public void removeRaceById(int raceId) throws IDNotRecognisedException {
        if(!Collections.list(AllRaces.keys()).contains(raceId)){
            throw new IDNotRecognisedException("Race ID not recognized");
        }
        AllRaces.get(raceId).DELETE(AllRaces,AllStages);
        AllRaces.remove(raceId);
    }

    @Override
    public int getNumberOfStages(int raceId) throws IDNotRecognisedException {
        if(!Collections.list(AllRaces.keys()).contains(raceId)){
            throw new IDNotRecognisedException("Race ID not recognized");
        }
        return AllRaces.get(raceId).getStages().size();
    }

    @Override
    public int addStageToRace(int raceId, String stageName, String description, double length, LocalDateTime startTime, StageType type) throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException {
        if(!Collections.list(AllRaces.keys()).contains(raceId)){
            throw new IDNotRecognisedException("Race ID not recognized");
        }
        if(length == 0){
            throw new InvalidLengthException("Length of Stage cannot be null");
        }
        if(stageName == "" || stageName.contains("\s") || stageName.length()>30){
            throw new InvalidNameException("-Stage Name Cannot Contain Spaces, Be Greater Than 30 Letters Or Left Empty.-");
        }
        for (int i = 0;i < AllStages.size(); i++){
            if(Collections.list(AllStages.elements()).get(i).stageName == stageName  ){
                throw new IllegalNameException("Stage Name Already In Use");
            }
        }
        Stage b;
        int a = AllRaces.get(raceId).AddStage(b = new Stage(stageName,type,description,length,startTime,raceId));
        AllStages.put(a,b);
        return a;
    }

    @Override
    public int[] getRaceStages(int raceId) throws IDNotRecognisedException {
        if(!Collections.list(AllRaces.keys()).contains(raceId)){
            throw new IDNotRecognisedException("Race ID not recognized");
        }
        int[] stageIDList = new int[getNumberOfStages(raceId)];
        for (int i = 0; i < stageIDList.length;i++){
            stageIDList[i] = AllRaces.get(raceId).getStages().get(i).getStageID();
        }
        return stageIDList;
    }

    @Override
    public double getStageLength(int stageId) throws IDNotRecognisedException {
        if(!Collections.list(AllStages.keys()).contains(stageId)){
            throw new IDNotRecognisedException("Stage ID not recognized");
        }
        return AllStages.get(stageId).length;
    }

    @Override
    public void removeStageById(int stageId) throws IDNotRecognisedException {
        if(!Collections.list(AllStages.keys()).contains(stageId)){
            throw new IDNotRecognisedException("Stage ID not recognized");
        }
        AllStages.get(stageId).DELETE(AllRaces,AllStages);//Passes all the required data
    }

    @Override
    public int addCategorizedClimbToStage(int stageId, Double location, CheckpointType type, Double averageGradient, Double length) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
        if(!Collections.list(AllStages.keys()).contains(stageId)){
            throw new IDNotRecognisedException("Stage ID not recognized");
        }
        if(location < 0 || location >= AllStages.get(stageId).length){
            throw new InvalidLocationException("Location of stage out of bounds");
        }
        if(AllStages.get(stageId).type == StageType.TT){
            throw new InvalidStageTypeException("Cannot Add To Time Trial");
        }
        if(AllStages.get(stageId).State.equals("waiting for results") ){
            throw new InvalidStageStateException();
        }

        Checkpoint climb = new Climb(stageId,location,type,length,averageGradient);//Climb is a subclass of checkpoint
        AllStages.get(stageId).addCheckpoint(climb);
        AllCheckpoints.put(climb.getCheckpointID(),climb);
        return climb.getCheckpointID();
    }

    @Override
    public int addIntermediateSprintToStage(int stageId, double location) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
        if(!Collections.list(AllStages.keys()).contains(stageId)){
            throw new IDNotRecognisedException("Stage ID not recognized");
        }
        if(location < 0 || location >= AllStages.get(stageId).length){
            throw new InvalidLocationException("Location of stage out of bounds");
        }
        if(AllStages.get(stageId).type == StageType.TT){
            throw new InvalidStageTypeException("Cannot Add To Time Trial");
        }
        if(AllStages.get(stageId).State.equals("waiting for results") ){
            throw new InvalidStageStateException();
        }
        Sprint sprint = new Sprint(stageId,location);//Sprint is a subclass of checkpoint
        AllStages.get(stageId).addCheckpoint(sprint);
        AllCheckpoints.put(sprint.getCheckpointID(),sprint);
        return sprint.getCheckpointID();
    }

    @Override
    public void removeCheckpoint(int checkpointId) throws IDNotRecognisedException, InvalidStageStateException {
        if(!Collections.list(AllCheckpoints.keys()).contains(checkpointId)){
            throw new IDNotRecognisedException("Checkpoint ID not recognized");
        }
        if(AllStages.get(AllCheckpoints.get(checkpointId).getParentID()).State.equals("waiting for results") ){
            throw new InvalidStageStateException();
        }
        AllCheckpoints.get(checkpointId).DELETE(AllStages);
        AllCheckpoints.remove(checkpointId);
    }

    @Override
    public void concludeStagePreparation(int stageId) throws IDNotRecognisedException, InvalidStageStateException {
        if (!Collections.list(AllStages.keys()).contains(stageId)){
            throw new IDNotRecognisedException("Stage ID not recognized");
        }
        if (!AllStages.get(stageId).State.equals("Preparing")){
            throw new InvalidStageStateException("State is not preparing");
        }
        AllStages.get(stageId).setState("waiting for results");
    }

    @Override
    public int[] getStageCheckpoints(int stageId) throws IDNotRecognisedException {
        if (!Collections.list(AllStages.keys()).contains(stageId)){
            throw new IDNotRecognisedException();
        }
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
        Collections.list(AllTeams.elements()).forEach(CurrentName ->{
            if(CurrentName.equals(name)){
                try {
                    throw new IllegalNameException("Team already exists with that name");
                } catch (IllegalNameException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        if(name == "" || name.contains("\s") || name.length()>30){
            throw new InvalidNameException("-Team Name Cannot Contain Spaces, Be Greater Than 30 Letters Or Left Empty.-");
        }
        Team team = new Team(name,description);
        AllTeams.put(team.getTeamID(),team);
        return team.getTeamID();
    }

    @Override
    public void removeTeam(int teamId) throws IDNotRecognisedException {
        if (!Collections.list(AllTeams.keys()).contains(teamId)){
            throw new IDNotRecognisedException("Team ID Not Recognized");
        }
        AllTeams.remove(teamId);
    }

    @Override
    public int[] getTeams() {
        int[] Teams = Collections.list(AllTeams.keys()).stream().mapToInt(Integer::intValue).toArray();
        return Teams;
    }

    @Override
    public int[] getTeamRiders(int teamId) throws IDNotRecognisedException {
        if (!Collections.list(AllTeams.keys()).contains(teamId)){
            throw new IDNotRecognisedException("Team ID not recognized");
        }
        int[] ridersID = new int[AllTeams.get(teamId).getRiders().size()];
        for (int a = 0; a < ridersID.length; a++){
           ridersID[a] = AllTeams.get(teamId).getRiders().get(a);
        }
        return ridersID;
    }

    @Override
    public int createRider(int teamID, String name, int yearOfBirth) throws IDNotRecognisedException, IllegalArgumentException {
        if (!Collections.list(AllTeams.keys()).contains(teamID)){
            throw new IDNotRecognisedException("Team ID not recognized");
        }
        if(name.equals("") || name == null || yearOfBirth<1900){
            throw new IllegalArgumentException("Name Is Empty Or Birth Year less than 1900");
        }
        Rider newRider = new Rider(teamID,name,yearOfBirth);
        AllRiders.put(newRider.getId(),newRider);
        return newRider.getId();
    }

    @Override
    public void removeRider(int riderId) throws IDNotRecognisedException {
        if (!Collections.list(AllRiders.keys()).contains(riderId)){
            throw new IDNotRecognisedException("Rider ID not recognized");
        }

        AllTeams.get(AllRiders.get(riderId).getCurrentTeamID()).removeRider(riderId);
        AllRiders.remove(riderId);
    }

    @Override
    public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpointTimes) throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointTimesException, InvalidStageStateException {
        if (!Collections.list(AllStages.keys()).contains(stageId)){
            throw new IDNotRecognisedException("Stage ID not recognized");
        }
        if (!AllStages.get(stageId).State.equals("waiting for results")){
            throw new InvalidStageStateException("Stage is not waiting for results");
        }
        Collections.list(AllStages.get(stageId).AllRidersResults.elements()).forEach(x ->{
            if(x.RiderID == riderId){
                try {
                    throw new DuplicatedResultException("Rider cannot have two results in the same stage");
                } catch (DuplicatedResultException e) {
                    throw new RuntimeException(e);
                }
            }
        } );
        if (checkpointTimes.length != AllStages.get(stageId).Checkpoints.size()+2){//+2 to include finish and start
            throw new InvalidCheckpointTimesException();
        }
        Collections.sort(Arrays.asList(checkpointTimes));
        AllStages.get(stageId).registerResults(riderId,new RiderResults(stageId,riderId,checkpointTimes));//First entry is start time, last entry is finish time
    }

    @Override
    public LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {//Not sure if you want to also return the start/finish time, I chose not to
        if (!Collections.list(AllStages.keys()).contains(stageId)){
            throw new IDNotRecognisedException("Stage ID not recognized");
        }
        if (!Collections.list(AllRiders.keys()).contains(riderId)){
            throw new IDNotRecognisedException("Rider ID not recognized");
        }
        LocalTime[] ToReturn = new LocalTime[AllStages.get(stageId).getRiderResults(riderId).checkpointTimes.length + 1];
        for (int i = 1; i < ToReturn.length-2; i++) {//Starts at 1, to skip the start time, length is -2 to skip the finish time
            ToReturn[i] = AllStages.get(stageId).getRiderResults(riderId).checkpointTimes[i];
        }
        ToReturn[ToReturn.length-2] = AllStages.get(stageId).getRiderResults(riderId).ElapsedTime;
        return ToReturn;
    }

    @Override
    public LocalTime getRiderAdjustedElapsedTimeInStage(int riderId, int stageId) throws IDNotRecognisedException {
        if (!Collections.list(AllStages.keys()).contains(stageId)){
            throw new IDNotRecognisedException("Stage ID not recognized");
        }
        if (!Collections.list(AllRiders.keys()).contains(riderId)){
            throw new IDNotRecognisedException("Rider ID not recognized");
        }
        ArrayList<RiderResults> StageResults = Collections.list(AllStages.get(stageId).AllRidersResults.elements());//Retrieves the elements of the dictionary
        ElapsedTimeComparator a = new ElapsedTimeComparator();//Creates comparator
        Collections.sort(StageResults, a);//Sorts the riderresults for the stage by ElapsedTime
        boolean Solved = false;
        long x = 0;
        LocalTime CurrentSmallestTime = StageResults.get(0).ElapsedTime;
        LocalTime PrevTime = StageResults.get(0).ElapsedTime;
        LocalTime RiderTime = LocalTime.parse("00:00");
        long diff = 0;
        while(Solved == false){
            LocalTime TempTime = StageResults.get((int) x).ElapsedTime;
            if(x == 0){
                diff = (CurrentSmallestTime.until(TempTime,ChronoUnit.NANOS));//Calculates difference in nanoseconds
            }else{
                diff = (PrevTime.until(TempTime,ChronoUnit.NANOS));
            }

            if (!((diff <= 1000000000) && diff >= 0)){
                CurrentSmallestTime = TempTime;
            }
            if(StageResults.get((int) x).RiderID == riderId){
                RiderTime = CurrentSmallestTime;
                Solved = true;
            }
            x++;
            PrevTime = TempTime;
        }
        return RiderTime;


    }

    @Override
    public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
        if (!Collections.list(AllStages.keys()).contains(stageId)){
            throw new IDNotRecognisedException("Stage ID not recognized");
        }
        if (!Collections.list(AllRiders.keys()).contains(riderId)){
            throw new IDNotRecognisedException("Rider ID not recognized");
        }
        AllRiders.get(riderId).deleteResults();
        AllStages.get(stageId).AllRidersResults.remove(riderId);
    }

    @Override
    public int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException {
        if (!Collections.list(AllStages.keys()).contains(stageId)){
            throw new IDNotRecognisedException("Stage ID not recognized");
        }
        ElapsedTimeComparator a = new ElapsedTimeComparator();
        ArrayList<RiderResults> OrderedResults = Collections.list(AllStages.get(stageId).AllRidersResults.elements());
        int[] end = new int[OrderedResults.size()];
        Collections.sort(OrderedResults,a);
        for (int i = 0; i < OrderedResults.size();i++){
            end[i] = OrderedResults.get(i).RiderID;
        }
        return end;
    }

    @Override
    public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException {
        if (!Collections.list(AllStages.keys()).contains(stageId)){
            throw new IDNotRecognisedException("Stage ID not recognized");
        }
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
        if (!Collections.list(AllStages.keys()).contains(stageId)){
            throw new IDNotRecognisedException("Stage ID not recognized");
        }
        Stage TheStage = AllStages.get(stageId);
        int[] RidersInRank = getRidersRankInStage(stageId);
        ArrayList<RiderResults> stageResults = Collections.list(AllStages.get(stageId).AllRidersResults.elements());
        ArrayList<RiderResults> CurrentCheckpointTimes = new ArrayList<>();
        FinishTimeComparator a = new FinishTimeComparator();
        Collections.sort(stageResults,a);
        Dictionary<Integer,ArrayList<Integer>> Standings = new Hashtable<>();
        for (int o = 0; o < RidersInRank.length;o++){
            Standings.put(RidersInRank[o],new ArrayList<Integer>());//initialises all the standings,
        }
        int[] end = new int[stageResults.size()];
        int placeCount = 0;
        LocalTime TiePlace = LocalTime.parse("00:00:00");
        for (int j = 1; j < getRiderResultsInStage(stageId,RidersInRank[0]).length-1;j++) {//Goes through and compares winners for every checkpoint

            for (int i = 0; i < stageResults.size(); i++) {
                List<LocalTime> times = Arrays.asList(getRiderResultsInStage(stageId,stageResults.get(i).RiderID));
                CurrentCheckpointTimes.add(new RiderResults(TheStage.getStageID(),stageResults.get(i).RiderID,new LocalTime[]{times.get(j)}));
            }//adds a rider with one time, which is the currently observed time and the same id, therefore we can use the comparator to sort it
            Collections.sort(CurrentCheckpointTimes,a);//Sorts the current checkpoints times


            for (int i = 0; i < CurrentCheckpointTimes.size();i++){

                if(CurrentCheckpointTimes.get(i).checkpointTimes[0].equals(TiePlace)){
                    Standings.get(CurrentCheckpointTimes.get(i).RiderID).add(placeCount);
                    TiePlace = CurrentCheckpointTimes.get(i).checkpointTimes[0];//used for riders that finish at the same time, they are given the same positons as it did not specify what to do
                }else{
                    placeCount++;//if there is not a tie it increases the position place
                    Standings.get(CurrentCheckpointTimes.get(i).RiderID).add(placeCount);//adds the standings for the current checkpoint (1st,2nd 3rd etc.)
                    TiePlace = CurrentCheckpointTimes.get(i).checkpointTimes[0];
                }
            }

            CurrentCheckpointTimes.clear();
            placeCount = 0;
            TiePlace = LocalTime.parse("00:00:00");
        }


        for (int x = 0; x < RidersInRank.length; x++){//Converts the standings for each checkpoint to the points. Excludes mountains.
            ArrayList<Integer> places = Standings.get(RidersInRank[x]);
            for (int y = 0; y < places.size();y++){
                    if (y != places.size() - 1) {
                        if (TheStage.Checkpoints.get(y).type == CheckpointType.SPRINT) {
                            end[x] = end[x] + CheckpointType.getPoints(TheStage.Checkpoints.get(y).type)[places.get(y) - 1];
                        }
                    } else {
                        end[x] = end[x] + StageType.getPoints(TheStage.type)[places.get(y) - 1];//Points for finishing the stage the fastest.
                    }

            }
        }
        return end;
    }

    @Override
    public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException {
        if (!Collections.list(AllStages.keys()).contains(stageId)){
            throw new IDNotRecognisedException("Stage ID not recognized");
        }
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

        for(int j = 1; j < getRiderResultsInStage(stageId,RidersInRank[0]).length-1;j++){


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
            CurrentCheckpointTimes.clear();
            PlaceCount = 0;
            TiePlace = LocalTime.parse("00:00:00");
        }




        for (int x = 0; x < RidersInRank.length; x++){
            ArrayList<Integer> places = RidersStandings.get(RidersInRank[x]);
            for (int y = 0; y < places.size();y++){
                if (y != places.size() - 1) {
                    if (TheStage.Checkpoints.get(y).type != CheckpointType.SPRINT) {
                        end[x] = end[x] + CheckpointType.getPoints(TheStage.Checkpoints.get(y).type)[places.get(y) - 1];
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
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filename);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            CyclingPortalImpl a = this;
            objectOutputStream.writeObject(a);

            objectOutputStream.flush();
            objectOutputStream.close();
        }catch(IOException e){
            throw e;
        }
    }

    @Override
    public void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException {
        try{
            FileInputStream fileInputStream = new FileInputStream(filename);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            CyclingPortalImpl a = (CyclingPortalImpl) objectInputStream.readObject();
            objectInputStream.close();

                AllRiders = a.AllRiders;
                AllTeams = a.AllTeams;
                AllStages = a.AllStages;
                AllCheckpoints = a.AllCheckpoints;
                AllRaces = a.AllRaces;

        }catch(IOException e){
            throw e;
        }catch (ClassNotFoundException b){
            throw b;
        }

    }
}