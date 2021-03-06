package pl.edu.pwr.wordnetloom.business.download.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "Example")
@XmlAccessorType(XmlAccessType.FIELD)
public class OmwExample {

    @XmlValue
    private String value;

    public OmwExample() {
    }

    public OmwExample(String value) {
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
        if (!(o instanceof OmwExample)) return false;

        OmwExample example = (OmwExample) o;

        return value != null ? value.equals(example.value) : example.value == null;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
