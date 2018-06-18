package pl.edu.pwr.wordnetloom.business.download.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Lemma")
@XmlAccessorType(XmlAccessType.FIELD)
public class OmwLemma {

    @XmlAttribute(required = true)
    private String writtenForm;

    @XmlAttribute(required = true)
    private String partOfSpeech;

    public OmwLemma() {
    }

    public OmwLemma(String writtenForm, String partOfSpeech) {
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
        if (!(o instanceof OmwLemma)) return false;

        OmwLemma omwLemma = (OmwLemma) o;

        if (writtenForm != null ? !writtenForm.equals(omwLemma.writtenForm) : omwLemma.writtenForm != null) return false;
        return partOfSpeech != null ? partOfSpeech.equals(omwLemma.partOfSpeech) : omwLemma.partOfSpeech == null;
    }

    @Override
    public int hashCode() {
        int result = writtenForm != null ? writtenForm.hashCode() : 0;
        result = 31 * result + (partOfSpeech != null ? partOfSpeech.hashCode() : 0);
        return result;
    }
}
