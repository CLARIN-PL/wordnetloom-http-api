package pl.edu.pwr.wordnetloom.business;

import pl.edu.pwr.wordnetloom.business.localistaion.entity.LocalisedString;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Startup
@Singleton
public class LocalisedStringsLocator {

    @PersistenceContext
    EntityManager em;

    private Map<String, Map<Long, String>> localisedStrings;

    private final String defaultLanguage = "en";

    @PostConstruct
    void init() {
        localisedStrings = new ConcurrentHashMap<>(findAll());
    }

    public String find(final Long id, final Locale locale) {
        String lang = defaultLanguage;
        if (locale != null) {
            String l = locale.getLanguage().substring(0, 2);
            lang = localisedStrings.containsKey(l) ? l : defaultLanguage;
        }

        if (localisedStrings.get(lang).containsKey(id)) {
            return localisedStrings.get(lang).get(id);
        }
        return id != null ? id.toString() : null;
    }

    private Map<String, Map<Long, String>> findAll() {
        final List<LocalisedString> list = em.createNamedQuery(LocalisedString.FIND_ALL, LocalisedString.class)
                .getResultList();
       return list.stream()
                .collect(Collectors.groupingBy(o -> o.getKey().getLanguage(),
                        Collectors.toMap(c -> c.getKey().getId(), LocalisedString::getValue)));
    }
}
