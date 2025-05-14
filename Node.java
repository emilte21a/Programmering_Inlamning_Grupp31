public class Node {
    private int id;

    Node(int id){
        this.id=id;
    }

    public int getId(){
        return id;
    }

    // Jämförelsemetod, kollar ifall noder har samma ID
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Node other = (Node) obj;
        return this.id == other.id;
    }

    // För att noderna ska fungera på rätt sätt i mängder och mappar
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public String toString(){
        return "Node[id=" + id + "]";
    }
}
