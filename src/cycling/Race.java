package cycling;

import java.util.ArrayList;
import java.util.Dictionary;

public class Race implements IDGenerator {
    protected String name;
    protected String description;
    private ArrayList<Stage> Stages = new ArrayList<>();
    private static int raceID;
    private static int nextID = 1;
    private Double TotalLength = 0.0;

    public Race(String name, String description) {
        this.name = name;
        this.description = description;
        this.raceID = GenerateID(nextID++);
    }

    public void DELETE(Dictionary<Integer,Race> AllRaces,Dictionary<Integer,Stage> AllStages){
        for(int i = 0; i < Stages.size();i++){
            Stages.get(i).DELETE(AllRaces,AllStages);
        }
        AllRaces.remove(raceID);
    }

    protected int getRaceID() {
        return raceID;
    }

    public ArrayList<Stage> getStages() {
        return Stages;
    }

    protected String GetDetails(){
        String string = "Race Details:\n\tID: "+raceID
                +"\n\tName: "+name
                +"\n\tDescription: "+description
                +"\n\tNo. of stages: "+ Stages.size()
                +"\n\tTotal Length of Race: "+ TotalLength ;

        return string;
    }
    protected int AddStage(Stage a){
        Stages.add(a);
        TotalLength += a.getLength();
        return a.getStageID();
    }

}
