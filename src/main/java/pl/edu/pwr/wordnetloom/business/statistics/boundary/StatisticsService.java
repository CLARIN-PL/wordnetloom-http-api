package pl.edu.pwr.wordnetloom.business.statistics.boundary;

import pl.edu.pwr.wordnetloom.business.sense.enity.Sense;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.util.*;

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

    public Optional<Long> findLemmaMonosemyCountByLexiconAndPartOfSpeech(Long lexiconId,Long partOfSpeechId) {
        try {
            return Optional.of(
                    ((BigInteger) em.createNativeQuery("SELECT count(c.cnt) FROM (SELECT count(s.id) AS cnt FROM sense s " +
                            "LEFT JOIN word w ON w.id = s.word_id " +
                            "WHERE s.part_of_speech_id = ?1 AND s.lexicon_id= ?2 " +
                            "GROUP BY s.word_id " +
                            "HAVING cnt = 1) AS c")
                            .setParameter(1, partOfSpeechId)
                            .setParameter(2, lexiconId)
                    .getSingleResult()).longValue());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Optional<Long> findLemmaPolisemyCountByLexiconAndPartOfSpeech(Long lexiconId,Long partOfSpeechId) {
        try {
            return Optional.of(
                    ((BigInteger) em.createNativeQuery("SELECT count(c.cnt) FROM (SELECT count(s.id) AS cnt FROM sense s " +
                            "LEFT JOIN word w ON w.id = s.word_id " +
                            "WHERE s.part_of_speech_id = ?1 AND s.lexicon_id= ?2 " +
                            "GROUP BY s.word_id " +
                            "HAVING cnt > 1) AS c")
                            .setParameter(1, partOfSpeechId)
                            .setParameter(2, lexiconId)
                            .getSingleResult()).longValue());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Map<Integer, Float> findPercentageNumberOfSensesInSynset(Long lexiconId,Long partOfSpeechId, Long numberOfSynsets){

        Map<Integer, Float> map = new HashMap<>();

        List<Object[]> result = em.createNativeQuery(
                "SELECT c.cnt, count(c.cnt) FROM ( " +
                    "SELECT count(s.id) as cnt FROM sense s " +
                        "WHERE s.part_of_speech_id= ?1 and s.lexicon_id = ?2 " +
                        "GROUP BY s.synset_id) c " +
                    "GROUP BY c.cnt ORDER BY c.cnt ASC")
                .setParameter(1, partOfSpeechId)
                .setParameter(2, lexiconId)
                .getResultList();

        transformToMap(numberOfSynsets, map, result);
        return map;
    }

    public Map<Integer, Float> findPercentageNumberOfLemmasInSynset(Long lexiconId, Long partOfSpeechId, Long numberOfSynsets){

        Map<Integer, Float> map = new HashMap<>();

        List<Object[]> result = em.createNativeQuery(
                "SELECT c.cnt, count(c.cnt) FROM (" +
                        "SELECT count(s.word_id) AS cnt " +
                        "FROM sense s " +
                        "WHERE s.part_of_speech_id= ?1 AND s.lexicon_id= ?2 " +
                        "GROUP BY s.word_id) c " +
                        "GROUP BY c.cnt ORDER BY c.cnt ASC")
                .setParameter(1, partOfSpeechId)
                .setParameter(2, lexiconId)
                .getResultList();

        transformToMap(numberOfSynsets, map, result);
        return map;
    }

    public void findSynsetRelationsStatistics(){
        List<Object[]> result = em.createNativeQuery(
                "SELECT s1.lexicon_id, s1.part_of_speech_id, sr.synset_relation_type_id, s2.part_of_speech_id, s2.lexicon_id, count(sr.id) FROM wordnet.synset_relation sr " +
                        "LEFT JOIN sense s1 ON sr.parent_synset_id = s1.synset_id " +
                        "LEFT JOIN sense s2 ON sr.child_synset_id = s2.synset_id " +
                        "WHERE s1.synset_position = 0 AND s2.synset_position = 0 " +
                        "GROUP BY s1.part_of_speech_id, s2.part_of_speech_id, sr.synset_relation_type_id " +
                        "ORDER BY sr.synset_relation_type_id,  s1.part_of_speech_id, s2.part_of_speech_id;")
                .getResultList();
    }

    private void transformToMap(Long numberOfSynsets, Map<Integer, Float> map, List<Object[]> result) {
        result.forEach( row -> {
            Integer key = ((BigInteger) row[0]).intValue();
            Float value = ((BigInteger) row[1]).floatValue();

            if(key > 10){
                if(!map.containsKey(10)){
                    map.put(10, 0f);
                }
                map.replace(10, map.get(10)+value);
            } else {
                map.put(key, value);
            }
        });

        map.forEach((k,v)-> map.replace(k, calculatePercentage(numberOfSynsets, v)));
    }


    private float calculatePercentage(Long total, Float partial){
        if(total == 0) return 0f;
        return (100.0f * partial ) / total;
    }
}
