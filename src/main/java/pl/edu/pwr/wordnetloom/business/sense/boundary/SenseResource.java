package pl.edu.pwr.wordnetloom.business.sense.boundary;

import pl.edu.pwr.wordnetloom.business.EntityBuilder;
import pl.edu.pwr.wordnetloom.business.LinkBuilder;
import pl.edu.pwr.wordnetloom.business.search.entity.SearchFilter;
import pl.edu.pwr.wordnetloom.business.search.entity.SearchFilterExtractorFromUrl;
import pl.edu.pwr.wordnetloom.business.sense.enity.SenseAttributes;
import pl.edu.pwr.wordnetloom.business.sense.enity.SenseEmotions;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.stream.JsonCollectors;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.Locale;


@Path("senses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SenseResource {

    @Inject
    SenseService service;

    @Inject
    EntityBuilder entityBuilder;

    @Inject
    LinkBuilder linkBuilder;

    @Context
    UriInfo uriInfo;

    @GET
    public JsonObject getSenses(@HeaderParam("Accept-Language") Locale locale) {
        final SearchFilter filter = new SearchFilterExtractorFromUrl(uriInfo).getFilter();
        final long count = service.countWithFilter(filter);
        return entityBuilder.buildPaginatedSense(service.findAllPaginated(filter), count,
                filter.getPaginationData().getFirstResult(),
                filter.getPaginationData().getMaxResults(),
                uriInfo, locale);
    }

    @GET
    @Path("{id:\\d+}")
    public JsonObject getSense(@HeaderParam("Accept-Language") Locale locale,
                            @PathParam("id") final Long id) {
        final SenseAttributes attributes = service.findSenseAttributes(id).orElse(null);
        return service.findById(id)
                .map(s -> entityBuilder.buildSense(s, attributes, linkBuilder.forSense(s, uriInfo), uriInfo, locale))
                .orElse(Json.createObjectBuilder().build());
    }

    @GET
    @Path("{senseId:\\d+}/examples")
    public JsonArray getSenseExamples(@HeaderParam("Accept-Language") Locale locale,
                               @PathParam("senseId") final Long senseId) {
        return service.findSenseAttributes(senseId)
                .map(s -> s.getExamples()
                        .stream()
                        .map(e -> entityBuilder.buildSenseExample(e, linkBuilder.forSenseExample(e, uriInfo)))
                        .collect(JsonCollectors.toJsonArray()))
                .orElse(Json.createArrayBuilder().build());
    }

    @GET
    @Path("{senseId:\\d+}/examples/{exampleId:\\d+}")
    public JsonObject getSenseExample(@HeaderParam("Accept-Language") Locale locale,
                                      @PathParam("senseId") final Long senseId,  @PathParam("exampleId") final Long exampleId) {
        return service.findSenseExample(exampleId)
                .map(e -> entityBuilder.buildSenseExample(e, linkBuilder.forSenseExample(e, uriInfo)))
                .orElse(Json.createObjectBuilder().build());
    }


    @GET
    @Path("{id:\\d+}/relations")
    public JsonObject getSenseRelations(@HeaderParam("Accept-Language") Locale locale,
                              @PathParam("id") final Long id) {
        return service.findByIdWithRelations(id)
                .map(s -> entityBuilder.buildSenseRelations(s,locale))
                .orElse(Json.createObjectBuilder().build());
    }

    @GET
    @Path("{senseId:\\d+}/relations/{relationId:\\d+}")
    public JsonObject getSenseRelation(@HeaderParam("Accept-Language") Locale locale,
                                      @PathParam("senseId") final Long senseId,  @PathParam("relationId") final Long relationId) {
        return service.findById(senseId)
                .map(z -> entityBuilder.buildSenseRelation(z,service.findSenseRelation(relationId)
                        .get(),locale))
                .orElse(Json.createObjectBuilder().build());
    }


    @GET
    @Path("{id:\\d+}/emotional-annotations")
    public JsonArray getEmotionalAnnotations(@HeaderParam("Accept-Language") Locale locale,
                                 @PathParam("id") final Long id) {

        List<SenseEmotions> emotions = service.findSenseEmotions(id);
        emotions.sort((e1, e2) -> Boolean.compare(e1.isSuperAnnotation(), e2.isSuperAnnotation()));

        return emotions
                .stream()
                .map(e -> entityBuilder.buildEmotionalAnnotation(e, linkBuilder.forEmotionalAnnotation(e, uriInfo)))
                .collect(JsonCollectors.toJsonArray());
    }

    @GET
    @Path("{id:\\d+}/emotional-annotations/{annotationId:\\d+}")
    public JsonObject getEmotionalAnnotation(@HeaderParam("Accept-Language") Locale locale,
                                             @PathParam("id") final Long id, @PathParam("annotationId") final Long annotationId) {

       return service.findSenseEmotion(annotationId)
               .map(e -> entityBuilder.buildEmotionalAnnotation(e, linkBuilder.forEmotionalAnnotation(e, uriInfo)))
               .orElse(Json.createObjectBuilder().build());
    }

}