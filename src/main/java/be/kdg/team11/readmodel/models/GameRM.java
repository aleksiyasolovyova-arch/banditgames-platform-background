package be.kdg.team11.readmodel.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "game", schema = "read_model_schema")
public class GameRM {
    @Id
    private UUID gameId;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 500)
    private String description;

    @Column(nullable = false)
    private String pictureUrl;

    @Column(nullable = false)
    private String gameUrl;

    @Column(nullable = false)
    private String gameCreatorName;

    @ElementCollection
    @CollectionTable(
            name = "game_rules",
            schema = "content_schema",
            joinColumns = @JoinColumn(name = "game_id")
    )
    @Column(name = "rule", nullable = false)
    private List<String> rules = new ArrayList<>();

    public GameRM() {
    }

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getGameUrl() {
        return gameUrl;
    }

    public void setGameUrl(String gameUrl) {
        this.gameUrl = gameUrl;
    }

    public String getGameCreatorName() {
        return gameCreatorName;
    }

    public void setGameCreatorName(String gameCreatorName) {
        this.gameCreatorName = gameCreatorName;
    }

    public List<String> getRules() {
        return rules;
    }

    public void setRules(List<String> rules) {
        this.rules = rules;
    }
}
