package pl.edu.pwr.wordnetloom.business.lexicon.boundary;

import pl.edu.pwr.wordnetloom.business.EntityBuilder;
import pl.edu.pwr.wordnetloom.business.LinkBuilder;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.stream.JsonCollectors;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.Locale;

@Path("/lexicons")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LexiconResource {

    @Inject
    LexiconService service;

    @Inject
    EntityBuilder entityBuilder;

    @Inject
    LinkBuilder linkBuilder;

    @Context
    UriInfo uriInfo;

    @GET
    public JsonArray getAllLexicons(@HeaderParam("Accept-Language") Locale locale) {
        return  service
                .findAll()
                .stream()
                .map(l -> entityBuilder.buildLexicon(l, linkBuilder.forLexicon(l, uriInfo)))
                .collect(JsonCollectors.toJsonArray());
    }

    @GET
    @Path("{id:\\d+}")
    public JsonObject getLexicon(@HeaderParam("Accept-Language") Locale locale, @PathParam("id") long id) {
        return service.findById(id)
                .map(l -> entityBuilder.buildLexicon(l, linkBuilder.forLexicon(l, uriInfo)))
                .orElse(Json.createObjectBuilder().build());
    }

}
