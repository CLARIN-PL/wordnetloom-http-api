package pl.edu.pwr.wordnetloom.business.synset.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "synset_examples")
@NamedQuery(name = SynsetExample.FIND_BY_ID, query = "SELECT ex FROM SynsetExample ex WHERE ex.id = :id")
public class SynsetExample implements Serializable {

    public static final String FIND_BY_ID = "SynsetExample.finaById";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "synset_attributes_id")
    private SynsetAttributes synsetAttributes;

    @Column(name = "example")
    private String example;

    @Column(name = "type")
    private String type;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public SynsetAttributes getSynsetAttributes() {
        return synsetAttributes;
    }

    public void setSynsetAttributes(SynsetAttributes ssynsetAttributes) {
        synsetAttributes = ssynsetAttributes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
