package cycling;

import java.util.ArrayList;
public class Race implements IDGenerator {
    protected String name;
    protected String description;
    protected ArrayList<Stage> Stages = new ArrayList<>();
    private static int raceID;
    private static int nextID = 1;
    private Double TotalLength = 0.0;

    public Race(String name, String description) {
        this.name = name;
        this.description = description;
        this.raceID = GenerateID(nextID++);
    }

    public void DELETE(){
        for(int i = 0; i < Stages.size();i++){
            Stages.get(i).DELETE();
            Stages.remove(i);
        }
    }

    protected int getRaceID() {
        return raceID;
    }

    protected String GetDetails(){
        String string = "Race Details:\n\tID: "+raceID
                +"\n\tName: "+name
                +"\n\tDescription: "+description
                +"\n\tNo. of stages: "+ Stages.size()
                +"\n\tTotal Length of Race: "+ TotalLength ;

        return string;
    }
    protected void AddStage(Stage a){
        Stages.add(a);
        TotalLength += a.getLength();
    }

}
