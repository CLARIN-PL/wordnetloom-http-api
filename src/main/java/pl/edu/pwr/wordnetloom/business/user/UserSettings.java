package pl.edu.pwr.wordnetloom.business.user;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users_settings")
public class UserSettings implements Serializable {

    @Id
    private long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @MapsId
    private User user;

    @Column(name = "lexicon_marker")
    private Boolean lexionMarker = true;

    @Column(name = "chosen_lexicons")
    private String chosenLexicons;

    @Column(name = "show_tool_tips")
    private Boolean showToolTips = true;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getLexionMarker() {
        return lexionMarker;
    }

    public void setLexionMarker(Boolean lexionMarker) {
        this.lexionMarker = lexionMarker;
    }

    public String getChosenLexicons() {
        return chosenLexicons;
    }

    public void setChosenLexicons(String chosenLexicons) {
        this.chosenLexicons = chosenLexicons;
    }

    public Boolean getShowToolTips() {
        return showToolTips;
    }

    public void setShowToolTips(Boolean showToolTips) {
        this.showToolTips = showToolTips;
    }
}
