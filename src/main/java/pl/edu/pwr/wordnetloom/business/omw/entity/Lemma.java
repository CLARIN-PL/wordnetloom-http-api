package pl.edu.pwr.wordnetloom.business.omw.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Lemma {

    @XmlAttribute(required = true)
    private String writtenForm;

    @XmlAttribute(required = true)
    private String partOfSpeech;

    public Lemma() {
    }

    public Lemma(String writtenForm, String partOfSpeech) {
        this.writtenForm = writtenForm;
        this.partOfSpeech = partOfSpeech;
    }

    public String getWrittenForm() {
        return writtenForm;
    }

    public void setWrittenForm(String writtenForm) {
        this.writtenForm = writtenForm;
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
        if (!(o instanceof Lemma)) return false;

        Lemma lemma = (Lemma) o;

        if (writtenForm != null ? !writtenForm.equals(lemma.writtenForm) : lemma.writtenForm != null) return false;
        return partOfSpeech != null ? partOfSpeech.equals(lemma.partOfSpeech) : lemma.partOfSpeech == null;
    }

    @Override
    public int hashCode() {
        int result = writtenForm != null ? writtenForm.hashCode() : 0;
        result = 31 * result + (partOfSpeech != null ? partOfSpeech.hashCode() : 0);
        return result;
    }
}
