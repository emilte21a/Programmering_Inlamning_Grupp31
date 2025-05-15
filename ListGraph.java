import java.util.*;

public class ListGraph<T> implements Graph<T> {
    private final Map<T, List<Edge<T>>> adjacencyList;

    public ListGraph() {
        this.adjacencyList = new HashMap<>();
    }

    @Override
    public void add(T node) {
        if (node == null)
            throw new IllegalArgumentException("Node cannot be null");
        adjacencyList.putIfAbsent(node, new ArrayList<>());

    }

    @Override
    public void remove(T node) {
        if (!adjacencyList.containsKey(node)) {
            throw new NoSuchElementException("Node not found: " + node);
        }

        // Ta bort noden
        adjacencyList.remove(node);

        // Ta bort kanter som pekar till noden från andra noder
        for (List<Edge<T>> edges : adjacencyList.values()) {
            edges.removeIf(edge -> edge.getDestination().equals(node));
        }
    }

    @Override
    public void connect(T node1, T node2, String name, int weight) {
        if (!adjacencyList.containsKey(node1) || !adjacencyList.containsKey(node2)) {
            throw new NoSuchElementException("One or both nodes not found");
        }
        if (weight < 0) {
            throw new IllegalArgumentException("Weight cannot be negative");
        }
        if (getEdgeBetween(node1, node2) != null) {
            throw new IllegalStateException("Edge already exists between " + node1 + " and " + node2);
        }

        adjacencyList.get(node1).add(new Edge<>(node2, name, weight));
        adjacencyList.get(node2).add(new Edge<>(node1, name, weight));
    }

    @Override
    public void disconnect(T node1, T node2) {
        if (!adjacencyList.containsKey(node1) || !adjacencyList.containsKey(node2)) {
            throw new NoSuchElementException("One or both nodes not found");
        }

        Edge<T> edge1 = getEdgeBetween(node1, node2);
        Edge<T> edge2 = getEdgeBetween(node2, node1);

        if (edge1 == null || edge2 == null) {
            throw new IllegalStateException("No edge exists between " + node1 + " and " + node2);
        }

        adjacencyList.get(node1).remove(edge1);
        adjacencyList.get(node2).remove(edge2);
    }

    @Override
    public void setConnectionWeight(T node1, T node2, int weight) {
        if (weight < 0) {
            throw new IllegalArgumentException("Weight cannot be negative");
        }

        Edge<T> edge1 = getEdgeBetween(node1, node2);
        Edge<T> edge2 = getEdgeBetween(node2, node1);

        if (edge1 == null || edge2 == null) {
            throw new NoSuchElementException("Edge does not exist between " + node1 + " and " + node2);
        }

        edge1.setWeight(weight);
        edge2.setWeight(weight);
    }

    @Override
    public List<T> getNodes() {
        return new ArrayList<>(adjacencyList.keySet());
    }

    @Override
    public List<Edge<T>> getEdgesFrom(T node) {
        if (!adjacencyList.containsKey(node)) {
            throw new NoSuchElementException("Node not found: " + node);
        }
        return new ArrayList<>(adjacencyList.get(node));
    }

    @Override
    public Edge<T> getEdgeBetween(T node1, T node2) {
        if (!adjacencyList.containsKey(node1) || !adjacencyList.containsKey(node2)) {
            throw new NoSuchElementException("One or both nodes not found");
        }

        for (Edge<T> edge : adjacencyList.get(node1)) {
            if (edge.getDestination().equals(node2)) {
                return edge;
            }
        }
        return null;
    }

    @Override
    public boolean pathExists(T start, T end) {
        if (!adjacencyList.containsKey(start) || !adjacencyList.containsKey(end)) {
            return false;
        }
        Set<T> visited = new HashSet<>();
        return dfs(start, end, visited);
    }

    private boolean dfs(T current, T target, Set<T> visited) {
        if (current.equals(target))
            return true;
        visited.add(current);
        for (Edge<T> edge : adjacencyList.get(current)) {
            T neighbor = edge.getDestination();
            if (!visited.contains(neighbor)) {
                if (dfs(neighbor, target, visited)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public List<Edge<T>> getPath(T start, T end) {
        if (!adjacencyList.containsKey(start) || !adjacencyList.containsKey(end)) {
            return null;
        }

        Map<T, T> cameFrom = new HashMap<>();
        Queue<T> queue = new LinkedList<>();
        Set<T> visited = new HashSet<>();

        queue.add(start);
        visited.add(start);
        cameFrom.put(start, null);

        while (!queue.isEmpty()) {
            T current = queue.poll();

            if (current.equals(end)) {
                LinkedList<Edge<T>> path = new LinkedList<>();
                T node = end;

                while (!node.equals(start)) {
                    T prev = cameFrom.get(node);
                    Edge<T> edge = getEdgeBetween(prev, node);
                    path.addFirst(edge);
                    node = prev;
                }
                return path;
            }

            for (Edge<T> edge : adjacencyList.get(current)) {
                T neighbor = edge.getDestination();
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    cameFrom.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (T node : adjacencyList.keySet()) {
            sb.append(node.toString()).append(" -> ");
            List<Edge<T>> edges = adjacencyList.get(node);
            for (Edge<T> edge : edges) {
                sb.append(edge.toString()).append(", ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
