package pl.edu.pwr.wordnetloom.business.synset.boundary;

import pl.edu.pwr.wordnetloom.business.EntityBuilder;
import pl.edu.pwr.wordnetloom.business.ResourceUriBuilder;

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
import java.util.stream.Stream;

@Path("/synsets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SynsetResource {

    @Context
    UriInfo uriInfo;

    @Inject
    SynsetService service;

    @Inject
    EntityBuilder entityBuilder;

    @Inject
    ResourceUriBuilder resourceUriBuilder;

    @GET
    public void getSynsets(@HeaderParam("Accept-Language") Locale locale) {
    }


    @GET
    @Path("{id:\\d+}")
    public JsonObject getSynset(@HeaderParam("Accept-Language") Locale locale,
                                @PathParam("id") final Long id) {
        return service.findById(id)
                .map(s -> entityBuilder.buildSynset(s, service.findSynsetAttributes(id).get(), resourceUriBuilder.forSynset(s, uriInfo), uriInfo, locale))
                .orElse(Json.createObjectBuilder().build());
    }

    @GET
    @Path("{id:\\d+}/relations")
    public JsonObject getSynsetRelations(@HeaderParam("Accept-Language") Locale locale,
                                   @PathParam("id") final Long id) {
        return service.findSynsetRelations(id)
                .map(s -> entityBuilder.buildSynsetRelations(service.findSynsetHead(id).get(),s, locale))
                .orElse(Json.createObjectBuilder().build());
    }

    @GET
    @Path("{synsetId:\\d+}/examples")
    public JsonArray getSynsetExamples(@HeaderParam("Accept-Language") Locale locale,
                                       @PathParam("synsetId") final Long senseId) {
        return service.findSynsetAttributes(senseId)
                .map(s -> s.getExamples()
                        .stream()
                        .map(e -> entityBuilder.buildSynsetExample(e, resourceUriBuilder.forSynsetExample(e, uriInfo)))
                        .collect(JsonCollectors.toJsonArray()))
                .orElse(Json.createArrayBuilder().build());

    }

    @GET
    @Path("{synsetId:\\d+}/examples/{exampleId:\\d+}")
    public JsonObject getSynsetExample(@HeaderParam("Accept-Language") Locale locale,
                                       @PathParam("synsetId") final Long senseId, @PathParam("exampleId") final Long exampleId) {
        return service.findSynsetExample(exampleId)
                .map(e -> entityBuilder.buildSynsetExample(e, resourceUriBuilder.forSynsetExample(e, uriInfo)))
                .orElse(Json.createObjectBuilder().build());
    }

}