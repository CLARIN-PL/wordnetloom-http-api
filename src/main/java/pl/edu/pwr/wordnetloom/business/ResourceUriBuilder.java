package pl.edu.pwr.wordnetloom.business;

import pl.edu.pwr.wordnetloom.business.dictionary.boundary.DictionaryResource;
import pl.edu.pwr.wordnetloom.business.dictionary.entity.Dictionary;
import pl.edu.pwr.wordnetloom.business.dictionary.entity.Domain;
import pl.edu.pwr.wordnetloom.business.dictionary.entity.PartOfSpeech;
import pl.edu.pwr.wordnetloom.business.graph.boundary.GraphsResource;
import pl.edu.pwr.wordnetloom.business.lexicon.boundary.LexiconResource;
import pl.edu.pwr.wordnetloom.business.lexicon.entity.Lexicon;
import pl.edu.pwr.wordnetloom.business.relationtype.boundary.RelationTypeResource;
import pl.edu.pwr.wordnetloom.business.relationtype.entity.RelationTest;
import pl.edu.pwr.wordnetloom.business.relationtype.entity.RelationType;
import pl.edu.pwr.wordnetloom.business.search.boundary.SearchResource;
import pl.edu.pwr.wordnetloom.business.sense.boundary.SenseResource;
import pl.edu.pwr.wordnetloom.business.sense.enity.Sense;
import pl.edu.pwr.wordnetloom.business.sense.enity.SenseEmotions;
import pl.edu.pwr.wordnetloom.business.sense.enity.SenseExample;
import pl.edu.pwr.wordnetloom.business.settings.boundary.SettingsResource;
import pl.edu.pwr.wordnetloom.business.synset.boundary.SynsetResource;
import pl.edu.pwr.wordnetloom.business.synset.entity.Synset;
import pl.edu.pwr.wordnetloom.business.synset.entity.SynsetExample;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class ResourceUriBuilder {

    public URI forDictionary(Dictionary dic, String methodName, UriInfo uriInfo) {
        return createResourceUri(DictionaryResource.class, methodName, dic.getId(), uriInfo);
    }

    public URI forDictionaries(UriInfo uriInfo) {
        return createResourceUri(DictionaryResource.class, uriInfo);
    }

    public URI forDomain(Domain d, UriInfo uriInfo) {
        return createResourceUri(DictionaryResource.class, "getDomain", d.getId(), uriInfo);
    }

    public URI forStatutes(UriInfo uriInfo) {
        return createResourceUri(DictionaryResource.class, "getAllStatuses", uriInfo);
    }

    public URI forEmotions(UriInfo uriInfo) {
        return createResourceUri(DictionaryResource.class, "getAllEmotions", uriInfo);
    }

    public URI forPartOfSpeech(PartOfSpeech p, UriInfo uriInfo) {
        return createResourceUri(DictionaryResource.class, "getPartOfSpeech", p.getId(), uriInfo);
    }

    public URI forPartsOfSpeech(UriInfo uriInfo) {
        return createResourceUri(DictionaryResource.class, "getAllPartsOfSpeech", uriInfo);
    }

    public URI forDomains(UriInfo uriInfo) {
        return createResourceUri(DictionaryResource.class, "getAllDomains", uriInfo);
    }

    public URI forRegisters(UriInfo uriInfo) {
        return createResourceUri(DictionaryResource.class, "getAllRegisters", uriInfo);
    }

    public URI forLexicon(Lexicon l, UriInfo uriInfo) {
        return createResourceUri(LexiconResource.class, "getLexicon", l.getId(), uriInfo);
    }

    public URI forLexicons(UriInfo uriInfo) {
        return createResourceUri(LexiconResource.class, uriInfo);
    }

    public URI forSynsets(UriInfo uriInfo) {
        return createResourceUri(SynsetResource.class, uriInfo);
    }

    public URI forSynset(Synset s, UriInfo uriInfo) {
        return createResourceUri(SynsetResource.class, "getSynset", s.getId(), uriInfo);
    }

    public URI forSenses(UriInfo uriInfo) {
        return createResourceUri(SenseResource.class, uriInfo);
    }

    public URI forSense(Sense s, UriInfo uriInfo) {
        return createResourceUri(SenseResource.class, "getSense", s.getId(), uriInfo);
    }

    public URI forSenseExamples(Sense s, UriInfo uriInfo) {
        return createResourceUri(SenseResource.class, "getSenseExamples", s.getId(), uriInfo);
    }

    public URI forSenseExample(SenseExample se, UriInfo uriInfo) {
        return uriInfo.getBaseUriBuilder().path(SenseResource.class)
                .path(SenseResource.class, "getSenseExample").build(se.getSenseAttributes().getId(), se.getId());
    }

    public URI forEmotionalAnnotations(Sense s, UriInfo uriInfo) {
        return createResourceUri(SenseResource.class, "getEmotionalAnnotations", s.getId(), uriInfo);
    }

    public URI forEmotionalAnnotation(SenseEmotions e, UriInfo uriInfo) {
        return uriInfo.getBaseUriBuilder().path(SenseResource.class)
                .path(SenseResource.class, "getEmotionalAnnotation").build(e.getSense().getId(), e.getId());
    }

    public URI forSearch(UriInfo uriInfo) {
        return createResourceUri(SearchResource.class, uriInfo);
    }

    public URI forRelationTypes(UriInfo uriInfo) {
        return createResourceUri(RelationTypeResource.class, uriInfo);
    }

    public URI forRelationType(RelationType rt, UriInfo uriInfo) {
        return createResourceUri(RelationTypeResource.class, "getRelationType", rt.getId(), uriInfo);
    }

    public URI forRelationTests(RelationType rt, UriInfo uriInfo) {
        return createResourceUri(RelationTypeResource.class, "getRelationTests", rt.getId(), uriInfo);
    }

    public URI forRelationTest(RelationTest rt, UriInfo uriInfo) {
        return uriInfo.getBaseUriBuilder().path(RelationTypeResource.class)
                .path(RelationTypeResource.class, "getRelationTest").build(rt.getRelationType().getId(), rt.getId());
    }

    private URI createResourceUri(Class<?> resourceClass, String method, long id, UriInfo uriInfo) {
        return uriInfo.getBaseUriBuilder().path(resourceClass).path(resourceClass, method).build(id);
    }

    private URI createResourceUri(Class<?> resourceClass, String method, UriInfo uriInfo) {
        return uriInfo.getBaseUriBuilder().path(resourceClass).path(resourceClass, method).build();
    }

    private URI createResourceUri(Class<?> resourceClass, UriInfo uriInfo) {
        return uriInfo.getBaseUriBuilder().path(resourceClass).build();
    }

    public URI forValuations(UriInfo uriInfo) {
        return createResourceUri(DictionaryResource.class, "getAllValuations", uriInfo);
    }

    public URI forMarkedness(UriInfo uriInfo) {
        return createResourceUri(DictionaryResource.class, "getAllMarkedness", uriInfo);
    }

    public URI forSettings(UriInfo uriInfo) {
        return createResourceUri(SettingsResource.class, uriInfo);
    }

    public URI forGraphs(UriInfo uriInfo) {
        return createResourceUri(GraphsResource.class, uriInfo);
    }

    public URI forAspects(UriInfo uriInfo) {
        return createResourceUri(DictionaryResource.class, "getAllAspects", uriInfo);
    }

    public URI forSenseRelations(Sense s, UriInfo uriInfo) {
        return createResourceUri(SenseResource.class, "getSenseRelations", s.getId(), uriInfo);
    }

    public URI forSenseGraph(long senseId, UriInfo uriInfo) {
        return createResourceUri(GraphsResource.class, "getSenseGraph", senseId, uriInfo);
    }

    public URI forSynsetExamples(Synset s, UriInfo uriInfo) {
        return createResourceUri(SynsetResource.class, "getSynsetExamples", s.getId(), uriInfo);
    }

    public URI forSynsetExample(SynsetExample se, UriInfo uriInfo) {
        return uriInfo.getBaseUriBuilder().path(SynsetResource.class)
                .path(SynsetResource.class, "getSynsetExample").build(se.getSynsetAttributes().getId(), se.getId());
    }

    public URI forSynsetRelations(Synset s, UriInfo uriInfo) {
        return createResourceUri(SynsetResource.class, "getSynsetRelations", s.getId(), uriInfo);
    }

    public URI forSynsetsGraph(long id, UriInfo uriInfo) {
        return createResourceUri(GraphsResource.class, "getSynsetGraph", id, uriInfo);
    }
}