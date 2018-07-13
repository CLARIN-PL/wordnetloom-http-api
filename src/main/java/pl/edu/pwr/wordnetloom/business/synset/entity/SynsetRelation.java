package pl.edu.pwr.wordnetloom.business.synset.entity;

import pl.edu.pwr.wordnetloom.business.graph.entity.NodeDirection;
import pl.edu.pwr.wordnetloom.business.relationtype.entity.RelationType;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "synset_relation")
@NamedQuery(name = SynsetRelation.FIND_BY_PARENT_LEXICON_ID,
        query = "SELECT r FROM SynsetRelation r " +
                "JOIN FETCH r.parent p " +
                "JOIN FETCH r.child c " +
                "JOIN FETCH c.attributes " +
                "JOIN FETCH p.lexicon pl " +
                "JOIN FETCH c.lexicon  cl " +
                "JOIN FETCH r.relationType " +
                "WHERE pl.id = :lexId")
public class SynsetRelation implements Serializable {

    public static final String FIND_BY_PARENT_LEXICON_ID = "Synset.findByParentLexiconId";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "synset_relation_type_id", referencedColumnName = "id", nullable = false)
    private RelationType relationType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_synset_id", referencedColumnName = "id", nullable = false)
    private Synset parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_synset_id", referencedColumnName = "id", nullable = false)
    private Synset child;

    public SynsetRelation() {
    }

    public SynsetRelation(Long id, Long relationTypeId, Long parentId, Long childId, NodeDirection direction) {
        this.id = id;
        relationType = new RelationType();
        relationType.setId(relationTypeId);
        relationType.setNodePosition(direction);
        parent = new Synset();
        parent.setId(parentId);
        child = new Synset();
        child.setId(childId);

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Synset getParent() {
        return parent;
    }

    public void setParent(Synset parent) {
        this.parent = parent;
    }

    public Synset getChild() {
        return child;
    }

    public void setChild(Synset child) {
        this.child = child;
    }

    public RelationType getRelationType() {
        return relationType;
    }

    public void setRelationType(RelationType relationType) {
        this.relationType = relationType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SynsetRelation that = (SynsetRelation) o;

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
