package pl.edu.pwr.wordnetloom.business.synset.entity;

import pl.edu.pwr.wordnetloom.business.dictionary.entity.Status;
import pl.edu.pwr.wordnetloom.business.lexicon.entity.Lexicon;
import pl.edu.pwr.wordnetloom.business.sense.enity.Sense;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "synset")
@NamedQuery(name = Synset.FIND_BY_ID_WITH_LEXICON_AND_SENSES_WITH_DOMAIN,
        query = "SELECT DISTINCT s " +
                "FROM Synset s " +
                "LEFT JOIN FETCH s.lexicon " +
                "LEFT JOIN FETCH s.senses l " +
                "LEFT JOIN FETCH l.word " +
                "LEFT JOIN FETCH l.domain " +
                "LEFT JOIN FETCH l.partOfSpeech " +
                "WHERE s.id = :id")

@NamedQuery(name = Synset.FIND_SYNSET_HEAD,
        query = "SELECT s FROM Synset s " +
                "LEFT JOIN FETCH s.senses se " +
                "LEFT JOIN FETCH se.word w " +
                "LEFT JOIN FETCH se.domain d " +
                "WHERE se.synsetPosition = :synsetPosition " +
                "and s.id = :synsetId"
)
public class Synset implements Serializable {

    public static final String FIND_SYNSET_HEAD = "Synset.finaSynsetHead";
    public static final String FIND_BY_ID_WITH_LEXICON_AND_SENSES_WITH_DOMAIN = "Synset.findByIdWithLexiconAndSensesWithDomain";

    public static final int SYNSET_HEAD_POSITION = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "split")
    private Integer split = 1;

    @OneToMany(mappedBy = "synset", fetch = FetchType.LAZY)
    @OrderBy("synsetPosition")
    private Set<Sense> senses = new HashSet<>();

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lexicon_id", referencedColumnName = "id", nullable = false)
    private Lexicon lexicon;

    @Basic
    @Column(name = "abstract")
    private Boolean isAbstract = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private Status status;

    @OneToMany(mappedBy = "child", fetch = FetchType.LAZY)
    private Set<SynsetRelation> incomingRelations = new HashSet<>();

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private Set<SynsetRelation> outgoingRelations = new HashSet<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getSplit() {
        return split;
    }

    public void setSplit(Integer split) {
        this.split = split;
    }

    public Set<Sense> getSenses() {
        return senses;
    }

    public void setSenses(Set<Sense> senses) {
        this.senses = senses;
    }

    public Lexicon getLexicon() {
        return lexicon;
    }

    public void setLexicon(Lexicon lexicon) {
        this.lexicon = lexicon;
    }

    public Set<SynsetRelation> getIncomingRelations() {
        return incomingRelations;
    }

    public void setIncomingRelations(Set<SynsetRelation> incomingRelations) {
        this.incomingRelations = incomingRelations;
    }

    public Set<SynsetRelation> getOutgoingRelations() {
        return outgoingRelations;
    }

    public void setOutgoingRelations(Set<SynsetRelation> outgoingRelations) {
        this.outgoingRelations = outgoingRelations;
    }

    public Boolean getAbstract() {
        return isAbstract;
    }

    public void setAbstract(Boolean anAbstract) {
        isAbstract = anAbstract;
    }
}
