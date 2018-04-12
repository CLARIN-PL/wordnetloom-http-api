package pl.edu.pwr.wordnetloom.business.synset.boundary;

import pl.edu.pwr.wordnetloom.business.synset.entity.Synset;
import pl.edu.pwr.wordnetloom.business.synset.entity.SynsetAttributes;
import pl.edu.pwr.wordnetloom.business.synset.entity.SynsetExample;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.*;

@Stateless
public class SynsetService {

    @PersistenceContext
    EntityManager em;

    public Optional<Synset> findById(Long id) {

        try {
            return Optional.of(
                    em.createNamedQuery(Synset.FIND_BY_ID_WITH_LEXICON_AND_SENSES_WITH_DOMAIN, Synset.class)
                            .setParameter("id", id)
                            .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Optional<SynsetAttributes> findSynsetAttributes(Long id) {
        try {
            return Optional.of(
                    em.createNamedQuery(SynsetAttributes.FIND_BY_ID_WITH_EXAMPLES, SynsetAttributes.class)
                            .setParameter("id", id)
                            .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Optional<SynsetExample> findSynsetExample(Long id) {
        try {
            return Optional.of(
                    em.createNamedQuery(SynsetExample.FIND_BY_ID, SynsetExample.class)
                            .setParameter("id", id)
                            .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }


    public Optional<Synset> findSynsetHead(Long id) {
        try {
            return Optional.of(
                    em.createNamedQuery(Synset.FIND_SYNSET_HEAD, Synset.class)
                            .setParameter("synsetPosition", Synset.SYNSET_HEAD_POSITION)
                            .setParameter("synsetId", id)
                            .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Optional<Map<String, List<Object[]>>> findSynsetRelations(Long id) {

        try {

            String incomingQuery = "SELECT r1.synset_relation_type_id as type, rt1.name_id as nameId, r1.id as relId, r1.child_synset_id as synsetId, concat(w.word,' ',s.variant) as lemma, dom1.name_id as domainId " +
                    "FROM synset_relation r1 " +
                    "LEFT JOIN relation_type rt1 ON r1.synset_relation_type_id = rt1.id " +
                    "LEFT JOIN sense s ON s.synset_id = r1.child_synset_id " +
                    "LEFT JOIN word w ON w.id = s.word_id " +
                    "LEFT JOIN domain dom1 ON  dom1.id = s.domain_id " +
                    "WHERE r1.parent_synset_id = ?1 AND s.synset_position = ?2 " +
                    "ORDER BY type, lemma";

            String outgoingQuery = "SELECT r1.synset_relation_type_id as type, rt1.name_id as nameId, r1.id  as relId, r1.parent_synset_id, concat(w.word,' ',s.variant) as lemma, dom1.name_id as domainId "+
                    "FROM synset_relation r1 " +
                    "LEFT JOIN relation_type rt1 ON r1.synset_relation_type_id = rt1.id " +
                    "LEFT JOIN sense s ON s.synset_id = r1.parent_synset_id " +
                    "LEFT JOIN word w ON w.id = s.word_id " +
                    "LEFT JOIN domain dom1 ON  dom1.id = s.domain_id " +
                    "WHERE r1.child_synset_id = ?1 AND s.synset_position = ?2 " +
                    "ORDER BY type, lemma";

            List<Object[]> incoming = em.createNativeQuery(incomingQuery)
                    .setParameter(1, id)
                    .setParameter(2, Synset.SYNSET_HEAD_POSITION)
                    .getResultList();

            List<Object[]> outgoing =  em.createNativeQuery(outgoingQuery)
                    .setParameter(1, id)
                    .setParameter(2, Synset.SYNSET_HEAD_POSITION)
                    .getResultList();
            Map<String, List<Object[]>> result = new HashMap<>();
            result.put("incoming", incoming);
            result.put("outgoing", outgoing);
             return Optional.of(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

}
