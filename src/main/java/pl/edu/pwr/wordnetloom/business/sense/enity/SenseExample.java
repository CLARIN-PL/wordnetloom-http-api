package pl.edu.pwr.wordnetloom.business.sense.enity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "sense_examples")
@NamedQuery(name = SenseExample.FIND_BY_ID, query = "SELECT ex FROM SenseExample ex WHERE ex.id = :id")
public class SenseExample implements Serializable {

    public static final String FIND_BY_ID = "SenseExample.finaById";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sense_attribute_id")
    private SenseAttributes senseAttributes;

    @Column(name = "example")
    private String example;

    @Column(name = "type")
    private String type;

    public SenseAttributes getSenseAttributes() {
        return senseAttributes;
    }

    public void setSenseAttributes(SenseAttributes senseAttributes) {
        this.senseAttributes = senseAttributes;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
