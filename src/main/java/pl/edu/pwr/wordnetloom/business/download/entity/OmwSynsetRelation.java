package pl.edu.pwr.wordnetloom.business.download.entity;


import javax.xml.bind.annotation.*;

@XmlRootElement(name = "SynsetRelation")
@XmlAccessorType(XmlAccessType.FIELD)
public class OmwSynsetRelation {

    @XmlAttribute(required = true)
    private String target;

    @XmlAttribute(required = true)
    private String relType;

    @XmlAttribute(name = "dc:type")
    private String type;

    @XmlAttribute(name = "dc:description")
    private String desc;

    @XmlTransient
    private String parent;

    public OmwSynsetRelation() {
    }

    public OmwSynsetRelation(String parent, String target, String relType) {
        this.parent = parent;
        this.target = target;
        this.relType = relType;
    }

    public String getParent() {
        return parent;
    }

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
        if (!(o instanceof OmwSynsetRelation)) return false;

        OmwSynsetRelation that = (OmwSynsetRelation) o;

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
