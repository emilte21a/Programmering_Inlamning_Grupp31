public class Edge {
    int weight, to, from;
    String name;

    Edge(int weight, int to, int from, String name) {
        this.name = name;
        this.weight = weight;
        this.to = to;
        this.from = from;
    }

    public int getDestination() {
        return to;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "weight=" + weight +
                ", to=" + to +
                ", from=" + from +
                ", name='" + name + '\'' +
                '}';
    }
}
