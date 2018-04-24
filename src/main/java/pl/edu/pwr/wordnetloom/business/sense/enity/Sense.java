package pl.edu.pwr.wordnetloom.business.sense.enity;

import pl.edu.pwr.wordnetloom.business.dictionary.entity.Status;
import pl.edu.pwr.wordnetloom.business.dictionary.entity.Domain;
import pl.edu.pwr.wordnetloom.business.lexicon.entity.Lexicon;
import pl.edu.pwr.wordnetloom.business.dictionary.entity.PartOfSpeech;
import pl.edu.pwr.wordnetloom.business.synset.entity.Synset;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sense")
@NamedQuery(name = Sense.FIND_BY_ID_WITH_RELATIONS_AND_DOMAINS,
        query = "SELECT DISTINCT s  FROM Sense s LEFT JOIN FETCH s.word " +
                "LEFT JOIN FETCH s.domain " +
                "LEFT JOIN FETCH s.incomingRelations inr " +
                "LEFT JOIN FETCH s.outgoingRelations outr " +
                "LEFT JOIN FETCH inr.parent p " +
                "LEFT JOIN FETCH outr.child c " +
                "LEFT JOIN FETCH p.domain  " +
                "LEFT JOIN FETCH c.domain  WHERE s.id = :id")

@NamedQuery(name = Sense.FIND_BY_ID_WITH_ATTRIBUTES,
                query = "SELECT DISTINCT s FROM Sense s LEFT JOIN FETCH s.word " +
                        "LEFT JOIN FETCH s.domain " +
                        "LEFT JOIN FETCH s.partOfSpeech " +
                        "LEFT JOIN FETCH s.lexicon " +
                        "LEFT JOIN FETCH s.synset WHERE s.id = :id")

@NamedQuery(name = Sense.FIND_ALL_WITH_ATTRIBUTES,
        query = "SELECT DISTINCT s FROM Sense s LEFT JOIN FETCH s.word LEFT JOIN FETCH s.domain " +
                "LEFT JOIN FETCH s.partOfSpeech LEFT JOIN FETCH s.lexicon LEFT JOIN FETCH s.synset")

@NamedQuery(name = Sense.FIND_BY_ID_WITH_WORD_AND_DOMAIN,
        query = "SELECT s FROM Sense s LEFT JOIN s.word w LEFT JOIN s.domain d  WHERE s.id = :id ")

@NamedQuery(name = Sense.FIND_LEMMA_COUNT_BY_LEXICON_AND_POS,
        query="SELECT COUNT(DISTINCT w.word) FROM Sense s " +
                "LEFT JOIN s.word w "+
                "WHERE s.lexicon.id = :lexiconId AND s.partOfSpeech.id = :posId")

@NamedQuery(name = Sense.FIND_SENSE_COUNT_BY_LEXICON_AND_POS,
        query="SELECT COUNT(s.id)FROM Sense s " +
                "LEFT JOIN s.word w "+
                "WHERE s.lexicon.id = :lexiconId AND s.partOfSpeech.id = :posId")

@NamedQuery(name = Sense.FIND_SYNSET_COUNT_BY_LEXICON_AND_POS,
        query="SELECT COUNT(DISTINCT syn.id)FROM Sense s " +
                "LEFT JOIN s.synset syn "+
                "WHERE s.lexicon.id = :lexiconId AND s.partOfSpeech.id = :posId")
public class Sense implements Serializable {

    public static final String FIND_BY_ID_WITH_ATTRIBUTES = "Sense.findByIdWithAttributes";
    public static final String FIND_ALL_WITH_ATTRIBUTES = "Sense.findAllWithAttributes";
    public static final String FIND_BY_ID_WITH_RELATIONS_AND_DOMAINS = "Sense.findByIdWithRelationsAndDomains";
    public static final String FIND_BY_ID_WITH_WORD_AND_DOMAIN = "Sense.findByIdWithWordAndDomain";
    public static final String FIND_LEMMA_COUNT_BY_LEXICON_AND_POS = "Sense.findLemmaCountByLexiconIdAndPos";
    public static final String FIND_SENSE_COUNT_BY_LEXICON_AND_POS = "Sense.findSenseCountByLexiconIdAndPos";
    public static final String FIND_SYNSET_COUNT_BY_LEXICON_AND_POS = "Sense.findSynsetCountByLexiconIdAndPos";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "domain_id", referencedColumnName = "id", nullable = false)
    private Domain domain;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "word_id", referencedColumnName = "id", nullable = false)
    private Word word;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "part_of_speech_id", referencedColumnName = "id", nullable = false)
    private PartOfSpeech partOfSpeech;

    @NotNull
    @Column(name = "variant", nullable = false, columnDefinition = "int default 1")
    private Integer variant = 1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "synset_id", referencedColumnName = "id")
    private Synset synset;

    @Column(name = "synset_position", columnDefinition = "int default 0")
    private Integer synsetPosition = 0;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lexicon_id", referencedColumnName = "id", nullable = false)
    private Lexicon lexicon;

    @OneToMany(mappedBy = "child", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final Set<SenseRelation> incomingRelations = new HashSet<>();

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final Set<SenseRelation> outgoingRelations = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private Status status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public PartOfSpeech getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(PartOfSpeech partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public Lexicon getLexicon() {
        return lexicon;
    }

    public void setLexicon(Lexicon lexicon) {
        this.lexicon = lexicon;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public Integer getVariant() {
        return variant;
    }

    public void setVariant(Integer variant) {
        this.variant = variant;
    }

    public Synset getSynset() {
        return synset;
    }

    public void setSynset(Synset synset) {
        this.synset = synset;
    }

    public Integer getSynsetPosition() {
        return synsetPosition;
    }

    public void setSynsetPosition(Integer synsetPposition) {
        synsetPosition = synsetPposition;
    }

    public Set<SenseRelation> getIncomingRelations() {
        return incomingRelations;
    }

    public Set<SenseRelation> getOutgoingRelations() {
        return outgoingRelations;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Sense sense = (Sense) o;

        if (id != sense.id) {
            return false;
        }
        if (!word.equals(sense.word)) {
            return false;
        }
        if (!partOfSpeech.equals(sense.partOfSpeech)) {
            return false;
        }
        return variant.equals(sense.variant);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + word.hashCode();
        result = 31 * result + partOfSpeech.hashCode();
        result = 31 * result + variant.hashCode();
        return result;
    }
}