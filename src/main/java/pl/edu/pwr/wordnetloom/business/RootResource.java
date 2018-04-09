package pl.edu.pwr.wordnetloom.business;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import static javax.json.Json.createObjectBuilder;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RootResource {

    @Inject
    ResourceUriBuilder resourceUriBuilder;

    @Context
    UriInfo uriInfo;

    @GET
    public JsonObject getRoot() {
        final JsonObjectBuilder linkBuilder = createObjectBuilder();
        linkBuilder.add("application_name", "plWordNet HTTP API Endpoint");
        linkBuilder.add("description", "plWordNet is a relational lexico-semantic dictionary which reflects the lexical system of the Polish language. plWN currently contains 191 000 nouns, verbs, adjectives, and adverbs, 285 000 word senses, and over 600 000 relations. It is now the largest wordnet in the world and is still growing.");
        linkBuilder.add("_links", Json.createObjectBuilder()
                .add("dictionaries", resourceUriBuilder.forDictionaries(uriInfo).toString())
                .add("graphs", resourceUriBuilder.forGraphs(uriInfo).toString())
                .add("lexicons", resourceUriBuilder.forLexicons(uriInfo).toString())
                .add("relation_types", resourceUriBuilder.forRelationTypes(uriInfo).toString())
                .add("search", resourceUriBuilder.forSearch(uriInfo).toString())
                .add("senses", resourceUriBuilder.forSenses(uriInfo).toString())
                .add("settings", resourceUriBuilder.forSettings(uriInfo).toString())
                .add("synsets", resourceUriBuilder.forSynsets(uriInfo).toString())
                .build());

        return linkBuilder.build();
    }
}
