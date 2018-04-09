package pl.edu.pwr.wordnetloom.business.lexicon.boundary;

import pl.edu.pwr.wordnetloom.business.lexicon.entity.Lexicon;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Stateless
public class LexiconService {

    @PersistenceContext
    EntityManager em;

    public List<Lexicon> findAll() {
        return em.createNamedQuery(Lexicon.FIND_ALL, Lexicon.class)
                .getResultList();
    }

    public Optional<Lexicon> findById(long id) {
        try {
            return Optional.of(
                    em.createNamedQuery(Lexicon.FIND_BY_ID, Lexicon.class)
                            .setParameter("id", id)
                            .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
