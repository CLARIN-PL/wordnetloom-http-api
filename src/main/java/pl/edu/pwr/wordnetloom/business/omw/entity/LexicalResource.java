package pl.edu.pwr.wordnetloom.business.omw.entity;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "LexicalResource")
@XmlAccessorType(XmlAccessType.FIELD)

public class LexicalResource {

    @XmlAttribute(name = "xmlns:dc")
    private String xmlns = "http://purl.org/dc/elements/1.1/";

    @XmlElement(name = "Lexicon")
    private List<Lexicon> lexicon = new ArrayList<>();

    public LexicalResource() {
    }

    private LexicalResource(LexicalResourceBuilder builder) {
        lexicon = builder.lexicon;
    }

    public String getXmlns() {
        return xmlns;
    }

    public void setXmlns(String xmlns) {
        this.xmlns = xmlns;
    }

    public List<Lexicon> getLexicon() {
        return lexicon;
    }

    public void setLexicon(List<Lexicon> lexicon) {
        this.lexicon = lexicon;
    }

    public static class LexicalResourceBuilder {
        public List<Lexicon> lexicon = new ArrayList<>();

        public LexicalResourceBuilder addLexicon(Lexicon lex) {
            lexicon.add(lex);
            return this;
        }

        public LexicalResource build() {
            return new LexicalResource(this);
        }
    }
}
