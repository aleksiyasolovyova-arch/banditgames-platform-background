package team11.platform_backend.game.adapter.out.jpa;

import jakarta.persistence.*;
import team11.platform_backend.game.domain.game.GameRegistrationState;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "game", schema = "game_schema")
public class GameJpaEntity {
    @Id
    private UUID gameId;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 500)
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private String pictureUrl;

    @Column(nullable = false)
    private String gameUrl;

    @Column(nullable = false)
    private String gameCreatorName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GameRegistrationState registrationState;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "game_rules", schema = "game_schema",
            joinColumns = @JoinColumn(name = "game_id"))
    @Column(name = "description", length = 255)
    private List<String> rules = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "game_achievements", schema = "game_schema",
            joinColumns = @JoinColumn(name = "game_id"))
    private List<GameAchievementEmbeddable> achievements = new ArrayList<>();

    public GameJpaEntity() {}

    public UUID getGameId() {
        return gameId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public String getGameUrl() {
        return gameUrl;
    }

    public String getGameCreatorName() {
        return gameCreatorName;
    }

    public GameRegistrationState getRegistrationState() {
        return registrationState;
    }

    public List<String> getRules() {
        return rules;
    }

    public List<GameAchievementEmbeddable> getAchievements() {
        return achievements;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public void setGameUrl(String gameUrl) {
        this.gameUrl = gameUrl;
    }

    public void setGameCreatorName(String gameCreatorName) {
        this.gameCreatorName = gameCreatorName;
    }

    public void setRegistrationState(GameRegistrationState registrationState) {
        this.registrationState = registrationState;
    }

    public void setRules(List<String> rules) {
        this.rules = rules;
    }

    public void setAchievements(List<GameAchievementEmbeddable> achievements) {
        this.achievements = achievements;
    }
}
