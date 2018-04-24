package pl.edu.pwr.wordnetloom.business.statistics.boundary;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Optional;

@Path("/statistics")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StatisticsResource {

    @Inject
    StatisticsService service;

    @GET
    public JsonObject getStatistics() {
        Optional<Long> lemmas = service.findLemmaCountByLexiconAndPartOfSpeech(1L,1L);
        Optional<Long> senses = service.findSenseCountByLexiconAndPartOfSpeech(1L,1L);
        Optional<Long> synsets = service.findSynsetsCountByLexiconAndPartOfSpeech(1L,1L);
        return Json.createObjectBuilder()
                .add("number_of_lemmas", lemmas.get())
                .add("number_of_senses",senses.get())
                .add("number_of_synsets",synsets.get())
                .build();
    }
}