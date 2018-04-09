package pl.edu.pwr.wordnetloom.business.dictionary.boundary;

import pl.edu.pwr.wordnetloom.business.dictionary.entity.Dictionary;
import pl.edu.pwr.wordnetloom.business.dictionary.entity.Domain;
import pl.edu.pwr.wordnetloom.business.dictionary.entity.PartOfSpeech;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;


@Stateless
public class DictionaryService {

    @PersistenceContext
    EntityManager em;

    public <T> List<Dictionary> findDictionaryByClass(Class<T> clazz) {
        return em.createNamedQuery(Dictionary.FIND_ALL_BY_TYPE, Dictionary.class)
                .setParameter("type", clazz)
                .getResultList();
    }

    public Optional<Dictionary> findDictionaryById(long id) {
        try {
            return Optional.of(
                    em.createNamedQuery(Dictionary.FIND_BY_ID, Dictionary.class)
                            .setParameter("id", id)
                            .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public List<Domain> findAllDomains() {
        return em.createNamedQuery(Domain.FIND_ALL, Domain.class)
                 .getResultList();
    }

    public Optional<Domain> findDomain(long id) {
        try {
            return Optional.of(
                    em.createNamedQuery(Domain.FIND_BY_ID, Domain.class)
                            .setParameter("id", id)
                            .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public List<PartOfSpeech> findAllPartsOfSpeech() {
        return em.createNamedQuery(PartOfSpeech.FIND_ALL, PartOfSpeech.class).getResultList();
    }

    public Optional<PartOfSpeech> findPartsOfSpeech(long id) {
        try {
            return Optional.of(
                    em.createNamedQuery(PartOfSpeech.FIND_BY_ID, PartOfSpeech.class)
                            .setParameter("id", id)
                            .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
