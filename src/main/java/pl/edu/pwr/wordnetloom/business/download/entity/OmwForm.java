package pl.edu.pwr.wordnetloom.business.download.entity;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Form")
public class OmwForm {

    @XmlAttribute(required = true)
    private String writtenForm;

    private String script;

    @XmlElement(name = "Tag")
    private OmwTag omwTag;

    public OmwForm() {
    }

    public OmwForm(String writtenForm, OmwTag omwTag) {
        this.writtenForm = writtenForm;
        this.omwTag = omwTag;
    }

    public String getWrittenForm() {
        return writtenForm;
    }

    public void setWrittenForm(String writtenForm) {
        this.writtenForm = writtenForm;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public OmwTag getOmwTag() {
        return omwTag;
    }

    public void setOmwTag(OmwTag omwTag) {
        this.omwTag = omwTag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OmwForm)) return false;

        OmwForm omwForm = (OmwForm) o;

        if (writtenForm != null ? !writtenForm.equals(omwForm.writtenForm) : omwForm.writtenForm != null) return false;
        if (script != null ? !script.equals(omwForm.script) : omwForm.script != null) return false;
        return omwTag != null ? omwTag.equals(omwForm.omwTag) : omwForm.omwTag == null;
    }

    @Override
    public int hashCode() {
        int result = writtenForm != null ? writtenForm.hashCode() : 0;
        result = 31 * result + (script != null ? script.hashCode() : 0);
        result = 31 * result + (omwTag != null ? omwTag.hashCode() : 0);
        return result;
    }
}
