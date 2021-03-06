package pl.edu.pwr.wordnetloom.business.download.entity;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@XmlRootElement(name = "Lexicon")
@XmlAccessorType(XmlAccessType.FIELD)
public class OmwLexicon {

    @XmlAttribute(required = true)
    private String id;

    @XmlAttribute(required = true)
    private String label;

    @XmlAttribute(required = true)
    private String language;

    @XmlAttribute(required = true)
    private String email;

    @XmlAttribute(required = true)
    private String license;

    @XmlAttribute(required = true)
    private String version;

    @XmlAttribute(required = false)
    private String url;

    @XmlAttribute(required = false)
    private String citation;

    @XmlAttribute(required = true)
    private String confidenceScore;

    @XmlElement(name = "LexicalEntry")
    private List<OmwLexicalEntry> lexicalEntries = new ArrayList<>();

    @XmlElement(name = "Synset")
    private List<OmwSynset> omwSynsets = new ArrayList<>();

    public OmwLexicon() {
    }

    private OmwLexicon(LexiconBuilder builder) {
        id = builder.id;
        label = builder.label;
        language = builder.language;
        email = builder.email;
        license = builder.license;
        version = builder.version;
        url = builder.url;
        lexicalEntries = builder.lexicalEntries;
        omwSynsets = builder.omwSynsets;
        citation = builder.citation;
        confidenceScore = builder.confidenceScore;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCitation() {
        return citation;
    }

    public void setCitation(String citation) {
        this.citation = citation;
    }

    public String getConfidenceScore() {
        return confidenceScore;
    }

    public void setConfidenceScore(String confidenceScore) {
        this.confidenceScore = confidenceScore;
    }

    public List<OmwLexicalEntry> getLexicalEntries() {
        return lexicalEntries;
    }

    public void setLexicalEntries(List<OmwLexicalEntry> lexicalEntries) {
        this.lexicalEntries = lexicalEntries;
    }

    public List<OmwSynset> getOmwSynsets() {
        return omwSynsets;
    }

    public void setOmwSynsets(List<OmwSynset> omwSynsets) {
        this.omwSynsets = omwSynsets;
    }

    public static class LexiconBuilder {

        private final String id;
        private final String label;
        private String language;
        private String email;
        private String license;
        private String version;
        private String url;
        private String confidenceScore;
        private String citation;
        private List<OmwSynset> omwSynsets = new ArrayList<>();
        private List<OmwLexicalEntry> lexicalEntries;

        public LexiconBuilder(String id, String label) {
            this.id = id;
            this.label = label;
        }

        public LexiconBuilder language(String language) {
            this.language = language;
            return this;
        }

        public LexiconBuilder email(String email) {
            this.email = email;
            return this;
        }

        public LexiconBuilder license(String licanse) {
            license = licanse;
            return this;
        }

        public LexiconBuilder version(String version) {
            this.version = version;
            return this;
        }

        public LexiconBuilder url(String url) {
            this.url = url;
            return this;
        }

        public LexiconBuilder synsets(Set<OmwSynset> omwSynsets) {
            this.omwSynsets.addAll(omwSynsets);
            return this;
        }

        public LexiconBuilder lexicalEntries(List<OmwLexicalEntry> lexicalEntries) {
            this.lexicalEntries = lexicalEntries;
            return this;
        }

        public LexiconBuilder citation(String citation) {
            this.citation = citation;
            return this;
        }

        public LexiconBuilder confidenceScore(String score) {
            confidenceScore = score;
            return this;
        }

        public OmwLexicon build() {
            return new OmwLexicon(this);
        }
    }

}

