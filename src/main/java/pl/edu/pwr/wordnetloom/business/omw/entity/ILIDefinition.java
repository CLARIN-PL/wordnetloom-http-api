package pl.edu.pwr.wordnetloom.business.omw.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "ILIDefinition")
@XmlAccessorType(XmlAccessType.FIELD)
public class ILIDefinition {

    @XmlValue
    private String value;

    public ILIDefinition() {
    }

    public ILIDefinition(String value) {
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
        if (!(o instanceof ILIDefinition)) return false;

        ILIDefinition that = (ILIDefinition) o;

        return value != null ? value.equals(that.value) : that.value == null;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
