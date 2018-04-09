package pl.edu.pwr.wordnetloom.business.graph.boundary;

import pl.edu.pwr.wordnetloom.business.EntityBuilder;
import pl.edu.pwr.wordnetloom.business.LocalisedStringsLocator;
import pl.edu.pwr.wordnetloom.business.graph.entity.NodeExpanded;
import pl.edu.pwr.wordnetloom.business.graph.entity.NodeHidden;
import pl.edu.pwr.wordnetloom.business.sense.boundary.SenseService;
import pl.edu.pwr.wordnetloom.business.graph.entity.RootNode;
import pl.edu.pwr.wordnetloom.business.sense.enity.Sense;
import pl.edu.pwr.wordnetloom.business.synset.boundary.SynsetService;
import pl.edu.pwr.wordnetloom.business.synset.entity.Synset;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Stateless
public class GraphService {

    @PersistenceContext
    EntityManager em;

    @Inject
    SynsetService synsetService;

    @Inject
    SenseService senseService;

    @Inject
    EntityBuilder entityBuilder;

    @Inject
    LocalisedStringsLocator loc;

    private static final int NUMBER_OF_EXPANDED_NODES = 4;

    private List<Object[]> fetchSynsetGraphNode(Long id) {
        String query = "SELECT * FROM (SELECT rt1.node_position AS position," +
                "r1.child_synset_id AS c, rt1.short_display_text_id AS f, rt2.short_display_text_id AS t," +
                "concat(w.word, ' ', s.variant) AS lemma, dom1.name_id AS domain," +
                "s.part_of_speech_id AS pos FROM synset_relation r1 " +
                "LEFT JOIN relation_type rt1 ON r1.synset_relation_type_id = rt1.id " +
                "LEFT JOIN relation_type rt2 ON rt1.reverse_relation_type_id = rt2.id " +
                "LEFT JOIN synset_relation r2 ON r2.parent_synset_id = r1.child_synset_id AND rt1.reverse_relation_type_id = r2.synset_relation_type_id " +
                "LEFT JOIN sense s ON s.synset_id = r1.child_synset_id " +
                "LEFT JOIN word w ON w.id = s.word_id " +
                "LEFT JOIN domain dom1 ON  dom1.id = s.domain_id " +
                "WHERE r1.parent_synset_id = ?1 AND s.synset_position = ?2 AND rt1.node_position != 'IGNORE' " +
                "UNION SELECT " +
                "CASE WHEN rt1.node_position  = 'LEFT' THEN IF(rt1.id = rt2.id,'LEFT','RIGHT') " +
                "WHEN rt1.node_position  = 'RIGHT' THEN IF(rt1.id = rt2.id,'RIGHT','LEFT') " +
                "WHEN rt1.node_position  = 'TOP' THEN 'BOTTOM' " +
                "WHEN rt1.node_position  = 'BOTTOM' THEN 'TOP' " +
                "WHEN rt1.node_position  = 'IGNORE' THEN 'IGNORE' END AS position," +
                "r1.parent_synset_id AS c, rt2.short_display_text_id AS f, rt1.short_display_text_id AS t," +
                "concat(w.word, ' ', s.variant) AS lemma, dom1.name_id AS domain," +
                "s.part_of_speech_id AS pos FROM synset_relation r1 " +
                "LEFT JOIN relation_type rt1 ON r1.synset_relation_type_id = rt1.id " +
                "LEFT JOIN relation_type rt2 ON rt1.reverse_relation_type_id = rt2.id " +
                "LEFT JOIN synset_relation r2 ON r2.child_synset_id = r1.parent_synset_id AND rt1.reverse_relation_type_id = r2.synset_relation_type_id " +
                "LEFT JOIN sense s ON s.synset_id = r1.parent_synset_id " +
                "LEFT JOIN word w ON w.id = s.word_id " +
                "LEFT JOIN domain dom1 ON  dom1.id = s.domain_id " +
                "WHERE r1.child_synset_id = ?1 AND s.synset_position = ?2 AND rt1.node_position != 'IGNORE') AS t " +
                "ORDER BY t.position DESC, t.lemma, t.domain";

        return em.createNativeQuery(query)
                .setParameter(1, id)
                .setParameter(2, Synset.SYNSET_HEAD_POSITION)
                .getResultList();
    }

    private List<Object[]> fetchSenseGraphNode(Long id) {
        String query = "SELECT rt1.node_position, r1.child_sense_id AS c," +
                "rt1.short_display_text_id AS f,  rt2.short_display_text_id  AS t, " +
                "concat(w.word, ' ', s.variant) AS lemma,"+
                "dom1.name_id as domain,"+
                "s.part_of_speech_id AS pos "+
                "FROM sense_relation r1 " +
                "LEFT JOIN relation_type rt1 ON r1.relation_type_id = rt1.id " +
                "LEFT JOIN relation_type rt2 ON rt1.reverse_relation_type_id = rt2.id " +
                "LEFT JOIN sense_relation r2 ON r2.parent_sense_id = r1.child_sense_id AND rt1.reverse_relation_type_id = r2.relation_type_id " +
                "LEFT JOIN sense s ON s.id = r1.child_sense_id " +
                "LEFT JOIN word w ON w.id = s.word_id " +
                "LEFT JOIN domain dom1 ON  dom1.id = s.domain_id " +
                "WHERE r1.parent_sense_id = ?1 AND rt1.node_position != 'IGNORE' " +
                "UNION SELECT " +
                "CASE WHEN rt1.node_position  = 'LEFT' THEN IF(rt1.id = rt2.id,'LEFT','RIGHT') " +
                "WHEN rt1.node_position  = 'RIGHT' THEN IF(rt1.id = rt2.id,'RIGHT','LEFT') " +
                "WHEN rt1.node_position  = 'TOP' THEN 'BOTTOM' " +
                "WHEN rt1.node_position  = 'BOTTOM' THEN 'TOP' " +
                "WHEN rt1.node_position  = 'IGNORE' THEN 'IGNORE' " +
                "END AS position," +
                "r1.parent_sense_id AS c," +
                "rt2.short_display_text_id AS f, rt1.short_display_text_id AS t,"+
                "concat(w.word, ' ', s.variant) AS lemma," +
                "dom1.name_id as domain,"+
                "s.part_of_speech_id AS pos " +
                "FROM sense_relation r1 " +
                "LEFT JOIN relation_type rt1 ON r1.relation_type_id = rt1.id " +
                "LEFT JOIN relation_type rt2 ON rt1.reverse_relation_type_id = rt2.id " +
                "LEFT JOIN sense_relation r2 ON r2.child_sense_id = r1.parent_sense_id AND rt1.reverse_relation_type_id = r2.relation_type_id " +
                "LEFT JOIN sense s ON s.id = r1.parent_sense_id " +
                "LEFT JOIN word w ON w.id = s.word_id " +
                "LEFT JOIN domain dom1 ON  dom1.id = s.domain_id " +
                "WHERE r1.child_sense_id = ?1 AND rt1.node_position != 'IGNORE'" +
                "UNION SELECT 'LEFT' AS np, z.id AS id, 999 AS f,999 AS t," +
                "concat(w.word, ' ', z.variant) AS lemma," +
                "dom1.name_id as domain,"+
                "z.part_of_speech_id AS pos " +
                "FROM sense z " +
                "LEFT JOIN word w ON w.id = z.word_id " +
                "LEFT JOIN domain dom1 ON  dom1.id = z.domain_id " +
                "WHERE z.synset_id = (SELECT zs.synset_id FROM sense zs WHERE zs.id = ?1) " +
                "and z.synset_position > 0";

        return em.createNativeQuery(query)
                .setParameter(1, id)
                .getResultList();
    }

    private NodeExpanded hiddenNode(final Long parentId, final NodeHidden source, Locale locale, boolean isSynset) {

        List<Object[]> result;
        if (isSynset) {
            result = fetchSynsetGraphNode(source.getId());
        } else {
            result = fetchSenseGraphNode(source.getId());
        }

        NodeExpanded node = new NodeExpanded(new RootNode(source.getId(), source.getPos(), source.getLabel()), source.getRel());

        result.stream()
                .map(o ->  buildNodeHidden(locale,o[0],o[1],o[2],o[3],o[4],o[5],o[6]))
                .collect(Collectors.toList())
                .stream()
                .collect(Collectors.groupingBy(NodeHidden::getPosition))
                .forEach((k, v) -> {
                    if (k.equals("TOP")) {
                        loadHidden(parentId, v, node::addTopHidden);
                    }
                    if (k.equals("BOTTOM")) {
                        loadHidden(parentId, v, node::addBottomHidden);
                    }
                    if (k.equals("RIGHT")) {
                        loadHidden(parentId, v, node::addRightHidden);
                    }
                    if (k.equals("LEFT")) {
                        loadHidden(parentId, v, node::addLeftHidden);
                    }
                });
        return node;
    }

    private void loadHidden(Long parentId, List<NodeHidden> v, Consumer<NodeHidden> consumer) {
        v.stream()
                .filter(i -> !i.getId().equals(parentId))
                .forEach(consumer);
    }


    public NodeExpanded synsetGraph(final Long id, Locale locale) {
        List<Object[]> result = fetchSynsetGraphNode(id);
        RootNode rn = synsetService.findSynsetHead(id)
                .map(s -> new RootNode(s.getId(),
                        s.getSenses().stream().findFirst().get().getPartOfSpeech().getId(),
                        entityBuilder.buildLabel(s,locale)) )
                .orElse(new RootNode());
        NodeExpanded node = new NodeExpanded(rn, null);
        return graph(result, node, locale, true);
    }

    public NodeExpanded senseGraph(final Long id, Locale locale) {
        List<Object[]> result = fetchSenseGraphNode(id);
        Sense s = senseService.findHeadSense(id).get();
        NodeExpanded node = new NodeExpanded(new RootNode(s.getId(), s.getPartOfSpeech().getId(),
                s.getWord().getWord()+" "+s.getVariant()+ " (" + loc.find(s.getDomain().getName(), locale) + ")"), null);
        return graph(result, node, locale, false);
    }

    private NodeHidden buildNodeHidden(Locale locale, Object position, Object target, Object firstRelation, Object secondRelation, Object label, Object domain, Object pos) {
        String r1 = firstRelation != null ? loc.find(((BigInteger) firstRelation).longValue(), locale) : null;
        String r2 = secondRelation != null ? loc.find(((BigInteger) secondRelation).longValue(), locale) : null;
        String dom = domain != null ? "(" + loc.find(((BigInteger) domain).longValue(), locale) + ")" : "";
        return new NodeHidden(position, target, r1, r2, label, dom, pos);
    }

    private NodeExpanded graph(final List<Object[]> result, final NodeExpanded node, Locale locale, boolean isSynset) {

        result.stream()
                .map(o ->  buildNodeHidden(locale,o[0],o[1],o[2],o[3],o[4],o[5],o[6]))
                .collect(Collectors.toList())
                .stream()
                .collect(Collectors.groupingBy(NodeHidden::getPosition))
                .forEach((k, v) -> {
                    if (k.equals("TOP")) {
                        v.stream()
                                .limit(NUMBER_OF_EXPANDED_NODES)
                                .forEach(i -> node.addTopExpanded(hiddenNode(node.getId(), i, locale, isSynset)));
                        v.stream()
                                .skip(NUMBER_OF_EXPANDED_NODES)
                                .forEach(node::addTopHidden);
                    }
                    if (k.equals("BOTTOM")) {
                        v.stream()
                                .limit(NUMBER_OF_EXPANDED_NODES)
                                .forEach(i -> node.addBottomExpanded(hiddenNode(node.getId(), i, locale, isSynset)));
                        v.stream()
                                .skip(NUMBER_OF_EXPANDED_NODES).forEach(node::addBottomHidden);
                    }
                    if (k.equals("RIGHT")) {
                        v.stream()
                                .limit(NUMBER_OF_EXPANDED_NODES)
                                .forEach(i -> node.addRightExpanded(hiddenNode(node.getId(), i, locale, isSynset)));
                        v.stream()
                                .skip(NUMBER_OF_EXPANDED_NODES).forEach(node::addRightHidden);
                    }
                    if (k.equals("LEFT")) {
                        v.stream()
                                .limit(NUMBER_OF_EXPANDED_NODES)
                                .forEach(i -> node.addLeftExpanded(hiddenNode(node.getId(), i, locale, isSynset)));
                        v.stream()
                                .skip(NUMBER_OF_EXPANDED_NODES).forEach(node::addLeftHidden);
                    }
                });
        return node;
    }

}
