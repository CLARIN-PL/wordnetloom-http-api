package pl.edu.pwr.wordnetloom.business.relationtype.boundary;

import pl.edu.pwr.wordnetloom.business.EntityBuilder;
import pl.edu.pwr.wordnetloom.business.ResourceUriBuilder;
import pl.edu.pwr.wordnetloom.business.relationtype.entity.RelationType;

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

@Path("/relation-types")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RelationTypeResource {

    @Inject
    RelationTypeService service;

    @Inject
    EntityBuilder entityBuilder;

    @Inject
    ResourceUriBuilder resourceUriBuilder;

    @Context
    UriInfo uriInfo;

    @GET
    public JsonArray getRelationTypes(@HeaderParam("Accept-Language") Locale locale) {
        return service.findAllRelationTypes()
                .stream()
                .map(rt -> entityBuilder.buildRelationType(rt, resourceUriBuilder.forRelationType(rt, uriInfo),
                        resourceUriBuilder.forRelationTests(rt,uriInfo), uriInfo, locale))
                .collect(JsonCollectors.toJsonArray());

    }

    @GET
    @Path("{id:\\d+}")
    public JsonObject getRelationType(@HeaderParam("Accept-Language") Locale locale, @PathParam("id") long id) {
        return service.findRelationTypeById(id)
                .map(rt -> entityBuilder.buildRelationType(rt, resourceUriBuilder.forRelationType(rt, uriInfo),
                        resourceUriBuilder.forRelationTests(rt, uriInfo),uriInfo, locale))
                .orElse(Json.createObjectBuilder().build());

    }

    @GET
    @Path("{id:\\d+}/tests")
    public JsonArray getRelationTests(@HeaderParam("Accept-Language") Locale locale, @PathParam("id") long relId) {
        return service.findAllRelationTests(relId)
                .stream()
                .map(rt -> entityBuilder.buildRelationTest(rt, resourceUriBuilder.forRelationTest(rt, uriInfo), locale))
                .collect(JsonCollectors.toJsonArray());

    }

    @GET
    @Path("{id:\\d+}/tests/{testId:\\d+}")
    public JsonObject getRelationTest(@HeaderParam("Accept-Language") Locale locale, @PathParam("id") long relId, @PathParam("testId") long testId) {
        return service.findRelationTest(relId, testId)
                .map(rt -> entityBuilder.buildRelationTest(rt, resourceUriBuilder.forRelationTest(rt, uriInfo), locale))
                .orElse(Json.createObjectBuilder().build());

    }
}
