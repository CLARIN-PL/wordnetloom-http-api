package pl.edu.pwr.wordnetloom.business.graph.entity;

public class RootNode {

    private long id;
    private long pos;
    private String label;

    public RootNode() {
    }

    public RootNode(long id, long pos, String label) {
        this.id = id;
        this.pos = pos;
        this.label = label;
    }

    public long getId() {
        return id;
    }

    public long getPos() {
        return pos;
    }

    public String getLabel() {
        return label;
    }
}
