package pl.edu.pwr.wordnetloom.business.graph.entity;

import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.json.bind.annotation.JsonbTransient;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@JsonbPropertyOrder({"id","lex","label","pos", "rel" })
public class NodeHidden {

    private transient String position;
    private Long id;
    private Long pos;
    private Long lex;
    private List<String> rel = new ArrayList<>(2);
    private String label;

    public NodeHidden(Object position, Object target, Object firstRelation, Object secondRelation, Object label, Object domain, Object pos, Object lexicon) {
        this.position = (String) position;
        this.id = ((BigInteger) target).longValue();
        this.label = label != null ? label +" "+ domain : null;
        this.pos = ((BigInteger) pos).longValue();
        this.lex = ((BigInteger) lexicon).longValue();
        rel.add(firstRelation != null ? (String) firstRelation : null);
        rel.add(secondRelation != null ? (String) secondRelation : null);
    }

    public String getPosition() {
        return position;
    }

    public Long getId() {
        return id;
    }

    public List<String> getRel() {
        return rel;
    }

    public String getLabel() {
        return label;
    }

    public Long getPos() {
        return pos;
    }

    public Long getLex() {
        return lex;
    }
}
