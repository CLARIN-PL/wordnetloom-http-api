package pl.edu.pwr.wordnetloom.business.download.control;

import pl.edu.pwr.wordnetloom.business.download.entity.*;
import pl.edu.pwr.wordnetloom.business.lexicon.boundary.LexiconService;
import pl.edu.pwr.wordnetloom.business.sense.boundary.SenseService;
import pl.edu.pwr.wordnetloom.business.sense.enity.SenseAttributes;
import pl.edu.pwr.wordnetloom.business.synset.boundary.SynsetService;

import javax.inject.Inject;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class EnWordnetBuilder {

    public String prefix;

    private String lexicalEntryPrefix = prefix + "-ent-";
    private String sensePrefix = prefix + "-unt-";
    private String synsetPrefix = prefix + "-syn-";
    private final AtomicInteger id = new AtomicInteger(1);
    private final Map<String, OmwSynset> synsets = new ConcurrentHashMap<>();
    private final Map<String, Set<OmwSynsetRelation>> synsetRelations = new ConcurrentHashMap<>();

    @Inject
    LexiconService lexiconService;

    @Inject
    SenseService senseService;

    @Inject
    SynsetService synsetService;

    public OmwLexicalResource buildLexicalResource() {
        return new OmwLexicalResource.LexicalResourceBuilder()
                .addLexicon(buildLexicon().orElse(null))
                .build();
    }

    private Optional<OmwLexicon> buildLexicon() {
        Long lexiconId = 3L;
        return lexiconService.findById(lexiconId)
                .map(l -> new OmwLexicon.LexiconBuilder(String.format("%swn", l.getLanguageShortcut()), l.getName())
                        .language(l.getLanguageShortcut())
                        .email(l.getEmail())
                        .license(l.getLicense())
                        .version(l.getLexiconVersion())
                        .url(l.getReferenceUrl())
                        .citation(l.getCitation())
                        .confidenceScore(l.getConfidenceScore())
                        .lexicalEntries(buildLexicalEntries(senseService.findAllSensesByLexicon(lexiconId)))
                        .synsets(new HashSet<>(synsets.values()))
                        .build());
    }

    private List<OmwLexicalEntry> buildLexicalEntries(List<SenseAttributes> senses) {
        Map<String, Map<Long, List<SenseAttributes>>> map = senses
                .stream()
                .collect(
                        Collectors.groupingBy(sa -> sa.getSense().getWord().getWord(),
                                Collectors.groupingBy(sa -> sa.getSense().getPartOfSpeech().getId())));

        return map.entrySet()
                .stream()
                .flatMap(e -> e.getValue().entrySet().stream()
                        .map(z -> buildLexicalEntry(e.getKey(), z.getKey(), z.getValue()))
                        .collect(Collectors.toList()).stream())
                .collect(Collectors.toList());
    }

    private OmwLexicalEntry buildLexicalEntry(String lemma, Long pos, List<SenseAttributes> sense) {
        OmwLemma l = new OmwLemma(lemma, WordnetUtils.getPartOfSpeech(pos));

        OmwLexicalEntry en = new OmwLexicalEntry
                .LexicalEntryBuilder(lexicalEntryPrefix + id.getAndIncrement(), l)
                .build();
        sense.forEach(s -> en.addOmwSense(buildSense(s)));
        return en;
    }

    private OmwSense buildSense(SenseAttributes sense) {
        return new OmwSense.SenseBuilder(sensePrefix + sense.getId(), synsetPrefix + sense.getSense().getSynset().getId())
                .senseRelations(buildSenseRelations(sense))
                .examples(buildExamples(sense.getComment()))
                .build();
    }

    private List<OmwExample> buildExamples(String comment) {
        return Collections.emptyList();
    }

    private Set<OmwSenseRelation> buildSenseRelations(SenseAttributes sense) {
        return new HashSet<>();
    }

}
