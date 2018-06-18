package pl.edu.pwr.wordnetloom.business.download.entity;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "Definition")
@XmlAccessorType(XmlAccessType.FIELD)
public class OmwDefinition {

    @XmlAttribute
    private String language;

    @XmlAttribute
    private String sourceSense;

    @XmlValue
    private String value;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSourceSense() {
        return sourceSense;
    }

    public void setSourceSense(String sourceSense) {
        this.sourceSense = sourceSense;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OmwDefinition)) return false;

        OmwDefinition that = (OmwDefinition) o;

        if (language != null ? !language.equals(that.language) : that.language != null) return false;
        if (sourceSense != null ? !sourceSense.equals(that.sourceSense) : that.sourceSense != null) return false;
        return value != null ? value.equals(that.value) : that.value == null;
    }

    @Override
    public int hashCode() {
        int result = language != null ? language.hashCode() : 0;
        result = 31 * result + (sourceSense != null ? sourceSense.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
