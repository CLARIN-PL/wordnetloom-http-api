package pl.edu.pwr.wordnetloom.business.download.entity;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "LexicalResource")
@XmlAccessorType(XmlAccessType.FIELD)

public class OmwLexicalResource {

    @XmlAttribute(name = "xmlns:dc")
    private String xmlns = "http://purl.org/dc/elements/1.1/";

    @XmlElement(name = "Lexicon")
    private List<OmwLexicon> omwLexicon = new ArrayList<>();

    public OmwLexicalResource() {
    }

    private OmwLexicalResource(LexicalResourceBuilder builder) {
        omwLexicon = builder.omwLexicon;
    }

    public String getXmlns() {
        return xmlns;
    }

    public void setXmlns(String xmlns) {
        this.xmlns = xmlns;
    }

    public List<OmwLexicon> getOmwLexicon() {
        return omwLexicon;
    }

    public void setOmwLexicon(List<OmwLexicon> omwLexicon) {
        this.omwLexicon = omwLexicon;
    }

    public static class LexicalResourceBuilder {
        public List<OmwLexicon> omwLexicon = new ArrayList<>();

        public LexicalResourceBuilder addLexicon(OmwLexicon lex) {
            omwLexicon.add(lex);
            return this;
        }

        public OmwLexicalResource build() {
            return new OmwLexicalResource(this);
        }
    }
}
