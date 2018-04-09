package pl.edu.pwr.wordnetloom.business.sense.enity;

import pl.edu.pwr.wordnetloom.business.relationtype.entity.RelationType;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "sense_relation")
@NamedQuery(name = SenseRelation.FIND_BY_ID, query = "SELECT r FROM SenseRelation r WHERE r.id = :relId")
public class SenseRelation implements Serializable {

    public static final String FIND_BY_ID = "SenseRelation.findById";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "relation_type_id", referencedColumnName = "id", nullable = false)
    private RelationType relationType;

    @ManyToOne
    @JoinColumn(name = "parent_sense_id", referencedColumnName = "id", nullable = false)
    private Sense parent;

    @ManyToOne
    @JoinColumn(name = "child_sense_id", referencedColumnName = "id", nullable = false)
    private Sense child;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RelationType getRelationType() {
        return relationType;
    }

    public void setRelationType(RelationType relationType) {
        this.relationType = relationType;
    }

    public Sense getParent() {
        return parent;
    }

    public void setParent(Sense parent) {
        this.parent = parent;
    }

    public Sense getChild() {
        return child;
    }

    public void setChild(Sense child) {
        this.child = child;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SenseRelation that = (SenseRelation) o;

        if (id != that.id) return false;
        if (!relationType.equals(that.relationType)) return false;
        if (!parent.equals(that.parent)) return false;
        return child.equals(that.child);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + relationType.hashCode();
        result = 31 * result + parent.hashCode();
        result = 31 * result + child.hashCode();
        return result;
    }
}
