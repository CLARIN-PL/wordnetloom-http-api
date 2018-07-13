package pl.edu.pwr.wordnetloom.business.download.entity;


import javax.xml.bind.annotation.*;
import java.util.HashSet;
import java.util.Set;

@XmlRootElement(name = "Synset")
@XmlAccessorType(XmlAccessType.FIELD)
public class OmwSynset {

    @XmlAttribute(required = true)
    private String id;

    @XmlAttribute(required = true)
    private String ili;

    @XmlAttribute
    private String note;

    @XmlAttribute(required = true)
    private String partOfSpeech;

    @XmlElement(name = "Definition")
    private Set<OmwDefinition> definitions = new HashSet<>();

    @XmlElement(name = "ILIDefinition")
    private OmwILIDefinition omwIliDefinition;

    @XmlElement(name = "SynsetRelation")
    private Set<OmwSynsetRelation> omwSynsetRelations = new HashSet<>();

    @XmlElement(name = "Example")
    private OmwExample example;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIli() {
        return ili;
    }

    public void setIli(String ili) {
        this.ili = ili;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Set<OmwDefinition> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(Set<OmwDefinition> definitions) {
        this.definitions = definitions;
    }

    public OmwILIDefinition getOmwIliDefinition() {
        return omwIliDefinition;
    }

    public void setOmwIliDefinition(OmwILIDefinition omwIliDefinition) {
        this.omwIliDefinition = omwIliDefinition;
    }

    public Set<OmwSynsetRelation> getOmwSynsetRelations() {
        return omwSynsetRelations;
    }

    public void setOmwSynsetRelations(Set<OmwSynsetRelation> omwSynsetRelations) {
        this.omwSynsetRelations = omwSynsetRelations;
    }

    public OmwExample getExample() {
        return example;
    }

    public void setExample(OmwExample example) {
        this.example = example;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof OmwSynset)) return false;

        OmwSynset omwSynset = (OmwSynset) o;

        if (id != null ? !id.equals(omwSynset.id) : omwSynset.id != null) return false;
        if (ili != null ? !ili.equals(omwSynset.ili) : omwSynset.ili != null) return false;
        if (note != null ? !note.equals(omwSynset.note) : omwSynset.note != null) return false;
        if (definitions != null ? !definitions.equals(omwSynset.definitions) : omwSynset.definitions != null) return false;
        if (omwIliDefinition != null ? !omwIliDefinition.equals(omwSynset.omwIliDefinition) : omwSynset.omwIliDefinition != null)
            return false;
        if (omwSynsetRelations != null ? !omwSynsetRelations.equals(omwSynset.omwSynsetRelations) : omwSynset.omwSynsetRelations != null)
            return false;
        return example != null ? example.equals(omwSynset.example) : omwSynset.example == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (ili != null ? ili.hashCode() : 0);
        result = 31 * result + (note != null ? note.hashCode() : 0);
        result = 31 * result + (definitions != null ? definitions.hashCode() : 0);
        result = 31 * result + (omwIliDefinition != null ? omwIliDefinition.hashCode() : 0);
        result = 31 * result + (omwSynsetRelations != null ? omwSynsetRelations.hashCode() : 0);
        result = 31 * result + (example != null ? example.hashCode() : 0);
        return result;
    }

    public OmwSynset() {
    }

    public OmwSynset(OmwSynset s, Set<OmwSynsetRelation> omwSynsetRelations) {
        this.id = s.id;
        this.ili = s.ili;
        this.note = s.note;
        this.partOfSpeech = s.partOfSpeech;
        this.definitions = s.definitions;
        this.omwIliDefinition = s.omwIliDefinition;
        this.omwSynsetRelations = omwSynsetRelations;
        this.example = s.example;
    }

    private OmwSynset(SynsetBuilder builder) {
        id = builder.id;
        ili = builder.ili;
        note = builder.note;
        partOfSpeech = builder.partOfSpeech;
        definitions = builder.definitions;
        omwIliDefinition = builder.omwIliDefinition;
        omwSynsetRelations = builder.omwSynsetRelations;
        example = builder.example;
    }

    public static class SynsetBuilder {

        private final String id;
        private final String ili;
        private String note;
        private String partOfSpeech;
        private Set<OmwDefinition> definitions = new HashSet<>();
        private OmwILIDefinition omwIliDefinition;
        private OmwExample example;
        private Set<OmwSynsetRelation> omwSynsetRelations = new HashSet<>();

        public SynsetBuilder(String id, String ili) {
            this.id = id;
            this.ili = ili;
        }

        public SynsetBuilder partOfSpeech(String pos) {
            this.partOfSpeech = pos;
            return this;
        }

        public SynsetBuilder note(String note) {
            this.note = note;
            return this;
        }

        public SynsetBuilder addDefinition(OmwDefinition definition) {
            if (definition != null && !"".equals(definition) && definitions.size() == 0) {
                definitions.add(definition);
            }
            return this;
        }

        public SynsetBuilder iliDefinition(OmwILIDefinition definition) {
            if (definition != null && definition.getValue() != null && !"".equals(definition) && ili.equals("in")) {
                omwIliDefinition = definition;
            }
            return this;
        }

        public SynsetBuilder addExample(String example) {
            if (example != null) {
                this.example = new OmwExample(example);
            }
            return this;
        }

        public SynsetBuilder synsetRelations(Set<OmwSynsetRelation> relations) {
            omwSynsetRelations = relations;
            return this;
        }

        public OmwSynset build() {
            return new OmwSynset(this);
        }
    }
}
