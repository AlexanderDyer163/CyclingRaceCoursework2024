package cycling;

import java.security.SecureRandom;

public interface IDGenerator {

    default int GenerateID(){
        SecureRandom random = new SecureRandom();
        int id = random.nextInt();
        return id;
    }
}
