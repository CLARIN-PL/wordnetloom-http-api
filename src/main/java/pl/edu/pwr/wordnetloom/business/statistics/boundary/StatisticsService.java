package pl.edu.pwr.wordnetloom.business.statistics.boundary;

import pl.edu.pwr.wordnetloom.business.sense.enity.Sense;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Stateless
public class StatisticsService {

    @PersistenceContext
    EntityManager em;

    public Optional<Long> findLemmaCountByLexiconAndPartOfSpeech(Long lexiconId,Long partOfSpeechId) {
        try {
            return Optional.of(
                    em.createNamedQuery(Sense.FIND_LEMMA_COUNT_BY_LEXICON_AND_POS, Long.class)
                            .setParameter("lexiconId", lexiconId)
                            .setParameter("posId",partOfSpeechId)
                            .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Optional<Long> findSenseCountByLexiconAndPartOfSpeech(Long lexiconId,Long partOfSpeechId) {
        try {
            return Optional.of(
                    em.createNamedQuery(Sense.FIND_SENSE_COUNT_BY_LEXICON_AND_POS, Long.class)
                            .setParameter("lexiconId", lexiconId)
                            .setParameter("posId",partOfSpeechId)
                            .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Optional<Long> findSynsetsCountByLexiconAndPartOfSpeech(Long lexiconId,Long partOfSpeechId) {
        try {
            return Optional.of(
                    em.createNamedQuery(Sense.FIND_SYNSET_COUNT_BY_LEXICON_AND_POS, Long.class)
                            .setParameter("lexiconId", lexiconId)
                            .setParameter("posId",partOfSpeechId)
                            .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
