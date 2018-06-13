package pl.edu.pwr.wordnetloom.business.omw.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "Example")
@XmlAccessorType(XmlAccessType.FIELD)
public class Example {

    @XmlValue
    private String value;

    public Example() {
    }

    public Example(String value) {
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
        if (!(o instanceof Example)) return false;

        Example example = (Example) o;

        return value != null ? value.equals(example.value) : example.value == null;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
