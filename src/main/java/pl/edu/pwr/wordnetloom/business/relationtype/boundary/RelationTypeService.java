package pl.edu.pwr.wordnetloom.business.relationtype.boundary;

import pl.edu.pwr.wordnetloom.business.relationtype.entity.RelationTest;
import pl.edu.pwr.wordnetloom.business.relationtype.entity.RelationType;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Stateless
public class RelationTypeService {

    @PersistenceContext
    EntityManager em;

    public List<RelationType> findAllRelationTypes() {
        return em.createNamedQuery(RelationType.FIND_ALL, RelationType.class)
                .getResultList();
    }

    public Optional<RelationType> findRelationTypeById(long id) {
        try {
            return Optional.of(
                    em.createNamedQuery(RelationType.FIND_BY_ID, RelationType.class)
                            .setParameter("id", id)
                            .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public List<RelationTest> findAllRelationTests(long relationTypeId) {
        return em.createNamedQuery(RelationTest.FIND_ALL_BY_RELATION_TYPE_ID, RelationTest.class)
                .setParameter("relId", relationTypeId)
                .getResultList();
    }

    public Optional<RelationTest> findRelationTest(long relationTypeId, long testId) {
        try {
            return Optional.of(
                    em.createNamedQuery(RelationTest.FIND_BY_ID_AND_RELATION_TYPE_ID, RelationTest.class)
                            .setParameter("relId", relationTypeId)
                            .setParameter("testId", testId)
                            .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }


}
