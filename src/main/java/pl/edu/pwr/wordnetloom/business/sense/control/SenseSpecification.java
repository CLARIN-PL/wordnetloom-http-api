package pl.edu.pwr.wordnetloom.business.sense.control;

import pl.edu.pwr.wordnetloom.business.search.entity.SearchFilter;
import pl.edu.pwr.wordnetloom.business.sense.enity.*;
import pl.edu.pwr.wordnetloom.business.synset.entity.Synset;
import pl.edu.pwr.wordnetloom.business.synset.entity.SynsetRelation;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SenseSpecification {

    public static Specification<Sense> byFilter(SearchFilter filter) {

        return (root, query, cb) -> {

            List<Predicate> criteria = new ArrayList<>();

            if (filter.getLemma() != null && !filter.getLemma().isEmpty()) {
                criteria.add(byLemmaLike(filter.getLemma()).toPredicate(root, query, cb));
            }

            if (filter.getPartOfSpeechId() != null) {
                criteria.add(byPartOfSpeech(filter.getPartOfSpeechId()).toPredicate(root, query, cb));
            }

            if (filter.getDomainId() != null) {
                criteria.add(byDomain(filter.getDomainId()).toPredicate(root, query, cb));
            }

            if (filter.getLexicon() != null) {
                criteria.add(byLexiconId(filter.getLexicon()).toPredicate(root, query, cb));
            }

            if (filter.getRelationTypeId() != null) {
                if (filter.getSynsetMode()) {
                    criteria.addAll(bySynsetRelationType(filter.getRelationTypeId(), root, cb));
                } else {
                    criteria.addAll(bySenseRelationType(filter.getRelationTypeId(), root, cb));
                }
            }

            if (filter.getSynsetId() != null) {
                criteria.add(bySynsetId(filter.getSynsetId()).toPredicate(root, query, cb));
            }

            Predicate attributes = filterSenseAttributes(filter, root, cb);
            if (attributes != null) criteria.add(attributes);

            return cb.and(criteria.toArray(new Predicate[criteria.size()]));
        };
    }

    public static Predicate filterSenseAttributes(SearchFilter filter, Root<Sense> root, CriteriaBuilder cb) {

        if (filter.getRegisterId() != null || filter.getComment() != null || filter.getExample() != null) {

            CriteriaQuery<Long> query = cb.createQuery(Long.class);
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<SenseAttributes> senseAttributesRoot = subquery.from(SenseAttributes.class);
            subquery.select(senseAttributesRoot.get("sense").get("id"));

            List<Predicate> predicates = new ArrayList<>();

            if (filter.getRegisterId() != null) {
                Predicate senseAttributesPredicate = cb.equal(senseAttributesRoot.get("register"), filter.getRegisterId());
                predicates.add(senseAttributesPredicate);
            }

            if (filter.getComment() != null && !filter.getComment().isEmpty()) {
                Predicate commentPredicate = cb.like(senseAttributesRoot.get("comment"), "%" + filter.getComment() + "%");
                predicates.add(commentPredicate);
            }

            if (filter.getExample() != null) {
                Join<SenseAttributes, SenseExample> senseExampleJoin = senseAttributesRoot.join("examples");
                Predicate examplePredicate = cb.like(senseExampleJoin.get("examples"), filter.getExample());
                predicates.add(examplePredicate);
            }

            subquery.where(cb.and(predicates.toArray(new Predicate[0])));
            return cb.in(root.get("id")).value(subquery);
        }
        return null;
    }

    public static List<Predicate> bySenseRelationType(Long rel, Root<Sense> root, CriteriaBuilder cb) {
        Join<Sense, SenseRelation> outgoing = root.join("outgoingRelations", JoinType.LEFT);
        Predicate outgoingRelationsPredicate = cb.equal(outgoing.get("relationType").get("id"), rel);
        return Collections.singletonList(outgoingRelationsPredicate);
    }

    public static List<Predicate> bySynsetRelationType(Long rel, Root<Sense> root, CriteriaBuilder cb) {
        Join<Sense, Synset> synset = root.join("synset", JoinType.LEFT);
        Join<Synset, SynsetRelation> outgoing = synset.join("outgoingRelations", JoinType.LEFT);
        Predicate outgoingRelationsPredicate = cb.equal(outgoing.get("relationType").get("id"), rel);
        return Collections.singletonList(outgoingRelationsPredicate);
    }

    public static Specification<Sense> byPartOfSpeech(Long posId) {
        return (root, query, cb) -> cb.equal(root.get("partOfSpeech").get("id"), posId);
    }

    public static Specification<Sense> byDomain(Long domainId) {
        return (root, query, cb) -> cb.equal(root.get("domain").get("id"), domainId);
    }

    public static Specification<Sense> byWord(Word word) {
        return (root, query, cb) -> cb.equal(root.get("word"), word);
    }

    public static Specification<Sense> byLemma(String lemma) {
        return (root, query, cb) -> cb.equal(root.get("word").get("word"), lemma);
    }

    public static Specification<Sense> byLemmaLike(String lemma) {
        return (root, query, cb) -> cb.like(root.get("word").get("word"), lemma + "%");
    }

    public static Specification<Sense> byLemmaLikeContains(String lemma) {
        if (lemma == null || lemma.isEmpty()) {
            return byLemmaLike("%");
        } else {
            return byLemmaLike("%" + lemma + "%");
        }
    }

    public static Specification<Sense> bySynsetId(Long id) {
        return (root, query, cb) -> cb.equal(root.get("synset").get("id"), id);
    }

    public static Specification<Sense> byVarinat(Integer variant) {
        return (root, query, cb) -> cb.equal(root.get("variant"), variant);
    }

    public static Specification<Sense> byLexicon(Long lexiconId) {
        return (root, query, cb) -> cb.equal(root.get("lexicon").get("id"), lexiconId);
    }

    public static Specification<Sense> byLexiconId(Long id) {
        return (root, query, cb) -> cb.equal(root.get("lexicon").get("id"), id);
    }

    public static Specification<Sense> byLexiconIds(List<Long> ids) {
        return (root, query, cb) -> root.get("lexicon").get("id").in(ids);
    }

}
