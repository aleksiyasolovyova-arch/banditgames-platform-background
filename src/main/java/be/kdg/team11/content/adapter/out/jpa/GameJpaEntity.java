package be.kdg.team11.content.adapter.out.jpa;

import be.kdg.team11.content.domain.game.GameReviewState;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "game", schema = "content_schema")
public class GameJpaEntity {
    @Id
    private UUID gameId;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 500)
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "picture_url")
    private String pictureUrl;

    @Column(nullable = false)
    private String gameUrl;

    @Column(nullable = false)
    private String gameCreatorName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GameReviewState reviewState;

    @ElementCollection
    @CollectionTable(
            name = "game_rules",
            schema = "content_schema",
            joinColumns = @JoinColumn(name = "game_id")
    )
    @Column(name = "rule", nullable = false)
    private List<String> rules = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "game_achievements",
            schema = "content_schema",
            joinColumns = @JoinColumn(name = "game_id")
    )
    private List<GameAchievementEmbeddable> achievementEmbeddables = new ArrayList<>();

    public GameJpaEntity() {
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    public GameReviewState getReviewState() {
        return reviewState;
    }

    public void setReviewState(GameReviewState reviewState) {
        this.reviewState = reviewState;
    }

    public List<String> getRules() {
        return rules;
    }

    public void setRules(List<String> rules) {
        this.rules = rules;
    }

    public List<GameAchievementEmbeddable> getAchievementEmbeddables() {
        return achievementEmbeddables;
    }

    public void setAchievementEmbeddables(List<GameAchievementEmbeddable> achievementEmbeddables) {
        this.achievementEmbeddables = achievementEmbeddables;
    }
}
