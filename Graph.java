import java.util.List;

public interface Graph<T> {

    void add(T node);  

    void remove(T node);  

    void connect(T node1, T node2, String name, int weight);  

    void disconnect(T node1, T node2);  

    void setConnectionWeight(T node1, T node2, int weight);  

    List<T> getNodes();  

    List<Edge<T>> getEdgesFrom(T node); 

    Edge<T> getEdgeBetween(T node1, T node2);  

    boolean pathExists(T start, T end);  

    List<Edge<T>> getPath(T start, T end);  

}
