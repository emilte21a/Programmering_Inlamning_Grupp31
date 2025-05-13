import java.util.*;

public interface Graph {

    void add(int id);

    void remove(int id);

    void connect(int id1, int id2, String name, int weight);

    void disconnect(int id1, int id2);

    void setConnectionWeight(int id1, int id2, int weight);

    
}
