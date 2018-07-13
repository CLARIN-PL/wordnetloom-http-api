package pl.edu.pwr.wordnetloom.business.download.control;

import pl.edu.pwr.wordnetloom.business.download.entity.*;
import pl.edu.pwr.wordnetloom.business.lexicon.boundary.LexiconService;
import pl.edu.pwr.wordnetloom.business.sense.boundary.SenseService;
import pl.edu.pwr.wordnetloom.business.sense.enity.Sense;
import pl.edu.pwr.wordnetloom.business.sense.enity.SenseRelation;
import pl.edu.pwr.wordnetloom.business.synset.boundary.SynsetService;
import pl.edu.pwr.wordnetloom.business.synset.entity.Synset;
import pl.edu.pwr.wordnetloom.business.synset.entity.SynsetExample;
import pl.edu.pwr.wordnetloom.business.synset.entity.SynsetRelation;

import javax.inject.Inject;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EnWordnetBuilder {

    private final static AtomicInteger id = new AtomicInteger(1);

    @Inject
    LexiconService lexiconService;

    @Inject
    SenseService senseService;

    @Inject
    SynsetService synsetService;

    private static String lexicalEntryId(String prefix) {
        return String.format("%s-ent-%s", prefix.toLowerCase(), id.getAndIncrement());
    }

    private static String senseId(String prefix, long id) {
        return String.format("%s-unt-%s", prefix.toLowerCase(), id);
    }

    private static String synsetId(String prefix, long id) {
        return String.format("%s-syn-%s", prefix.toLowerCase(), id);
    }

    private Map<String, OmwSynset> synsetsFromSenses(List<SenseRelation> sr, List<Long> allowedLexiconRelations, Map<Long, Long> synsetPartOfSpeeches) {

        Set<Synset> synsets = sr.stream()
                .filter(r -> allowedLexiconRelations.contains(r.getChild().getLexicon().getId()))
                .filter(r -> !WordnetUtils.convertSenseRelationToSynset(r.getRelationType().getId()))
                .map(r -> r.getChild().getSynset())
                .collect(Collectors.toSet());

        return synsets.stream()
                .filter(s -> s.getLexicon().getId() == 2L)
                .map(s -> buildPrincetonSynset(s, s.getLexicon().getIdentifier(), synsetPartOfSpeeches))
                .distinct()
                .collect(Collectors.toMap(OmwSynset::getId, Function.identity()));
    }

    private Map<String, Set<OmwSenseRelation>> buildSenseRelations(List<SenseRelation> sr, List<Long> allowedLexiconRelations) {
        return sr.stream()
                .filter(r -> allowedLexiconRelations.contains(r.getChild().getLexicon().getId()))
                .filter(r -> !WordnetUtils.convertSenseRelationToSynset(r.getRelationType().getId()))
                .map(r -> new OmwSenseRelation(
                        senseId(r.getParent().getLexicon().getIdentifier(), r.getParent().getId()),
                        senseId(r.getChild().getLexicon().getIdentifier(), r.getChild().getId()),
                        WordnetUtils.mapRelType(r.getRelationType().getId())))
                .collect(Collectors.groupingBy(OmwSenseRelation::getParent, Collectors.toSet()));
    }

    private Map<String, Set<OmwSynsetRelation>> buildSynsetRelationsFromSenseRelations(List<SenseRelation> sr, List<Long> allowedLexiconRelations) {
        return sr.stream()
                .filter(r -> allowedLexiconRelations.contains(r.getChild().getLexicon().getId()))
                .filter(r -> WordnetUtils.convertSenseRelationToSynset(r.getRelationType().getId()))
                .map(r -> new OmwSynsetRelation(
                        synsetId(r.getParent().getLexicon().getIdentifier(), r.getParent().getSynset().getId()),
                        synsetId(r.getChild().getLexicon().getIdentifier(), r.getChild().getSynset().getId()),
                        WordnetUtils.mapRelType(r.getRelationType().getId())))
                .collect(Collectors.groupingBy(OmwSynsetRelation::getParent, Collectors.toSet()));
    }

    private List<OmwLexicalEntry> buildLexicalEntries(long lexiconId, List<Long> allowedLexiconRelations, List<SenseRelation> relations) {

        List<Sense> senses = senseService.findAllSensesByLexicon(lexiconId);

        Map<String, Set<OmwSenseRelation>> senseRelations = buildSenseRelations(relations, allowedLexiconRelations);

        Map<String, Map<Long, List<Sense>>> map = senses
                .stream()
                .collect(Collectors.groupingBy(s -> s.getWord().getWord(),
                        Collectors.groupingBy(s -> s.getPartOfSpeech().getId())));

        return map.entrySet()
                .stream()
                .flatMap(e -> e.getValue().entrySet().stream()
                        .map(z -> buildLexicalEntry("enwn", e.getKey(), z.getKey(), z.getValue(), senseRelations))
                        .collect(Collectors.toList()).stream())
                .collect(Collectors.toList());
    }

    private OmwLexicalEntry buildLexicalEntry(String prefix, String lemma, Long pos, List<Sense> sense, Map<String, Set<OmwSenseRelation>> rels) {
        OmwLemma l = new OmwLemma(lemma, WordnetUtils.getPartOfSpeech(pos));

        OmwLexicalEntry en = new OmwLexicalEntry
                .LexicalEntryBuilder(lexicalEntryId(prefix), l)
                .build();

        sense.stream()
                .filter(s -> s.getSynset() != null)
                .forEach(s -> en.addOmwSense(buildSense(s, rels.get(senseId(s.getLexicon().getIdentifier(), s.getId())))));
        return en;
    }

    private OmwSense buildSense(Sense sense, Set<OmwSenseRelation> relations) {
        return new OmwSense.SenseBuilder(senseId(sense.getLexicon().getIdentifier(), sense.getId()),
                synsetId(sense.getLexicon().getIdentifier(), sense.getSynset().getId()))
                .examples(buildExamples(sense))
                .senseRelations(relations)
                .build();
    }

    private List<OmwExample> buildExamples(Sense sense) {
        if (sense.getAttributes().getExamples() == null) {
            return Collections.emptyList();
        }
        return sense.getAttributes().getExamples()
                .stream()
                .map(e -> new OmwExample(e.getExample()))
                .collect(Collectors.toList());
    }

    private Set<OmwSynset> buildSynsets(long lexiconId, List<Long> allowedLexiconRelations,
                                        Map<String, Set<OmwSynsetRelation>> fromSense,
                                        Map<String, OmwSynset> synsetsFromSense,  Map<Long, Long> synsetPartOfSpeeches) {


        Map<String, OmwSynset> synsets = synsetService.findSynsetWithAttributesByLexicon(lexiconId)
                .stream()
                .map(s -> buildEnWordnetSynset(s, s.getLexicon().getIdentifier(), synsetPartOfSpeeches))
                .collect(Collectors.toMap(OmwSynset::getId, Function.identity()));

        List<SynsetRelation> synsetRelations = synsetService.findSynsetRelationsByParentLexicon(lexiconId);
        List<SynsetRelation> princetonSynsetRelations = synsetService.findSynsetRelationsByParentLexicon(2L)
                .stream().filter(r -> r.getChild().getId() == 3L)
                .collect(Collectors.toList());

        Map<String, OmwSynset> princetonSynsets = synsetRelations.stream()
                .filter(sr -> sr.getChild().getLexicon().getId() == 2L)
                .map(sr -> buildPrincetonSynset(sr.getChild(), sr.getChild().getLexicon().getIdentifier(), synsetPartOfSpeeches))
                .distinct()
                .collect(Collectors.toMap(OmwSynset::getId, Function.identity()));

        Map<String, Set<OmwSynsetRelation>> osr = Stream.of(synsetRelations, princetonSynsetRelations).flatMap(Collection::stream)
                .filter(r -> allowedLexiconRelations.contains(r.getChild().getLexicon().getId()))
                .map(r -> new OmwSynsetRelation(
                        synsetId(r.getParent().getLexicon().getIdentifier(), r.getParent().getId()),
                        synsetId(r.getChild().getLexicon().getIdentifier(), r.getChild().getId()),
                        WordnetUtils.mapRelType(r.getRelationType().getId())))
                .collect(Collectors.groupingBy(OmwSynsetRelation::getParent, Collectors.toSet()));

        Map<String, Set<OmwSynsetRelation>> allRelations = Stream.of(osr, fromSense)
                .map(Map::entrySet)
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return Stream.of(synsets, princetonSynsets, synsetsFromSense).flatMap(m -> m.entrySet().stream())
                .map(s -> new OmwSynset(s.getValue(), allRelations.get(s.getKey())))
                .collect(Collectors.toSet());
    }

    private OmwSynset buildPrincetonSynset(Synset s, String lexiconPrefix, Map<Long, Long> synsetPartOfSpeeches) {
        return new OmwSynset.SynsetBuilder(synsetId(lexiconPrefix, s.getId()), s.getAttributes().getIliId())
                .partOfSpeech(WordnetUtils.getPartOfSpeech(synsetPartOfSpeeches.get(s.getId())))
                .build();
    }

    private OmwSynset buildEnWordnetSynset(Synset s, String lexiconPrefix, Map<Long, Long> synsetPartOfSpeeches) {
        return new OmwSynset.SynsetBuilder(synsetId(lexiconPrefix, s.getId()), "in")
                .addDefinition(new OmwDefinition(s.getAttributes().getDefinition() != null ? s.getAttributes().getDefinition() : ""))
                .partOfSpeech(WordnetUtils.getPartOfSpeech(synsetPartOfSpeeches.get(s.getId())))
                .addExample(s.getAttributes().getExamples().stream()
                        .map(SynsetExample::getExample)
                        .findFirst().orElse(null))
                .iliDefinition(new OmwILIDefinition(s.getAttributes().getDefinition()))
                .build();

    }

    public OmwLexicalResource buildLexicalResource(long lexiconId) {
        List<Long> allowed = Arrays.asList(2L, 3L);

        List<SenseRelation> sr = senseService.findSenseRelationsByParentLexicon(lexiconId);
        Map<Long, Long> synsetPartOfSpeeches = synsetService.findSynsetsPartOfSpeech(allowed);
        Map<String, Set<OmwSynsetRelation>> rebuildSenseRelations = buildSynsetRelationsFromSenseRelations(sr, allowed);

        List<OmwLexicalEntry> entries = buildLexicalEntries(lexiconId, allowed, sr);
        Map<String, OmwSynset> fromSense = synsetsFromSenses(sr, allowed, synsetPartOfSpeeches);
        Set<OmwSynset> synsets = buildSynsets(lexiconId, allowed, rebuildSenseRelations, fromSense, synsetPartOfSpeeches);

        return new OmwLexicalResource.LexicalResourceBuilder()
                .addLexicon(buildLexicon(lexiconId, entries, synsets).orElse(null))
                .build();
    }

    private Optional<OmwLexicon> buildLexicon(long lexiconId, List<OmwLexicalEntry> entries, Set<OmwSynset> synsets) {
        return lexiconService.findById(lexiconId)
                .map(l -> new OmwLexicon.LexiconBuilder(l.getIdentifier().toLowerCase(), l.getName())
                        .language(l.getLanguageShortcut())
                        .email(l.getEmail())
                        .license(l.getLicense())
                        .version(l.getLexiconVersion())
                        .url(l.getReferenceUrl())
                        .citation(l.getCitation())
                        .confidenceScore(l.getConfidenceScore())
                        .lexicalEntries(entries)
                        .synsets(synsets)
                        .build());
    }

}
