package pl.edu.pwr.wordnetloom.business.synset.boundary;

import pl.edu.pwr.wordnetloom.business.synset.entity.Synset;
import pl.edu.pwr.wordnetloom.business.synset.entity.SynsetAttributes;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Optional;

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

    public Optional<Synset> findSynsetRelations(Long id) {
        try {
            return Optional.of(
                    em.createNamedQuery(Synset.FIND_BY_ID_WITH_RELATIONS_AND_DOMAINS, Synset.class)
                            .setParameter("id", id)
                            .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

}
