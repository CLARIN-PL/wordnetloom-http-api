package pl.edu.pwr.wordnetloom.business.download.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "SenseRelation")
@XmlAccessorType(XmlAccessType.FIELD)
public class OmwSenseRelation {

    @XmlAttribute(required = true)
    private String target;

    @XmlAttribute(required = true)
    private String relType;

    @XmlAttribute(name = "dc:type", required = false)
    private String type;

    @XmlAttribute(name = "dc:description", required = false)
    private String desc;

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getRelType() {
        return relType;
    }

    public void setRelType(String relType) {
        this.relType = relType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OmwSenseRelation)) return false;

        OmwSenseRelation that = (OmwSenseRelation) o;

        if (target != null ? !target.equals(that.target) : that.target != null) return false;
        if (relType != null ? !relType.equals(that.relType) : that.relType != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        return desc != null ? desc.equals(that.desc) : that.desc == null;
    }

    @Override
    public int hashCode() {
        int result = target != null ? target.hashCode() : 0;
        result = 31 * result + (relType != null ? relType.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (desc != null ? desc.hashCode() : 0);
        return result;
    }
}
