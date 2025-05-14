import java.util.*;

public class ListGraph<T> implements Graph<Node> {
    private HashMap<Integer, Node> nodes;
    private HashMap<Integer, List<Edge>> edges;

    public ListGraph() {
        nodes = new HashMap<>();
        edges = new HashMap<>();
    }

    @Override
    public void add(int id) {
        if (!nodes.containsKey(id)) {
            nodes.put(id, new Node(id));
            edges.put(id, new ArrayList<>());
        }
    }

    @Override
    public void remove(int id) {
        if (!nodes.containsKey(id)) {
            throw new IllegalArgumentException("Node with id " + id + " does not exist.");
        }

        for (Edge edge : edges.get(id)) {
            edges.get(edge.getDestination()).removeIf(e -> e.getDestination() == id);
        }
        edges.remove(id);
        nodes.remove(id);
    }

    @Override
    public void connect(int id1, int id2, String name, int weight) {
        if (!nodes.containsKey(id1) || nodes.containsKey(id2)) {
            throw new NoSuchElementException("One or both nodes do not exist");
        }

        if (weight < 0) {
            throw new IllegalArgumentException("Weight cannot be negative");
        }

        for (Edge edge : edges.get(id1)) {
            if (edge.getDestination() == id2) {
                throw new IllegalStateException("An edge already exists between these nodes");
            }
        }

        Edge edge1 = new Edge(weight, id2, id1, name);
        Edge edge2 = new Edge(weight, id1, id2, name);

        edges.get(id1).add(edge1);
        edges.get(id2).add(edge2);
    }

    @Override
    public void disconnect(int id1, int id2) {
        if (!nodes.containsKey(id1) || !nodes.containsKey(id2)) {
            throw new NoSuchElementException("One or both nodes do not exist");
        }

        Edge edge = getEdgeBetween(id1, id2);
        if (edge == null) {
            throw new IllegalStateException("No edge exists between these nodes");

        }
        edges.get(id2).removeIf(e -> e.getDestination() == id1);
    }

    @Override
    public void setConnectionWeight(int id1, int id2, int weight) {

        if (!nodes.containsKey(id1) || !nodes.containsKey(id2)) {

            throw new NoSuchElementException("One or both nodes do not exist");
        }

        if (getEdgeBetween(id1, id2) == null) {
            throw new NoSuchElementException("No edge exists between these nodes");
        }

        if (weight < 0) {
            throw new IllegalArgumentException("Weight cannot be negative");
        }

        getEdgeBetween(id1, id2).setWeight(weight);
        getEdgeBetween(id2, id1).setWeight(weight);
    }

    @Override
    public List<Node> getNodes() {
        return new ArrayList<>(nodes.values());
    }

    @Override
    public List<Edge> getEdgesFrom(int id) {
        return new ArrayList<>(edges.get(id));
    }

    @Override
    public Edge getEdgeBetween(int id1, int id2) {
        for (Edge edge : edges.get(id1)) {
            if (edge.getDestination() == id2) {
                return edge;
            }
        }
        return null;
    }
}