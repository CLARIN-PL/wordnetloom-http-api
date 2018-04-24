package pl.edu.pwr.wordnetloom.business.settings.boundary;

import pl.edu.pwr.wordnetloom.business.EntityBuilder;
import pl.edu.pwr.wordnetloom.business.dictionary.entity.PartOfSpeech;
import pl.edu.pwr.wordnetloom.business.lexicon.entity.Lexicon;
import pl.edu.pwr.wordnetloom.business.relationtype.entity.RelationType;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Locale;

@Path("/settings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SettingsResource {

    @PersistenceContext
    EntityManager em;

    @Inject
    EntityBuilder entityBuilder;

    @GET
    public JsonObject settings(@HeaderParam("Accept-Language") Locale locale) {

        List<PartOfSpeech> pos = em.createNamedQuery(PartOfSpeech.FIND_ALL, PartOfSpeech.class)
                .getResultList();

        List<RelationType> rt = em.createNamedQuery(RelationType.FIND_ALL, RelationType.class)
                .getResultList();

        List<Lexicon> lex = em.createNamedQuery(Lexicon.FIND_ALL, Lexicon.class)
                .getResultList();

        return Json.createObjectBuilder()
                .add("relations", entityBuilder.buildRelationTypeSettings(rt, locale))
                .add("partsOfSpeech", entityBuilder.buildPartOfSpeechSettings(pos))
                .add("lexicons", entityBuilder.buildLexiconSettings(lex))
                .build();
    }
}
