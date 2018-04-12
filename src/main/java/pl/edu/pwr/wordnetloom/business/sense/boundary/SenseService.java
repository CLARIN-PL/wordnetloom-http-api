package pl.edu.pwr.wordnetloom.business.sense.boundary;

import pl.edu.pwr.wordnetloom.business.search.entity.SearchFilter;
import pl.edu.pwr.wordnetloom.business.sense.control.SenseSpecification;
import pl.edu.pwr.wordnetloom.business.sense.enity.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.logging.Logger;

@Stateless
public class SenseService {

    @PersistenceContext
    EntityManager em;

    public List<Sense> findByFilter(SearchFilter filter) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Sense> qc = cb.createQuery(Sense.class);

        Root<Sense> sense = qc.from(Sense.class);
        sense.fetch("domain");
        sense.fetch("partOfSpeech");
        sense.fetch("lexicon");
        sense.fetch("synset");
        sense.fetch("word");

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(SenseSpecification.byFilter(filter).toPredicate(sense, qc, cb));

        List<Order> orders = new ArrayList<>();
        orders.add(cb.asc(sense.get("word").get("word")));
        orders.add(cb.asc(sense.get("partOfSpeech")));
        orders.add(cb.asc(sense.get("variant")));
        orders.add(cb.asc(sense.get("lexicon")));

        qc.select(sense);
        qc.orderBy(orders);
        qc.where(predicates.toArray(new Predicate[predicates.size()]));

        TypedQuery<Sense> q = em.createQuery(qc);

        return q.setFirstResult(filter.getPaginationData().getFirstResult())
                .setMaxResults(filter.getPaginationData().getMaxResults())
                .getResultList();
    }

    public long countWithFilter(SearchFilter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);

        Root<Sense> sense = cq.from(Sense.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(SenseSpecification.byFilter(filter).toPredicate(sense, cq, cb));

        cq.select(cb.count(sense.get("id")));
        cq.where(predicates.toArray(new Predicate[predicates.size()]));
        return em.createQuery(cq).getSingleResult();
    }

    public List<Sense> findAllPaginated(SearchFilter filter) {
        return em.createNamedQuery(Sense.FIND_ALL_WITH_ATTRIBUTES, Sense.class)
                .setFirstResult(filter.getPaginationData().getFirstResult())
                .setMaxResults(filter.getPaginationData().getMaxResults())
                .getResultList();
    }

    public Optional<Sense> findById(Long id) {
        try {
            return Optional.of(
                    em.createNamedQuery(Sense.FIND_BY_ID_WITH_ATTRIBUTES, Sense.class)
                            .setParameter("id", id)
                            .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Optional<Sense> findHeadSense(Long id) {
        try {
            return Optional.of(
                    em.createNamedQuery(Sense.FIND_BY_ID_WITH_WORD_AND_DOMAIN, Sense.class)
                            .setParameter("id", id)
                            .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Optional<Sense> findByIdWithRelations(Long id) {
        try {
            return Optional.of(
                    em.createNamedQuery(Sense.FIND_BY_ID_WITH_RELATIONS_AND_DOMAINS, Sense.class)
                            .setParameter("id", id)
                            .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Optional<SenseAttributes> findSenseAttributes(Long id) {
        try {
            return Optional.of(
                    em.createNamedQuery(SenseAttributes.FIND_BY_ID, SenseAttributes.class)
                            .setParameter("id",id)
                            .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Optional<SenseExample> findSenseExample(Long id) {
        try {
            return Optional.of(
                    em.createNamedQuery(SenseExample.FIND_BY_ID, SenseExample.class)
                            .setParameter("id",id)
                            .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public List<SenseEmotions> findSenseEmotions(Long id) {
        return em.createNamedQuery(SenseEmotions.FIND_BY_SENSE_ID, SenseEmotions.class)
                .setParameter("id", id)
                .getResultList();
    }

    public Optional<SenseEmotions> findSenseEmotion(Long id){
        try {
            return Optional.of(
                    em.createNamedQuery(SenseEmotions.FIND_BY_ID, SenseEmotions.class)
                            .setParameter("id",id)
                            .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Optional<SenseRelation> findSenseRelation(Long id){
        try {
            return Optional.of(
                    em.createNamedQuery(SenseRelation.FIND_BY_ID, SenseRelation.class)
                            .setParameter("id",id)
                            .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
