package pl.edu.pwr.wordnetloom.business.graph.boundary;

import pl.edu.pwr.wordnetloom.business.ResourceUriBuilder;
import pl.edu.pwr.wordnetloom.business.graph.entity.NodeExpanded;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Locale;

import static javax.json.Json.createObjectBuilder;

@Path("graphs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GraphsResource {

    @Inject
    GraphService service;

    @Inject
    ResourceUriBuilder resourceUriBuilder;

    @Context
    UriInfo uriInfo;

    @GET
    public JsonObject getGraphs() {
        final JsonObjectBuilder builder = createObjectBuilder();
        builder.add("_links", Json.createObjectBuilder()
                .add("senses-graph", resourceUriBuilder.forSenseGraph(22l,uriInfo).toString())
                .add("synsets-graph", resourceUriBuilder.forSynsetsGraph( 10, uriInfo).toString())
                .build());
        return builder.build();
    }

    @GET
    @Path("senses/{id:\\d+}")
    public Response getSenseGraph(@HeaderParam("Accept-Language") Locale locale,
                                  @PathParam("id") final Long id) {
        NodeExpanded node = service.senseGraph(id, locale);
        return Response.ok().entity(JsonbBuilder.create().toJson(node)).build();
    }

    @GET
    @Path("synsets/{id:\\d+}")
    public Response getSynsetGraph(@HeaderParam("Accept-Language") Locale locale,
                                    @PathParam("id") final Long id) {
        NodeExpanded node = service.synsetGraph(id, locale);
        return Response.ok().entity(JsonbBuilder.create().toJson(node)).build();
    }
}
