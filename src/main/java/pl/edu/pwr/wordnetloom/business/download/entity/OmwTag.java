package pl.edu.pwr.wordnetloom.business.download.entity;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "Tag")
@XmlAccessorType(XmlAccessType.FIELD)
public class OmwTag {

    @XmlAttribute(required = true)
    private String category;

    @XmlValue
    private String value;

    public OmwTag() {
    }

    public OmwTag(String category, String value) {
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
