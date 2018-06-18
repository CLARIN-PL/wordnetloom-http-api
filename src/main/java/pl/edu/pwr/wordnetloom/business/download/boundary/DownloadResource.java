package pl.edu.pwr.wordnetloom.business.download.boundary;

import pl.edu.pwr.wordnetloom.business.EntityBuilder;
import pl.edu.pwr.wordnetloom.business.LinkBuilder;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.File;
import java.util.Locale;

@Path("/downloads")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DownloadResource {

    @Inject
    EntityBuilder entityBuilder;

    @Inject
    LinkBuilder linkBuilder;

    @Context
    UriInfo uriInfo;

    @Inject
    DownloadService downloadService;

    @GET
    public JsonArray getAllDownloads(@HeaderParam("Accept-Language") Locale locale) {
        return Json.createArrayBuilder().build();
    }

    @GET
    @Path("/enwordnet")
    @Produces("application/xml")
    public Response getFile() {
        File file = downloadService.buildEnWordnetFile();
        Response.ResponseBuilder response = Response.ok((Object) file);
        response.header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
        return response.build();
    }
}
