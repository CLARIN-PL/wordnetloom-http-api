package pl.edu.pwr.wordnetloom.business.download.entity;


import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@XmlRootElement(name = "Sense")
@XmlAccessorType(XmlAccessType.FIELD)
public class OmwSense {

    @XmlAttribute(required = true)
    private String id;

    @XmlAttribute(required = true)
    private String synset;

    @XmlAttribute(required = false)
    private String note;

    @XmlElement(name = "SenseRelation")
    private Set<OmwSenseRelation> omwSenseRelations = new HashSet<>();

    @XmlElement(name = "Example")
    private List<OmwExample> examples = new ArrayList<>();

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

    public Set<OmwSenseRelation> getOmwSenseRelations() {
        return omwSenseRelations;
    }

    public void setOmwSenseRelations(Set<OmwSenseRelation> omwSenseRelations) {
        this.omwSenseRelations = omwSenseRelations;
    }

    public List<OmwExample> getExamples() {
        return examples;
    }

    public void setExamples(List<OmwExample> examples) {
        this.examples = examples;
    }

    public OmwSense() {
    }

    private OmwSense(SenseBuilder builder) {
        id = builder.id;
        synset = builder.synset;
        note = builder.note;
        omwSenseRelations = builder.omwSenseRelations;
        examples = builder.examples;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OmwSense)) return false;

        OmwSense omwSense = (OmwSense) o;

        if (id != null ? !id.equals(omwSense.id) : omwSense.id != null) return false;
        if (synset != null ? !synset.equals(omwSense.synset) : omwSense.synset != null) return false;
        if (note != null ? !note.equals(omwSense.note) : omwSense.note != null) return false;
        if (omwSenseRelations != null ? !omwSenseRelations.equals(omwSense.omwSenseRelations) : omwSense.omwSenseRelations != null)
            return false;
        return examples != null ? examples.equals(omwSense.examples) : omwSense.examples == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (synset != null ? synset.hashCode() : 0);
        result = 31 * result + (note != null ? note.hashCode() : 0);
        result = 31 * result + (omwSenseRelations != null ? omwSenseRelations.hashCode() : 0);
        result = 31 * result + (examples != null ? examples.hashCode() : 0);
        return result;
    }

    public static class SenseBuilder {

        private final String id;
        private final String synset;
        private String note;
        private Set<OmwSenseRelation> omwSenseRelations;
        private List<OmwExample> examples;

        public SenseBuilder(String id, String synset) {
            this.id = id;
            this.synset = synset;
        }

        public SenseBuilder note(String note) {
            this.note = note;
            return this;
        }

        public SenseBuilder senseRelations(Set<OmwSenseRelation> relations) {
            omwSenseRelations = relations;
            return this;
        }

        public SenseBuilder examples(List<OmwExample> examples) {
            this.examples = examples;
            return this;
        }

        public OmwSense build() {
            return new OmwSense(this);
        }
    }
}
