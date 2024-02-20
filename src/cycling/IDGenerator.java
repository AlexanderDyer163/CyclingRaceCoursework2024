package cycling;

public interface IDGenerator {

    default int GenerateID(int NextID){
        return NextID++;//will make more complicated later
    }
}
