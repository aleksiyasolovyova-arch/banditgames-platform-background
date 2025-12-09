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
    private String gameName;

    @Column(nullable = false, length = 500)
    private String gameDescription;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal gamePrice;

    @Column(nullable = false)
    private String pictureUrls;

    @Column(nullable = false)
    private String gameUrl;

    @Column(nullable = false)
    private String gameCreatorName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GameRegistrationState gameRegistrationState;

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

    public String getGameName() {
        return gameName;
    }

    public String getGameDescription() {
        return gameDescription;
    }

    public BigDecimal getGamePrice() {
        return gamePrice;
    }

    public String getPictureUrls() {
        return pictureUrls;
    }

    public String getGameUrl() {
        return gameUrl;
    }

    public String getGameCreatorName() {
        return gameCreatorName;
    }

    public GameRegistrationState getGameRegistrationState() {
        return gameRegistrationState;
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

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public void setGameDescription(String gameDescription) {
        this.gameDescription = gameDescription;
    }

    public void setGamePrice(BigDecimal gamePrice) {
        this.gamePrice = gamePrice;
    }

    public void setPictureUrls(String pictureUrls) {
        this.pictureUrls = pictureUrls;
    }

    public void setGameUrl(String gameUrl) {
        this.gameUrl = gameUrl;
    }

    public void setGameCreatorName(String gameCreatorName) {
        this.gameCreatorName = gameCreatorName;
    }

    public void setGameRegistrationState(GameRegistrationState gameRegistrationState) {
        this.gameRegistrationState = gameRegistrationState;
    }

    public void setRules(List<String> rules) {
        this.rules = rules;
    }

    public void setAchievements(List<GameAchievementEmbeddable> achievements) {
        this.achievements = achievements;
    }
}
