package pl.edu.pwr.wordnetloom.business.omw.entity;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Form {

    @XmlAttribute(required = true)
    private String writtenForm;

    private String script;

    @XmlElement(name = "Tag")
    private Tag tag;

    public Form() {
    }

    public Form(String writtenForm, Tag tag) {
        this.writtenForm = writtenForm;
        this.tag = tag;
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

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Form)) return false;

        Form form = (Form) o;

        if (writtenForm != null ? !writtenForm.equals(form.writtenForm) : form.writtenForm != null) return false;
        if (script != null ? !script.equals(form.script) : form.script != null) return false;
        return tag != null ? tag.equals(form.tag) : form.tag == null;
    }

    @Override
    public int hashCode() {
        int result = writtenForm != null ? writtenForm.hashCode() : 0;
        result = 31 * result + (script != null ? script.hashCode() : 0);
        result = 31 * result + (tag != null ? tag.hashCode() : 0);
        return result;
    }
}
