public class Edge<T> {
    private final T destination; 
    private final String name;
    private int weight; 

    public Edge(T destination, String name, int weight) {
        if (weight < 0) {
            throw new IllegalArgumentException("Weight cannot be negative");
        }
        this.destination = destination;
        this.name = name;
        this.weight = weight;
    }

    public T getDestination() {
        return destination;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        if (weight < 0) {
            throw new IllegalArgumentException("Weight cannot be negative");
        }
        this.weight = weight;
    }

    @Override
    public String toString() {
        return String.format("Edge{name='%s', destination=%s, weight=%d}", name, destination.toString(), weight);
    }
}
