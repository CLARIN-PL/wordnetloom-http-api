package pl.edu.pwr.wordnetloom.business.statistics.boundary;

import pl.edu.pwr.wordnetloom.business.EntityBuilder;
import pl.edu.pwr.wordnetloom.business.dictionary.boundary.DictionaryService;
import pl.edu.pwr.wordnetloom.business.lexicon.boundary.LexiconService;

import javax.inject.Inject;
import javax.json.*;
import javax.json.stream.JsonCollectors;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@Path("/statistics")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StatisticsResource {

    @Inject
    StatisticsService statisticsService;

    @Inject
    LexiconService lexiconService;

    @Inject
    DictionaryService dictionaryService;

    @Inject
    EntityBuilder entityBuilder;

    @Context
    UriInfo uriInfo;

    @GET
    public JsonArray getStatistics(@HeaderParam("Accept-Language") Locale locale) {
        JsonArrayBuilder array = Json.createArrayBuilder();
        lexiconService.findAll().forEach(lexicon -> {
            JsonObjectBuilder builder = Json.createObjectBuilder();
            builder.add("lexicon", entityBuilder.buildLexiconShort(lexicon,uriInfo));
            JsonArrayBuilder arrayStats = Json.createArrayBuilder();

            dictionaryService.findAllPartsOfSpeech().forEach(pos -> {
                JsonObjectBuilder statsBuilder = Json.createObjectBuilder();

                Optional<Long> lemmas = statisticsService.findLemmaCountByLexiconAndPartOfSpeech(lexicon.getId(),pos.getId());
                Optional<Long> senses = statisticsService.findSenseCountByLexiconAndPartOfSpeech(lexicon.getId(),pos.getId());
                Optional<Long> synsets = statisticsService.findSynsetsCountByLexiconAndPartOfSpeech(lexicon.getId(),pos.getId());
                Optional<Long> monosemy = statisticsService.findLemmaMonosemyCountByLexiconAndPartOfSpeech(lexicon.getId(),pos.getId());
                Optional<Long> polysemy = statisticsService.findLemmaPolisemyCountByLexiconAndPartOfSpeech(lexicon.getId(),pos.getId());
                Map<Integer, Float> percentageNumberOfSensesInSynset = statisticsService.findPercentageNumberOfSensesInSynset(lexicon.getId(),pos.getId(),synsets.get());
                Map<Integer, Float> percentageNumberOfLemmasInSynset = statisticsService.findPercentageNumberOfLemmasInSynset(lexicon.getId(),pos.getId(), lemmas.get());

                float avgPolysemyIncluding =  lemmas.get() !=0 ? senses.orElse(1L).floatValue() / lemmas.orElse(1l) : 0;
                float avgPolysemyExcluding =  polysemy.get() != 0 ? (senses.orElse(1L).floatValue() - monosemy.orElse(1L)) / polysemy.orElse(1L):0;

                statsBuilder.add("part_of_speech", entityBuilder.buildPartOfSpeechShort(pos,uriInfo,locale));
                statsBuilder.add("number_of_lemmas", lemmas.orElse(0L));
                statsBuilder.add("number_of_senses",senses.orElse(0L));
                statsBuilder.add("number_of_synsets",synsets.orElse(0L));
                statsBuilder.add("number_of_monosemous_lemmas",monosemy.orElse(0L));
                statsBuilder.add("number_of_polysemous_lemmas",polysemy.orElse(0L));
                statsBuilder.add("avg_polysemy_including_monosemous_lemmas", avgPolysemyIncluding);
                statsBuilder.add("avg_polysemy_excluding_monosemous_lemmas",avgPolysemyExcluding);
                statsBuilder.add("percentage_number_of_senses_in_synset", percentageNumberOfSensesInSynset
                        .entrySet()
                        .stream()
                        .map(e -> Json.createObjectBuilder().add(e.getKey().toString(), e.getValue()).build())
                        .collect(JsonCollectors.toJsonArray()));
                statsBuilder.add("percentage_number_of_lemmas_in_synset", percentageNumberOfLemmasInSynset
                        .entrySet()
                        .stream()
                        .map(e -> Json.createObjectBuilder().add(e.getKey().toString(), e.getValue()).build())
                        .collect(JsonCollectors.toJsonArray()));

                arrayStats.add(statsBuilder);

            });
            builder.add("statistics", arrayStats);
            array.add(builder);
        });

        return array.build();

    }
}