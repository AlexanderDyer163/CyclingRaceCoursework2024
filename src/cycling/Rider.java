package cycling;

public class Rider implements IDGenerator{
    private int nextID = 0;
    private int id = GenerateID(nextID++);
    private Team CurrentTeam;
    private int YearOfBirth;
    private String name;

    public Rider(Team currentTeam, String name,int yearOfBirth) {
        this.CurrentTeam = currentTeam;
        this.YearOfBirth = yearOfBirth;
        this.name = name;
    }

    public int getId() {
        return id;
    }
}
