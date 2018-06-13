package pl.edu.pwr.wordnetloom.business.omw.entity;


import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@XmlRootElement(name = "Sense")
@XmlAccessorType(XmlAccessType.FIELD)
public class Sense {

    @XmlAttribute(required = true)
    private String id;

    @XmlAttribute(required = true)
    private String synset;

    @XmlAttribute(required = false)
    private String note;

    @XmlElement(name = "SenseRelation")
    private Set<SenseRelation> senseRelations = new HashSet<>();

    @XmlElement(name = "Example")
    private List<Example> examples = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSynset() {
        return synset;
    }

    public void setSynset(String synset) {
        this.synset = synset;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Set<SenseRelation> getSenseRelations() {
        return senseRelations;
    }

    public void setSenseRelations(Set<SenseRelation> senseRelations) {
        this.senseRelations = senseRelations;
    }

    public List<Example> getExamples() {
        return examples;
    }

    public void setExamples(List<Example> examples) {
        this.examples = examples;
    }

    public Sense() {
    }

    private Sense(SenseBuilder builder) {
        id = builder.id;
        synset = builder.synset;
        note = builder.note;
        senseRelations = builder.senseRelations;
        examples = builder.examples;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sense)) return false;

        Sense sense = (Sense) o;

        if (id != null ? !id.equals(sense.id) : sense.id != null) return false;
        if (synset != null ? !synset.equals(sense.synset) : sense.synset != null) return false;
        if (note != null ? !note.equals(sense.note) : sense.note != null) return false;
        if (senseRelations != null ? !senseRelations.equals(sense.senseRelations) : sense.senseRelations != null)
            return false;
        return examples != null ? examples.equals(sense.examples) : sense.examples == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (synset != null ? synset.hashCode() : 0);
        result = 31 * result + (note != null ? note.hashCode() : 0);
        result = 31 * result + (senseRelations != null ? senseRelations.hashCode() : 0);
        result = 31 * result + (examples != null ? examples.hashCode() : 0);
        return result;
    }

    public static class SenseBuilder {

        private final String id;
        private final String synset;
        private String note;
        private Set<SenseRelation> senseRelations;
        private List<Example> examples;

        public SenseBuilder(String id, String synset) {
            this.id = id;
            this.synset = synset;
        }

        public SenseBuilder note(String note) {
            this.note = note;
            return this;
        }

        public SenseBuilder senseRelations(Set<SenseRelation> relations) {
            senseRelations = relations;
            return this;
        }

        public SenseBuilder examples(List<Example> examples) {
            this.examples = examples;
            return this;
        }

        public Sense build() {
            return new Sense(this);
        }
    }
}
