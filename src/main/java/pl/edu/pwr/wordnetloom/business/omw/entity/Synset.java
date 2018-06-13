package pl.edu.pwr.wordnetloom.business.omw.entity;


import javax.xml.bind.annotation.*;
import java.util.HashSet;
import java.util.Set;

@XmlRootElement(name = "Synset")
@XmlAccessorType(XmlAccessType.FIELD)
public class Synset {

    @XmlAttribute(required = true)
    private String id;

    @XmlAttribute(required = true)
    private String ili;

    @XmlAttribute
    private String note;

    @XmlElement(name = "Definition")
    private Set<Definition> definitions = new HashSet<>();

    @XmlElement(name = "ILIDefinition")
    private ILIDefinition iliDefinition;

    @XmlElement(name = "SynsetRelation")
    private Set<SynsetRelation> synsetRelations = new HashSet<>();

    @XmlElement(name = "Example")
    private Example example;

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

    public Set<Definition> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(Set<Definition> definitions) {
        this.definitions = definitions;
    }

    public ILIDefinition getIliDefinition() {
        return iliDefinition;
    }

    public void setIliDefinition(ILIDefinition iliDefinition) {
        this.iliDefinition = iliDefinition;
    }

    public Set<SynsetRelation> getSynsetRelations() {
        return synsetRelations;
    }

    public void setSynsetRelations(Set<SynsetRelation> synsetRelations) {
        this.synsetRelations = synsetRelations;
    }

    public Example getExample() {
        return example;
    }

    public void setExample(Example example) {
        this.example = example;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof Synset)) return false;

        Synset synset = (Synset) o;

        if (id != null ? !id.equals(synset.id) : synset.id != null) return false;
        if (ili != null ? !ili.equals(synset.ili) : synset.ili != null) return false;
        if (note != null ? !note.equals(synset.note) : synset.note != null) return false;
        if (definitions != null ? !definitions.equals(synset.definitions) : synset.definitions != null) return false;
        if (iliDefinition != null ? !iliDefinition.equals(synset.iliDefinition) : synset.iliDefinition != null)
            return false;
        if (synsetRelations != null ? !synsetRelations.equals(synset.synsetRelations) : synset.synsetRelations != null)
            return false;
        return example != null ? example.equals(synset.example) : synset.example == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (ili != null ? ili.hashCode() : 0);
        result = 31 * result + (note != null ? note.hashCode() : 0);
        result = 31 * result + (definitions != null ? definitions.hashCode() : 0);
        result = 31 * result + (iliDefinition != null ? iliDefinition.hashCode() : 0);
        result = 31 * result + (synsetRelations != null ? synsetRelations.hashCode() : 0);
        result = 31 * result + (example != null ? example.hashCode() : 0);
        return result;
    }

    public Synset() {
    }

    ;

    private Synset(SynsetBuilder builder) {
        this.id = builder.id;
        this.ili = builder.ili;
        this.note = builder.note;
        this.definitions = builder.definitions;
        this.iliDefinition = builder.iliDefinition;
        this.synsetRelations = builder.synsetRelations;
        this.example = builder.example;
    }

    public static class SynsetBuilder {

        private final String id;
        private final String ili;
        private String note;
        private Set<Definition> definitions = new HashSet<>();
        private ILIDefinition iliDefinition;
        private Example example;
        private Set<SynsetRelation> synsetRelations = new HashSet<>();

        public SynsetBuilder(String id, String ili) {
            this.id = id;
            this.ili = ili;
        }

        public SynsetBuilder note(String note) {
            this.note = note;
            return this;
        }

        public SynsetBuilder addDefinition(Definition definition) {
            if (definition != null && !"".equals(definition) && definitions.size() == 0) {
                this.definitions.add(definition);
            }
            return this;
        }

        public SynsetBuilder iliDefinition(ILIDefinition definition) {
            if (definition != null && definition.getValue() != null && !"".equals(definition) && ili.equals("in")) {
                this.iliDefinition = definition;
            }
            return this;
        }

        public SynsetBuilder addExample(String example) {
            if (example != null) {
                this.example = new Example(example);
            }
            return this;
        }

        public SynsetBuilder synsetRelations(Set<SynsetRelation> relations) {
            this.synsetRelations = relations;
            return this;
        }

        public Synset build() {
            return new Synset(this);
        }
    }
}
