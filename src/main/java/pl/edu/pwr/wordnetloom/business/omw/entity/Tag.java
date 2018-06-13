package pl.edu.pwr.wordnetloom.business.omw.entity;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Tag {

    @XmlAttribute(required = true)
    private String category;

    @XmlValue
    private String value;

    public Tag() {
    }

    public Tag(String category, String value) {
        this.category = category;
        this.value = value;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
