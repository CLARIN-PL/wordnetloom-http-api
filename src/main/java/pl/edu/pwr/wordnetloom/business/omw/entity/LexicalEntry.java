package pl.edu.pwr.wordnetloom.business.omw.entity;

import javax.xml.bind.annotation.*;
import java.util.HashSet;
import java.util.Set;

@XmlRootElement(name = "LexicalEntry")
@XmlAccessorType(XmlAccessType.FIELD)
public class LexicalEntry {

    @XmlAttribute(required = true)
    private String id;

    @XmlElement(name = "Lemma")
    private Lemma lemma;

    @XmlElement(name = "Form")
    private Form form;

    @XmlElement(name = "Sense")
    private Set<Sense> sense = new HashSet<>();

    public LexicalEntry() {
    }

    private LexicalEntry(LexicalEntryBuilder builder) {
        id = builder.id;
        lemma = builder.lemma;
        form = builder.form;
        sense = builder.sense;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Lemma getLemma() {
        return lemma;
    }

    public void setLemma(Lemma lemma) {
        this.lemma = lemma;
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public Set<Sense> getSense() {
        return sense;
    }

    public void setSense(Set<Sense> sense) {
        this.sense = sense;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LexicalEntry)) return false;

        LexicalEntry that = (LexicalEntry) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (lemma != null ? !lemma.equals(that.lemma) : that.lemma != null) return false;
        if (form != null ? !form.equals(that.form) : that.form != null) return false;
        return sense != null ? sense.equals(that.sense) : that.sense == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (lemma != null ? lemma.hashCode() : 0);
        result = 31 * result + (form != null ? form.hashCode() : 0);
        result = 31 * result + (sense != null ? sense.hashCode() : 0);
        return result;
    }

    public static class LexicalEntryBuilder {

        private final String id;
        private final Lemma lemma;
        private Form form;
        private Set<Sense> sense = new HashSet<>();

        public LexicalEntryBuilder(String id, Lemma lemma) {
            this.id = id;
            this.lemma = lemma;
        }

        public LexicalEntryBuilder form(Form form) {
            this.form = form;
            return this;
        }

        public LexicalEntryBuilder addSense(Sense sense) {
            this.sense.add(sense);
            return this;
        }

        public LexicalEntry build() {
            return new LexicalEntry(this);
        }
    }
}
