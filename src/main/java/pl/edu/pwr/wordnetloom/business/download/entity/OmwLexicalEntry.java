package pl.edu.pwr.wordnetloom.business.download.entity;

import javax.xml.bind.annotation.*;
import java.util.HashSet;
import java.util.Set;

@XmlRootElement(name = "LexicalEntry")
@XmlAccessorType(XmlAccessType.FIELD)
public class OmwLexicalEntry {

    @XmlAttribute(required = true)
    private String id;

    @XmlElement(name = "Lemma")
    private OmwLemma omwLemma;

    @XmlElement(name = "Form")
    private OmwForm omwForm;

    @XmlElement(name = "Sense")
    private Set<OmwSense> omwSense = new HashSet<>();

    public OmwLexicalEntry() {
    }

    private OmwLexicalEntry(LexicalEntryBuilder builder) {
        id = builder.id;
        omwLemma = builder.omwLemma;
        omwForm = builder.omwForm;
        omwSense = builder.omwSense;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OmwLemma getOmwLemma() {
        return omwLemma;
    }

    public void setOmwLemma(OmwLemma omwLemma) {
        this.omwLemma = omwLemma;
    }

    public OmwForm getOmwForm() {
        return omwForm;
    }

    public void setOmwForm(OmwForm omwForm) {
        this.omwForm = omwForm;
    }

    public Set<OmwSense> getOmwSense() {
        return omwSense;
    }

    public void addOmwSense(OmwSense s) {
        omwSense.add(s);
    }

    public void setOmwSense(Set<OmwSense> omwSense) {
        this.omwSense = omwSense;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OmwLexicalEntry)) return false;

        OmwLexicalEntry that = (OmwLexicalEntry) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (omwLemma != null ? !omwLemma.equals(that.omwLemma) : that.omwLemma != null) return false;
        if (omwForm != null ? !omwForm.equals(that.omwForm) : that.omwForm != null) return false;
        return omwSense != null ? omwSense.equals(that.omwSense) : that.omwSense == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (omwLemma != null ? omwLemma.hashCode() : 0);
        result = 31 * result + (omwForm != null ? omwForm.hashCode() : 0);
        result = 31 * result + (omwSense != null ? omwSense.hashCode() : 0);
        return result;
    }

    public static class LexicalEntryBuilder {

        private final String id;
        private final OmwLemma omwLemma;
        private OmwForm omwForm;
        private Set<OmwSense> omwSense = new HashSet<>();

        public LexicalEntryBuilder(String id, OmwLemma omwLemma) {
            this.id = id;
            this.omwLemma = omwLemma;
        }

        public LexicalEntryBuilder form(OmwForm omwForm) {
            this.omwForm = omwForm;
            return this;
        }

        public LexicalEntryBuilder addSense(OmwSense omwSense) {
            this.omwSense.add(omwSense);
            return this;
        }

        public OmwLexicalEntry build() {
            return new OmwLexicalEntry(this);
        }
    }
}
