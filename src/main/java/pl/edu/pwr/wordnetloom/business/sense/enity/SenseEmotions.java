package pl.edu.pwr.wordnetloom.business.sense.enity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "sense_emotions")
@NamedQueries({
        @NamedQuery(name = SenseEmotions.FIND_BY_SENSE_ID, query = "SELECT DISTINCT se FROM SenseEmotions se WHERE se.sense.id = :id"),
})
public class SenseEmotions implements Serializable {

    public static final String FIND_BY_SENSE_ID = "SenseEmotions.findBySenseId";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "sense_id")
    private Sense sense;

    @Column(name = "super_anotation")
    private boolean superAnnotation;

    @Column(name = "has_emotional_characteristic")
    private boolean hasEmotionalCharacteristic;

    private String emotions;

    private String valuations;

    private String markedness;

    private String example1;

    private String example2;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Sense getSense() {
        return sense;
    }

    public void setSense(Sense sense) {
        this.sense = sense;
    }

    public boolean isSuperAnnotation() {
        return superAnnotation;
    }

    public void setSuperAnotation(boolean superAnotation) {
        this.superAnnotation = superAnotation;
    }

    public boolean isHasEmotionalCharacteristic() {
        return hasEmotionalCharacteristic;
    }

    public void setHasEmotionalCharacteristic(boolean hasEmotionalCharacteristic) {
        this.hasEmotionalCharacteristic = hasEmotionalCharacteristic;
    }

    public String getEmotions() {
        return emotions;
    }

    public void setEmotions(String emotions) {
        this.emotions = emotions;
    }

    public String getValuations() {
        return valuations;
    }

    public void setValuations(String valuations) {
        this.valuations = valuations;
    }

    public String getMarkedness() {
        return markedness;
    }

    public void setMarkedness(String markedness) {
        this.markedness = markedness;
    }

    public String getExample1() {
        return example1;
    }

    public void setExample1(String example1) {
        this.example1 = example1;
    }

    public String getExample2() {
        return example2;
    }

    public void setExample2(String example2) {
        this.example2 = example2;
    }
}