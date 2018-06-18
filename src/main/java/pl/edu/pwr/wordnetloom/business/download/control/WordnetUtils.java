package pl.edu.pwr.wordnetloom.business.download.control;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class WordnetUtils {

    private static List<Long> multiligualRelations = Arrays.asList(197l,
            198l, 199l, 200l, 201l, 202l, 203l, 204l, 205l, 206l, 207l, 208l, 209l, 210l, 211l, 212l, 213l,
            214l, 215l, 216l, 217l, 218l, 219l, 222l, 223l, 227l, 228l, 229l, 232l, 235l, 238l, 239l, 3004l, 3005l, 3006l, 3016l, 3015l, 3014l, 3013l);

    public static boolean isMultiligualRelation(Long id) {
        return multiligualRelations.contains(id);
    }

    public static String getPartOfSpeech(long pos) {
        switch (java.lang.Math.toIntExact(pos)) {
            case 1:
                return "v";
            case 2:
                return "n";
            case 3:
                return "r";
            case 4:
                return "a";
            default:
                return "u";
        }
    }

    public static String mapRelType(long r) {
        // Relations only in plWordNet
        switch (java.lang.Math.toIntExact(r)) {
            case 10:
                return "hyponym";
            case 11:
                return "hypernym";
            case 13:
                return "antonym"; //conversion -> antonym
            case 19:
                return "other"; //"fuzzynym";
            case 20:
                return "mero_part";
            case 21:
                return "mero_portion";
            case 22:
                return "mero_location";
            case 23:
                return "mero_member";
            case 24:
                return "mero_substance";
            case 25:
                return "holo_part";
            case 26:
                return "holo_portion";
            case 27:
                return "holo_location";
            case 28:
                return "holo_member";
            case 29:
                return "holo_substance";
            case 34:
                return "derivation"; //"agent";
            case 35:
                return "derivation"; // "patient";
            case 36:
                return "derivation"; //"instrument";
            case 37:
                return "derivation"; //"location";
            case 38:
                return "derivation"; //"result";
            case 39:
                return "derivation"; //time;
            case 40:
                return "derivation"; // "role";
            case 41:
                return "derivation"; //"involved_agent";
            case 42:
                return "derivation"; //"involved_patient";
            case 43:
                return "derivation"; //involved_time";
            case 44:
                return "derivation"; //"involved_location";
            case 45:
                return "derivation"; //"involved_instrument";
            case 46:
                return "derivation"; //"involved_result";
            case 47:
                return "derivation"; // "involved";
            case 48:
                return "derivation"; //agent_hidden;
            case 49:
                return "derivation"; //"location_hidden";
            case 50:
                return "derivation"; //"result_hidden";
            case 51:
                return "be_in_state";
            case 52:
                return "state_of";//"state"
            case 53:
                return "hyponym";//"feminine"
            case 55:
                return "hyponym";//"young"
            case 56:
                return "hyponym";//"diminutive"
            case 57:
                return "hyponym";//"augmentative
            case 58:
                return "other"; //"inhabitant";
            case 59:
                return "derivation"; // "derivation"
            case 60:
                return "other";//"inter_register_synonym"; -> eq_synonym
            case 62:
                return "pertainym";// "noun_verb_cross_category_synonym";
            case 63:
                return "other";//"noun_adjective_cross_category_synonym";
            case 64:
                return "mero_member";//"taxonomic_meronym";
            case 65:
                return "holo_member";//"taxonomic_holonym";
            case 74:
                return "derivation"; //"pure_perfective_imperfective";
            case 75:
                return "derivation"; //"pure_imperfective_perfective";
            case 89:
                return "other"; //"imperfective_verb_adjective_processuality";
            case 90:
                return "other"; //"imperfective_verb_noun_processuality";
            case 92:
                return "state_of"; //"verb_adjective_state";
            case 93:
                return "state_of"; //"verb_noun_state";
            case 101:
                return "antonym"; //"complementary_antonym";
            case 104:
                return "antonym"; //"proper_antonym";
            case 106:
                return "instance_hyponym"; //"type"; -> instance_hyponym
            case 107:
                return "other";// "instance_hypernym";
            case 108:
                return "other";//"synset_fuzzynym";
            case 110:
                return "derivation"; //"secondary_aspect_perfective_imperfective";
            case 111:
                return "derivation"; //"secondary_aspect_imperfective_perfective";
            case 113:
                return "is_entailed_by";//"meronym_of_substituation"; -> is_entailed_by
            case 114:
                return "is_subevent_of";//"meronym_of_accompanying_situation"; -> subevent_of
            case 116:
                return "entails";//"holonym_of_substituation";
            case 117:
                return "subevent";//"holonym_of_accompanying_situation";
            case 118:
                return "other";//"verb_perfective_adjective_processuality";
            case 119:
                return "other";//"verb_perfective_noun_processuality";
            case 120:
                return "causes";//"cause_imperfective_imperfective";
            case 121:
                return "causes";//"cause_perfective_perfective";
            case 122:
                return "causes";//"perfective_imperfective_cause_of_state";
            case 124:
                return "causes";//"perfective_adjective_cause_of_process";
            case 125:
                return "causes";//"perfective_noun_cause_of_process";
            case 126:
                return "causes";//"imperfective_adjective_cause_of_process";
            case 127:
                return "causes";//"imperfective_noun_cause_of_process";
            case 129:
                return "also"; //"perfective_imperfective";
            case 130:
                return "also"; //"imperfective_imperfective";
            case 131:
                return "other";
            case 134:
                return "also";//"iterative_imperfective_imperfective";
            case 136:
                return "also"; //"distributive";
            case 137:
                return "entails";//presuppositional"; -> entails
            case 138:
                return "also";//"preceding"; -> also
            case 140:
                return "also"; //"iterative_imperfective_perfective";
            case 141:
                return "other";//"verb_noun_cross_category_synonym";
            case 142:
                return "pertainym";//"adjective_noun_cross_category_synonym"; -> pertainym
            case 145:
                return "other";//"value_of_attribute"; ->attribute
            case 146:
                return "other";//"modifier";
            case 147:
                return "other";//"gradation"; -> also
            case 148:
                return "derivation";//"similititudinal_meaning";
            case 149:
                return "derivation";//"characterizing";
            case 151:
                return "derivation";//"comparative";
            case 152:
                return "derivation";//"superlative";
            case 154:
                return "derivation"; //"agent";
            case 155:
                return "derivation";// "patient";
            case 156:
                return "derivation";//"instrument";
            case 157:
                return "derivation";//"location";
            case 158:
                return "derivation";//time";
            case 160:
                return "derivation";//"result";
            case 161:
                return "derivation";//"cause";
            case 163:
                return "derivation";//"potential";
            case 164:
                return "derivation";//"habitual";
            case 165:
                return "derivation";//"quantitative";
            case 166:
                return "derivation";//"evaluation";
            case 168:
                return "derivation";//"markedness_of_intensity";
            case 169:
                return "other";//"cross_category_synonym_for_relational_adjectives"; -> pertainym
            case 194:
                return "other";//"participle_of_verb"; -> participle
            case 197:
                return "hyponym";
            case 198:
                return "eq_synonym";
            case 201:
                return "mero_part";
            case 202:
                return "mero_member";
            case 203:
                return "mero_substance";
            case 205:
                return "holo_part";
            case 206:
                return "holo_member";
            case 207:
                return "holo_substance";
            case 211:
                return "hyponym";
            case 212:
                return "hypernym";
            case 214:
                return "mero_part";
            case 215:
                return "mero_member";
            case 216:
                return "mero_substance";
            case 217:
                return "holo_part";
            case 218:
                return "holo_member";
            case 219:
                return "holo_substance";
            case 225:
                return "similar";//near_synonym";
            case 226:
                return "similar";//"near_synonym";
            case 228:
                return "similar";//"inter_register_synonym";
            case 229:
                return "similar";//"inter_register_synonym";
            case 230:
                return "instance_hyponym"; // To Sumo
            case 235:
                return "other";
            case 238:
                return "other";
            case 239:
                return "other";
            case 242:
                return "derivation";
            case 244:
                return "derivation";
            case 3000:
                return "eq_synonym"; // ???
            case 3001:
                return "hyponym";// ??? to skip
            case 3002:
                return "instance_hyponym";// ??? to skip
            case 3003:
                return "other";// ??? to skip
            case 3004:
                return "other";// ??? to skip
            case 3005:
                return "other";// ??? to skip
            case 3006:
                return "other";// ??? to skip
            case 3016:
                return "instance_hypernym";
            case 3015:
                return "instance_hyponym";
            case 3014:
                return "instance_hypernym";
            case 3013:
                return "instance_hyponym";
            // Relations in both resources
            case 170:
                return "antonym";
            case 171:
                return "hyponym";
            case 172:
                return "instance_hyponym";
            case 173:
                return "hypernym";
            case 174:
                return "instance_hypernym";
            case 175:
                return "mero_member";
            case 176:
                return "mero_substance";
            case 177:
                return "mero_part";
            case 178:
                return "holo_member";
            case 179:
                return "holo_substance";
            case 180:
                return "holo_part";
            case 181:
                return "attribute";
            case 182:
                return "derivation";
            case 183:
                return "has_domain_topic";
            case 184:
                return "domain_topic";
            case 185:
                return "has_domain_region";
            case 186:
                return "domain_region";
            case 187:
                return "exemplifies";
            case 188:
                return "is_exemplified_by";
            case 189:
                return "entails";
            case 190:
                return "causes";
            case 191:
                return "also";
            case 192:
                return "similar";
            case 193:
                return "similar";
            case 195:
                return "pertainym";
            case 208:
                return "eq_synonym"; // eq_synonym2
            case 209:
                return "eq_synonym"; // eq_synonym3
            case 210:
                return "hyponym"; //
            case 213:
                return "hypernym"; //
            case 222:
                return "other"; // automatic_prompt2
            case 223:
                return "other"; // automatic_prompt3
            default:
                return "other";
        }
    }

    public static boolean hasPolishCharacters(String str) {
        boolean[] result = {false};
        List<String> chars = Arrays.asList(
                "ą", "ć", "ę", "ł", "ś", "ź", "ż", "ó", "ń");
        chars.forEach(c -> {
            if (str.contains(c)) {
                result[0] = true;
                return;
            }
        });
        return result[0];
    }

    public static String replacePolishCharcacters(String str) {
        Map<String, String> chars = new HashMap<String, String>() {{
            put("ą", "a");
            put("ć", "c");
            put("ę", "e");
            put("ł", "l");
            put("ś", "s");
            put("ź", "z");
            put("ż", "z");
            put("ó", "o");
            put("ń", "n");
        }};
        String[] result = {str};
        chars.forEach((k, v) ->  result[0] = result[0].replace(k, v));
        return result[0];
    }

    public static boolean convertSenseRelationToSynset(Long relationId) {
        return sensToSynsetRelationMapping.containsKey(relationId);
    }

    public static String getConvertSenseRelationToSynset(Long relationId) {
        if (convertSenseRelationToSynset(relationId)) {
            return sensToSynsetRelationMapping.get(relationId);
        }
        return null;
    }

    private static Map<Long, String> sensToSynsetRelationMapping = new ConcurrentHashMap<Long, String>() {{
        put(34l, "agent");
        put(35l, "patient");
        put(36l, "instrument");
        put(37l, "location");
        put(38l, "result");
        put(39l, "role");
        put(40l, "role");
        put(41l, "involved_agent");
        put(42l, "involved_patient");
        put(43l, "involved");
        put(44l, "involved_location");
        put(45l, "involved_instrument");
        put(46l, "involved_result");
        put(47l, "involved");
        put(48l, "agent");
        put(49l, "location");
        put(50l, "result");
        put(51l, "be_in_state");
        put(52l, "state_of");
        put(53l, "hyponym");
        put(55l, "hyponym");
        put(56l, "hyponym");
        put(57l, "hyponym");
        put(154l, "agent");
        put(155l, "patient");
        put(156l, "instrument");
        put(157l, "location");
        put(158l, "role");
        put(160l, "result");
        put(161l, "role");

    }};
}
