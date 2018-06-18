package pl.edu.pwr.wordnetloom.business.download.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "ILIDefinition")
@XmlAccessorType(XmlAccessType.FIELD)
public class OmwILIDefinition {

    @XmlValue
    private String value;

    public OmwILIDefinition() {
    }

    public OmwILIDefinition(String value) {
        this.value = value;
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
        if (!(o instanceof OmwILIDefinition)) return false;

        OmwILIDefinition that = (OmwILIDefinition) o;

        return value != null ? value.equals(that.value) : that.value == null;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
