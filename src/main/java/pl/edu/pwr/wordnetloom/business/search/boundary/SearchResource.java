package pl.edu.pwr.wordnetloom.business.search.boundary;

import pl.edu.pwr.wordnetloom.business.EntityBuilder;
import pl.edu.pwr.wordnetloom.business.search.entity.SearchFilter;
import pl.edu.pwr.wordnetloom.business.search.entity.SearchFilterExtractorFromUrl;
import pl.edu.pwr.wordnetloom.business.sense.boundary.SenseService;
import pl.edu.pwr.wordnetloom.business.sense.enity.Sense;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.Locale;

@Path("/search")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SearchResource {

    @Inject
    SenseService service;

    @Inject
    EntityBuilder entityBuilder;

    @GET
    public JsonObject filter(@HeaderParam("Accept-Language") Locale locale, @Context UriInfo uriInfo) {

        final SearchFilter searchFilter = new SearchFilterExtractorFromUrl(uriInfo).getFilter();
        Long count = service.countWithFilter(searchFilter);
        List<Sense> senses = service.findByFilter(searchFilter);
        return entityBuilder.buildPaginatedSenseShort(senses, count, uriInfo, locale);

    }
}
