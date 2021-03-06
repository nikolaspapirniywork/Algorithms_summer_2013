package coursera_stanford_2013.week3.graphs;

public class Edge implements Cloneable {
    private int from;
    private int to;

    public Edge(int from, int to) {
        this.from = from;
        this.to = to;
    }


    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }


    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Edge)) return false;

        Edge edge = (Edge) o;

        if (from != edge.from && from != edge.to)
            return false;

        if (to != edge.to && to != edge.from)
            return false;


        return true;
    }

    @Override
    public int hashCode() {
        int result = from;
        result = 31 * result + to;
        return result;
    }

    @Override
    public Edge clone() throws CloneNotSupportedException {
        return new Edge(from, to);
    }
}
