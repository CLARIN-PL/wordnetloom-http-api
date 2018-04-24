package pl.edu.pwr.wordnetloom.business.dictionary.boundary;

import pl.edu.pwr.wordnetloom.business.EntityBuilder;
import pl.edu.pwr.wordnetloom.business.LinkBuilder;
import pl.edu.pwr.wordnetloom.business.dictionary.entity.*;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.stream.JsonCollectors;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.Locale;

import static javax.json.Json.createObjectBuilder;

@Path("dictionaries")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DictionaryResource {

    @Inject
    DictionaryService service;

    @Inject
    EntityBuilder entityBuilder;

    @Inject
    LinkBuilder linkBuilder;

    @Context
    UriInfo uriInfo;

    @GET
    public JsonObject getDictionaries(){
        final JsonObjectBuilder linkBuilder = createObjectBuilder();
        linkBuilder.add("_links", Json.createObjectBuilder()
                .add("aspects", this.linkBuilder.forAspects(uriInfo).toString())
                .add("domains", this.linkBuilder.forDomains(uriInfo).toString())
                .add("parts_of_speech", this.linkBuilder.forPartsOfSpeech(uriInfo).toString())
                .add("statuses", this.linkBuilder.forStatutes(uriInfo).toString())
                .add("emotions", this.linkBuilder.forEmotions(uriInfo).toString())
                .add("registers", this.linkBuilder.forRegisters(uriInfo).toString())
                .add("valuations", this.linkBuilder.forValuations(uriInfo).toString())
                .add("markedness", this.linkBuilder.forMarkedness(uriInfo).toString())
                .build());

        return linkBuilder.build();
    }

    @GET
    @Path("statuses")
    public JsonArray getAllStatuses(@HeaderParam("Accept-Language") Locale locale) {
        return buildDictionaryArray(Status.class, "getStatus", locale);
    }

    @GET
    @Path("statuses/{id:\\d+}")
    public JsonObject getStatus(@HeaderParam("Accept-Language") Locale locale, @PathParam("id") long id) {
        return buildDictionary(id, "getStatus", locale);
    }

    @GET
    @Path("emotions")
    public JsonArray getAllEmotions(@HeaderParam("Accept-Language") Locale locale) {
        return buildDictionaryArray(Emotion.class, "getEmotion", locale);
    }

    @GET
    @Path("emotions/{id:\\d+}")
    public JsonObject getEmotion(@HeaderParam("Accept-Language") Locale locale, @PathParam("id") long id) {
        return buildDictionary(id, "getEmotion", locale);
    }

    @GET
    @Path("registers")
    public JsonArray getAllRegisters(@HeaderParam("Accept-Language") Locale locale) {
        return buildDictionaryArray(Register.class, "getRegister", locale);
    }

    @GET
    @Path("registers/{id:\\d+}")
    public JsonObject getRegister(@HeaderParam("Accept-Language") Locale locale, @PathParam("id") long id) {
        return buildDictionary(id, "getRegister", locale);
    }

    @GET
    @Path("valuations")
    public JsonArray getAllValuations(@HeaderParam("Accept-Language") Locale locale) {
        return buildDictionaryArray(Valuation.class, "getValuation", locale);
    }

    @GET
    @Path("valuations/{id:\\d+}")
    public JsonObject getValuation(@HeaderParam("Accept-Language") Locale locale, @PathParam("id") long id) {
        return buildDictionary(id, "getValuation", locale);
    }

    @GET
    @Path("markedness")
    public JsonArray getAllMarkedness(@HeaderParam("Accept-Language") Locale locale) {
        return buildDictionaryArray(Markedness.class, "getMarkedness", locale);
    }

    @GET
    @Path("markedness/{id:\\d+}")
    public JsonObject getMarkedness(@HeaderParam("Accept-Language") Locale locale, @PathParam("id") long id) {
        return buildDictionary(id, "getMarkedness", locale);
    }

    @GET
    @Path("aspects")
    public JsonArray getAllAspects(@HeaderParam("Accept-Language") Locale locale) {
        return buildDictionaryArray(Aspect.class, "getAspect", locale);
    }

    @GET
    @Path("aspects/{id:\\d+}")
    public JsonObject getAspect(@HeaderParam("Accept-Language") Locale locale, @PathParam("id") long id) {
        return buildDictionary(id, "getAspect", locale);
    }

    @GET
    @Path("domains")
    public JsonArray getAllDomains(@HeaderParam("Accept-Language") Locale locale) {
        return service.findAllDomains()
                .stream()
                .map(d -> entityBuilder.buildDomain(d, linkBuilder.forDomain(d, uriInfo), locale))
                .collect(JsonCollectors.toJsonArray());
    }

    @GET
    @Path("domains/{id:\\d+}")
    public JsonObject getDomain(@HeaderParam("Accept-Language") Locale locale, @PathParam("id") long id) {
        return service.findDomain(id)
                .map(d -> entityBuilder.buildDomain(d, linkBuilder.forDomain(d, uriInfo), locale))
                .orElse(Json.createObjectBuilder().build());

    }

    @GET
    @Path("parts-of-speech/{id:\\d+}")
    public JsonObject getPartOfSpeech(@HeaderParam("Accept-Language") Locale locale, @PathParam("id") long id) {
        return service.findPartsOfSpeech(id)
                .map(p -> entityBuilder.buildPartOfSpeech(p,  linkBuilder.forPartOfSpeech(p, uriInfo), locale))
                .orElse(Json.createObjectBuilder().build());
    }

    @GET
    @Path("parts-of-speech")
    public JsonArray getAllPartsOfSpeech(@HeaderParam("Accept-Language") Locale locale) {
        return service.findAllPartsOfSpeech()
                .stream()
                .map(p -> entityBuilder.buildPartOfSpeech(p,  linkBuilder.forPartOfSpeech(p, uriInfo), locale))
                .collect(JsonCollectors.toJsonArray());
    }

    private <T> JsonArray buildDictionaryArray(final Class<T> clazz, String methodName, final Locale locale) {
        return service.findDictionaryByClass(clazz)
                .stream()
                .map(d -> entityBuilder.buildDictionary(d, linkBuilder.forDictionary(d, methodName, uriInfo), locale))
                .collect(JsonCollectors.toJsonArray());

    }

    private JsonObject buildDictionary(long id, String methodName, final Locale locale) {
         return service.findDictionaryById(id)
                .map(d -> entityBuilder.buildDictionary(d, linkBuilder.forDictionary(d, methodName, uriInfo), locale))
                .orElse(Json.createObjectBuilder().build());

    }
}
