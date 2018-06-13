package pl.edu.pwr.wordnetloom.business.omw.control;

import pl.edu.pwr.wordnetloom.business.lexicon.boundary.LexiconService;
import pl.edu.pwr.wordnetloom.business.omw.entity.Lexicon;
import pl.edu.pwr.wordnetloom.business.omw.entity.Synset;
import pl.edu.pwr.wordnetloom.business.omw.entity.SynsetRelation;
import pl.edu.pwr.wordnetloom.business.sense.boundary.SenseService;
import pl.edu.pwr.wordnetloom.business.synset.boundary.SynsetService;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class EnWordnetResource {

    public String prefix;

    public String lexicalEntryPrefix = prefix + "-ene-";
    public String sensePrefix = prefix + "-enu-";
    public String synsetPrefix = prefix + "-ens-";
    private final AtomicInteger id = new AtomicInteger(1);
    private final Map<String, Synset> synsetsMap = new ConcurrentHashMap<>();
    private final Map<String, Set<SynsetRelation>> synsetRelationsMap = new ConcurrentHashMap<>();

    private List<pl.edu.pwr.wordnetloom.business.sense.enity.SenseAttributes> senses;

    @Inject
    LexiconService lexiconService;

    @Inject
    SenseService senseService;

    @Inject
    SynsetService synsetService;

    public Lexicon buildLexiconById(Long id) {
        pl.edu.pwr.wordnetloom.business.lexicon.entity.Lexicon lexicon = lexiconService.findById(id).orElse(null);

        if (lexicon != null) {
            senses = senseService.findAllSensesByLexicon(id);
            //   List<LexicalEntry> entries = buildLexicalEntries(senses);

            prefix = String.format("%swn", lexicon.getLanguageShortcut());
/*
        //    return new Lexicon.LexiconBuilder(prefix, lexicon.getName())
                    .language(lexicon.getLanguageShortcut())
                    .email(lexicon.getEmail())
                    .license(lexicon.getLicense())
                    .version(lexicon.getLexiconVersion())
                    .url(lexicon.getReferenceUrl())
                    .citation(lexicon.getCitation())
                    .confidenceScore(lexicon.getConfidenceScore())
                    .lexicalEntries(entries)
                    .synsets(new HashSet<>(synsetsMap.values()))
                    .build();*/
        }

        return null;
    }
}
