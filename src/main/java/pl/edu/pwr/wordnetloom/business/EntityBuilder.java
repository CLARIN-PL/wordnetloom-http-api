package pl.edu.pwr.wordnetloom.business;

import pl.edu.pwr.wordnetloom.business.dictionary.entity.Dictionary;
import pl.edu.pwr.wordnetloom.business.dictionary.entity.Domain;
import pl.edu.pwr.wordnetloom.business.dictionary.entity.PartOfSpeech;
import pl.edu.pwr.wordnetloom.business.lexicon.entity.Lexicon;
import pl.edu.pwr.wordnetloom.business.relationtype.entity.RelationTest;
import pl.edu.pwr.wordnetloom.business.relationtype.entity.RelationType;
import pl.edu.pwr.wordnetloom.business.search.entity.SearchFilter;
import pl.edu.pwr.wordnetloom.business.sense.enity.*;
import pl.edu.pwr.wordnetloom.business.synset.entity.Synset;
import pl.edu.pwr.wordnetloom.business.synset.entity.SynsetAttributes;
import pl.edu.pwr.wordnetloom.business.synset.entity.SynsetExample;

import javax.inject.Inject;
import javax.json.*;
import javax.json.stream.JsonCollectors;
import javax.ws.rs.core.UriInfo;
import java.math.BigInteger;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static javax.json.Json.createArrayBuilder;
import static javax.json.Json.createObjectBuilder;

public class EntityBuilder {

    @Inject
    LocalisedStringsLocator loc;

    @Inject
    LinkBuilder linkBuilder;

    public JsonObject buildRootDocument(UriInfo uriInfo) {
        return createObjectBuilder()
                .add("links", createArrayBuilder()
                        .add(createLinkObject("dictionaries", linkBuilder.forDictionaries(uriInfo)))
                        .add(createLinkObject("graphs", linkBuilder.forGraphs(uriInfo)))
                        .add(createLinkObject("lexicons", linkBuilder.forLexicons(uriInfo)))
                        .add(createLinkObject("relation_types", linkBuilder.forRelationTypes(uriInfo)))
                        .add(createLinkObject("search", linkBuilder.forSearch(uriInfo)))
                        .add(createLinkObject("senses", linkBuilder.forSenses(uriInfo, 0, 100)))
                        .add(createLinkObject("settings", linkBuilder.forSettings(uriInfo)))
                        .add(createLinkObject("synsets", linkBuilder.forSynsets(uriInfo, 0, 100)))
                        .add(createLinkObject("statistics", linkBuilder.forStatistics(uriInfo)))
                ).build();
    }

    private JsonObject createLinkObject(String relation, URI uri) {
        return createObjectBuilder().add("rel", createArrayBuilder().add(relation)).add("href", uri.toString()).build();
    }

    public JsonObject buildDictionary(Dictionary dic, URI self, Locale locale) {

        JsonObjectBuilder builder = createObjectBuilder();

        builder.add("id", dic.getId());
        if (dic.getName() != null) {
            builder.add("name", loc.find(dic.getName(), locale));
        }
        if (dic.getDescription() != null) {
            builder.add("description", loc.find(dic.getDescription(), locale));
        }

        JsonObjectBuilder linkBuilder = createObjectBuilder();
        linkBuilder.add("self", self.toString());

        builder.add("_links", linkBuilder);

        return builder.build();
    }

    public JsonObject buildDomain(Domain d, URI self, Locale locale) {

        JsonObjectBuilder builder = createObjectBuilder();

        builder.add("id", d.getId());
        if (d.getName() != null) {
            builder.add("name", loc.find(d.getName(), locale));
        }
        if (d.getDescription() != null) {
            builder.add("description", loc.find(d.getDescription(), locale));
        }

        JsonObjectBuilder linkBuilder = createObjectBuilder();
        linkBuilder.add("self", self.toString());

        builder.add("_links", linkBuilder);
        return builder.build();
    }

    public JsonObject buildPartOfSpeech(PartOfSpeech p, URI self, Locale locale) {

        JsonObjectBuilder builder = createObjectBuilder();

        builder.add("id", p.getId());
        if (p.getName() != null) {
            builder.add("name", loc.find(p.getName(), locale));
        }

        JsonObjectBuilder linkBuilder = createObjectBuilder();
        linkBuilder.add("self", self.toString());

        builder.add("_links", linkBuilder);
        return builder.build();
    }

    public JsonObject buildLexicon(Lexicon lex, URI self) {
        JsonObjectBuilder builder = createObjectBuilder();

        builder.add("id", lex.getId());

        if (lex.getName() != null) {
            builder.add("name", lex.getName());
        }
        if (lex.getIdentifier() != null) {
            builder.add("identifier", lex.getIdentifier());
        }
        if (lex.getReferenceUrl() != null) {
            builder.add("reference_url", lex.getReferenceUrl());
        }
        if (lex.getLanguageName() != null) {
            builder.add("language", lex.getLanguageName());
        }
        if (lex.getLanguageName() != null) {
            builder.add("language_shortcut", lex.getLanguageShortcut());
        }
        if (lex.getLexiconVersion() != null) {
            builder.add("version", lex.getLexiconVersion());
        }
        if (lex.getLicense() != null) {
            builder.add("license", lex.getLicense());
        }
        if (lex.getEmail() != null) {
            builder.add("email", lex.getEmail());
        }
        if (lex.getCitation() != null) {
            builder.add("citation", lex.getCitation());
        }
        if (lex.getConfidenceScore() != null) {
            builder.add("confidence_score", lex.getConfidenceScore());
        }

        JsonObjectBuilder linkBuilder = createObjectBuilder();
        linkBuilder.add("self", self.toString());

        builder.add("_links", linkBuilder);
        return builder.build();
    }

    public JsonObject buildLexiconShort(Lexicon lex, UriInfo uriInfo) {
        JsonObjectBuilder builder = createObjectBuilder();

        builder.add("id", lex.getId());

        if (lex.getName() != null) {
            builder.add("name", lex.getName());
        }

        JsonObjectBuilder linkBuilder = createObjectBuilder();
        linkBuilder.add("self", this.linkBuilder.forLexicon(lex, uriInfo).toString());

        builder.add("_links", linkBuilder);
        return builder.build();
    }

    public JsonObject buildPartOfSpeechShort(PartOfSpeech pos, UriInfo uriInfo, Locale locale) {
        JsonObjectBuilder builder = createObjectBuilder();

        builder.add("id", pos.getId());

        if (pos.getName() != null) {
            builder.add("name", loc.find(pos.getName(), locale));
        }

        JsonObjectBuilder linkBuilder = createObjectBuilder();
        linkBuilder.add("self", this.linkBuilder.forPartOfSpeech(pos, uriInfo).toString());

        builder.add("_links", linkBuilder);
        return builder.build();
    }

    public JsonObject buildRelationTypeShort(RelationType rt, URI self, Locale locale) {
        JsonObjectBuilder builder = createObjectBuilder();
        builder.add("id", rt.getId());

        if (rt.getName() != null) {
            builder.add("name", loc.find(rt.getName(), locale));
        }

        JsonObjectBuilder linkBuilder = createObjectBuilder();
        linkBuilder.add("self", self.toString());

        builder.add("_links", linkBuilder);
        return builder.build();

    }

    public JsonObject buildDictionaryShort(Dictionary d, String method, UriInfo uriInfo, Locale locale) {
        JsonObjectBuilder builder = createObjectBuilder();
        builder.add("id", d.getId());

        if (d.getName() != null) {
            builder.add("name", loc.find(d.getName(), locale));
        }

        JsonObjectBuilder linkBuilder = createObjectBuilder();
        linkBuilder.add("self", this.linkBuilder.forDictionary(d, method, uriInfo).toString());

        builder.add("_links", linkBuilder);
        return builder.build();

    }

    public JsonObject buildDomainShort(Domain d, UriInfo uriInfo, Locale locale) {
        JsonObjectBuilder builder = createObjectBuilder();
        builder.add("id", d.getId());

        if (d.getName() != null) {
            builder.add("name", loc.find(d.getName(), locale));
        }

        JsonObjectBuilder linkBuilder = createObjectBuilder();
        linkBuilder.add("self", this.linkBuilder.forDomain(d, uriInfo).toString());

        builder.add("_links", linkBuilder);
        return builder.build();

    }

    public JsonObject buildRelationType(RelationType rt, URI self, URI tests, UriInfo uriInfo, Locale locale) {
        JsonObjectBuilder builder = createObjectBuilder();
        builder.add("id", rt.getId());

        if (rt.getLexicons().size() > 0) {
            JsonArray le = rt.getLexicons()
                    .stream()
                    .map(l -> buildLexiconShort(l, uriInfo))
                    .collect(JsonCollectors.toJsonArray());
            builder.add("allowed_lexicons", le);
        }
        if (rt.getPartsOfSpeech().size() > 0) {
            JsonArray pos = rt.getPartsOfSpeech()
                    .stream()
                    .map(p -> buildPartOfSpeechShort(p, uriInfo, locale))
                    .collect(JsonCollectors.toJsonArray());
            builder.add("allowed_parts_of_speech", pos);
        }
        if (rt.getName() != null) {
            builder.add("name", loc.find(rt.getName(), locale));
        }

        if (rt.getDescription() != null) {
            builder.add("description", loc.find(rt.getDescription(), locale));
        }

        if (rt.getDisplayText() != null) {
            builder.add("display", loc.find(rt.getDisplayText(), locale));
        }

        if (rt.getShortDisplayText() != null) {
            builder.add("short_name", loc.find(rt.getShortDisplayText(), locale));
        }

        if (rt.getAutoReverse() != null) {
            builder.add("auto_revers", rt.getAutoReverse());
        }

        builder.add("argument", rt.getRelationArgument().name());

        if (rt.getMultilingual() != null) {
            builder.add("multilingual", rt.getMultilingual());
        }

        if (rt.getParent() != null) {
            builder.add("parent_relation", buildRelationTypeShort(rt.getParent(),
                    linkBuilder.forRelationType(rt.getParent(), uriInfo), locale));
        }

        if (rt.getReverse() != null) {
            builder.add("reverse_relation", buildRelationTypeShort(rt.getReverse(),
                    linkBuilder.forRelationType(rt.getReverse(), uriInfo), locale));
        }

        JsonObjectBuilder linkBuilder = createObjectBuilder();
        linkBuilder.add("self", self.toString());
        linkBuilder.add("tests", tests.toString());

        builder.add("_links", linkBuilder);
        return builder.build();
    }

    public JsonObject buildSynset(Synset synset, SynsetAttributes attributes, URI self, UriInfo uriInfo, Locale locale) {

        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("id", synset.getId());

        if (Objects.nonNull(synset.getLexicon())) {
            builder.add("lexicon", buildLexiconShort(synset.getLexicon(), uriInfo));
        }

        builder.add("senses", synset.getSenses().stream()
                .map(s -> buildSenseShort(s, linkBuilder.forSense(s, uriInfo), uriInfo, locale))
                .collect(JsonCollectors.toJsonArray()));

        builder.add("isAbstract", synset.getAbstract());

        if (Objects.nonNull(attributes.getDefinition())) {
            builder.add("definition", attributes.getDefinition());
        }

        JsonObjectBuilder linkBuilder = createObjectBuilder();
        linkBuilder.add("self", self.toString());
        linkBuilder.add("examples", this.linkBuilder.forSynsetExamples(synset, uriInfo).toString());
        linkBuilder.add("relations", this.linkBuilder.forSynsetRelations(synset, uriInfo).toString());
        linkBuilder.add("graph", this.linkBuilder.forSynsetsGraph(synset.getId(), uriInfo).toString());

        builder.add("_links", linkBuilder);

        return builder.build();
    }

    public JsonObject buildRelationTest(RelationTest rt, URI self, Locale locale) {
        JsonObjectBuilder builder = createObjectBuilder();
        builder.add("id", rt.getId());

        if (rt.getTest() != null) {
            builder.add("test", rt.getTest());
        }

        JsonObjectBuilder linkBuilder = createObjectBuilder();
        linkBuilder.add("self", self.toString());

        builder.add("_links", linkBuilder);
        return builder.build();
    }

    public JsonObject buildPartOfSpeechSettings(List<PartOfSpeech> list) {
        JsonObjectBuilder b = Json.createObjectBuilder();
        list.forEach(p -> b.add(String.valueOf(p.getId()), Json.createObjectBuilder()
                .add("color", p.getColor() != null ? p.getColor() : "#000000")
                .build()));
        return b.build();
    }

    public JsonObject buildRelationTypeSettings(List<RelationType> list, Locale locale) {
        JsonObjectBuilder b = Json.createObjectBuilder();
        list.forEach(p -> b.add(loc.find(p.getShortDisplayText(), locale), Json.createObjectBuilder()
                .add("color", p.getColor() != null ? p.getColor() : "#000000")
                .add("direction", p.getNodePosition().getAsString())
                .add("dotted", false)
                .build()));
        return b.build();
    }

    public JsonObject buildPaginatedSense(List<Sense> senses, long count, int page, int perPage, UriInfo uriInfo, Locale locale) {

        JsonObjectBuilder linkBuilder = createObjectBuilder();
        linkBuilder.add("self", this.linkBuilder.forSenses(uriInfo, page, perPage).toString());
        linkBuilder.add("prev", "");

        linkBuilder.add("next", this.linkBuilder.forSenses(uriInfo,
                findNextPage(count, page, perPage)
                        .orElse(0), perPage).toString());

        return Json.createObjectBuilder()
                .add("page", page)
                .add("per_page", perPage)
                .add("size", count)
                .add("_links", linkBuilder)
                .add("rows", senses
                        .stream().map(s -> buildSenseShort(s, this.linkBuilder.forSense(s, uriInfo), uriInfo, locale))
                        .collect(JsonCollectors.toJsonArray()))
                .build();
    }

    private Optional<Integer> findNextPage(long count, Integer page, int perPage) {
        if (count > perPage) {
            return ((page + 1) * perPage) < count ? Optional.of(page + 1) : Optional.empty();
        }
        return Optional.empty();
    }

    public JsonObject buildPaginatedSenseSearch(List<Sense> senses, long count, SearchFilter filter, UriInfo uriInfo, Locale locale) {
        JsonObjectBuilder linkBuilder = createObjectBuilder();
        linkBuilder.add("self", uriInfo.getRequestUri().toString());
        linkBuilder.add("prev", "");
        linkBuilder.add("next", "");
        return Json.createObjectBuilder()
                .add("page", filter.getPaginationData().getFirstResult())
                .add("per_page", filter.getPaginationData().getMaxResults())
                .add("size", count)
                .add("_links", linkBuilder)
                .add("rows", senses
                        .stream().map(s -> buildSenseShort(s, this.linkBuilder.forSense(s, uriInfo), uriInfo, locale))
                        .collect(JsonCollectors.toJsonArray())).build();
    }

    public JsonObject buildSenseRelations(Sense sense, Locale locale) {
        return Json.createObjectBuilder()
                .add("root", buildLabel(sense, false, locale))
                .add("incoming", buildSenseRelations(sense.getIncomingRelations(), true, locale))
                .add("outgoing", buildSenseRelations(sense.getOutgoingRelations(), false, locale))
                .build();
    }

    public JsonObject buildSynsetRelations(Synset synset, Map<String, List<Object[]>> relations, Locale locale) {
        return Json.createObjectBuilder()
                .add("root", buildLabel(synset, locale))
                .add("incoming", buildSynsetRelations(relations.get("incoming"), locale))
                .add("outgoing", buildSynsetRelations(relations.get("outgoing"), locale))
                .build();
    }

    public JsonObject buildSenseRelation(Sense se, SenseRelation sr, Locale locale) {
        return Json.createObjectBuilder()
                .add("relation_id", sr.getId())
                .add("target", se.getId())
                .add("label", buildLabel(se, false, locale))
                .build();
    }

    public JsonObject buildSynsetRelation(Object[] row, Locale locale) {
        return Json.createObjectBuilder()
                .add("relation_id", (BigInteger) row[2])
                .add("target", (BigInteger) row[3])
                .add("label", buildLabel((String) row[4], ((BigInteger) row[5]).longValue(), locale))
                .build();
    }


    public JsonObject buildSense(Sense sense, SenseAttributes attributes, URI self, UriInfo uriInfo, Locale locale) {
        JsonObjectBuilder builder = createObjectBuilder();
        builder.add("id", sense.getId());

        if (sense.getLexicon() != null) {
            builder.add("lexicon", buildLexiconShort(sense.getLexicon(), uriInfo));
        }

        if (sense.getSynset() != null) {
            builder.add("synsetId", sense.getSynset().getId());
        }

        if (sense.getWord() != null) {
            builder.add("lemma", sense.getWord().getWord());
        }

        builder.add("variant", sense.getVariant());

        if (sense.getPartOfSpeech() != null) {
            builder.add("partOfSpeech", buildPartOfSpeechShort(sense.getPartOfSpeech(), uriInfo, locale));
        }

        if (Objects.nonNull(sense.getDomain())) {
            builder.add("domain", buildDomainShort(sense.getDomain(), uriInfo, locale));
        }

        if (Objects.nonNull(attributes.getAspect())) {
            builder.add("aspect", buildDictionaryShort(attributes.getAspect(), "getAspect", uriInfo, locale));
        }

        if (Objects.nonNull(attributes.getRegister())) {
            builder.add("register", buildDictionaryShort(attributes.getRegister(), "getRegister", uriInfo, locale));
        }

        if (Objects.nonNull(attributes.getDefinition())) {
            builder.add("definition", attributes.getDefinition());
        }

        if (Objects.nonNull(attributes.getLink())) {
            builder.add("link", attributes.getLink());
        }

        JsonObjectBuilder linkBuilder = createObjectBuilder();
        linkBuilder.add("self", self.toString());
        linkBuilder.add("examples", this.linkBuilder.forSenseExamples(sense, uriInfo).toString());
        linkBuilder.add("relations", this.linkBuilder.forSenseRelations(sense, uriInfo).toString());
        linkBuilder.add("emotional-annotations", this.linkBuilder.forEmotionalAnnotations(sense, uriInfo).toString());
        linkBuilder.add("graph", this.linkBuilder.forSenseGraph(sense.getId(), uriInfo).toString());

        builder.add("_links", linkBuilder);

        return builder.build();
    }


    public JsonObject buildSenseShort(Sense sense, URI self, UriInfo uriInfo, Locale locale) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("id", sense.getId());

        if (sense.getSynset() != null) {
            builder.add("synset", sense.getSynset().getId());
        }

        if (sense.getPartOfSpeech() != null) {
            builder.add("partOfSpeech", buildPartOfSpeechShort(sense.getPartOfSpeech(), uriInfo, locale));
        }

        if (sense.getWord() != null) {
            builder.add("label", buildLabel(sense, true, locale));
        }

        JsonObjectBuilder linkBuilder = createObjectBuilder();
        linkBuilder.add("self", self.toString());
        builder.add("_links", linkBuilder);

        return builder.build();
    }

    private JsonArray buildSenseRelations(Set<SenseRelation> list, boolean parent, Locale locale) {

        JsonArrayBuilder builder = createArrayBuilder();

        Map<RelationType, List<SenseRelation>> map = list.stream()
                .collect(Collectors.groupingBy(SenseRelation::getRelationType));

        map.forEach((k, v) -> {

            JsonArray a = v.stream()
                    .map(r -> {
                        Sense s = parent ? r.getParent() : r.getChild();
                        return buildSenseRelation(s, r, locale);
                    })
                    .collect(JsonCollectors.toJsonArray());

            JsonObject rel = Json.createObjectBuilder()
                    .add("relation_type_id", k.getId())
                    .add("relation_type_name", loc.find(k.getName(), locale))
                    .add("rows", a)
                    .build();

            builder.add(rel);
        });
        return builder.build();
    }

    private JsonArray buildSynsetRelations(List<Object[]> list, Locale locale) {

        JsonArrayBuilder builder = createArrayBuilder();

        Map<String, List<Object[]>> map = list.stream()
                .collect(Collectors.groupingBy(o -> loc.find(((BigInteger) o[1]).longValue(), locale)));

        map.forEach((k, v) -> {

            JsonArray a = v.stream()
                    .map(r -> buildSynsetRelation(r, locale))
                    .collect(JsonCollectors.toJsonArray());

            JsonObject rel = Json.createObjectBuilder()
                    .add("relation_type_name", k)
                    .add("rows", a)
                    .build();

            builder.add(rel);
        });
        return builder.build();
    }

    public JsonObject buildEmotionalAnnotation(SenseEmotions se, URI self) {

        JsonObjectBuilder builder = createObjectBuilder();
        builder.add("id", se.getId());
        builder.add("emotional_characteristic", se.isHasEmotionalCharacteristic());
        builder.add("super_annotation", se.isSuperAnnotation());
        if (se.getEmotions() != null)
            builder.add("emotions", se.getEmotions());
        if (se.getValuations() != null)
            builder.add("valuations", se.getValuations());
        if (se.getMarkedness() != null)
            builder.add("markedness", se.getMarkedness());
        if (se.getExample1() != null)
            builder.add("example1", se.getExample1());
        if (se.getExample2() != null)
            builder.add("example2", se.getExample2());

        JsonObjectBuilder linkBuilder = createObjectBuilder();
        linkBuilder.add("self", self.toString());
        builder.add("_links", linkBuilder);

        return builder.build();
    }

    public JsonObject buildSynsetExample(SynsetExample se, URI self) {
        JsonObjectBuilder builder = createObjectBuilder();

        builder.add("id", se.getId());
        if (se.getExample() != null)
            builder.add("text", se.getExample());
        if (se.getType() != null)
            builder.add("type", se.getType());

        JsonObjectBuilder linkBuilder = createObjectBuilder();
        linkBuilder.add("self", self.toString());
        builder.add("_links", linkBuilder);

        return builder.build();
    }

    public String buildLabel(Sense sense, Boolean withLexicon, Locale locale) {
        StringBuilder sb = new StringBuilder();
        sb.append(sense.getWord())
                .append(" ")
                .append(sense.getVariant());
        if (sense.getDomain() != null) {
            sb.append(" (").append(loc.find(sense.getDomain().getName(), locale)).append(")");
        }
        if (withLexicon && sense.getLexicon() != null) {
            sb.append(" ").append(sense.getLexicon().getIdentifier());
        }
        return sb.toString();
    }

    public String buildLabel(Synset synset, Locale locale) {
        return synset.getSenses().stream().findFirst()
                .map(s -> (synset.getAbstract() ? "S " : "") + s.getWord() + " " + s.getVariant() + " (" + loc.find(s.getDomain().getName(), locale) + ")")
                .orElse("");

    }

    public String buildLabel(String lemma, Long domainNameId, Locale locale) {
        return lemma + " (" + loc.find(domainNameId, locale) + ")";

    }

    public JsonObject buildSenseExample(SenseExample e, URI self) {
        JsonObjectBuilder builder = createObjectBuilder();
        builder.add("id", e.getId());

        if (e.getExample() != null)
            builder.add("text", e.getExample());
        if (e.getType() != null)
            builder.add("type", e.getType());

        JsonObjectBuilder linkBuilder = createObjectBuilder();
        linkBuilder.add("self", self.toString());
        builder.add("_links", linkBuilder);

        return builder.build();
    }

    public JsonObject buildLexiconSettings(List<Lexicon> lex) {
        JsonObjectBuilder b = Json.createObjectBuilder();
        lex.forEach(l -> {
            b.add(String.valueOf(l.getId()), Json.createObjectBuilder()
                    .add("icon", l.getLanguageShortcut()));
        });
        return b.build();
    }
}
