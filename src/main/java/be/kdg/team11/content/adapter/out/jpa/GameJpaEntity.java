package be.kdg.team11.content.adapter.out.jpa;

import jakarta.persistence.*;
import team11.platform_backend.game.domain.game.GameState;

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

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "game_picture_urls", joinColumns = @JoinColumn(name = "game_id"), schema = "game_schema")
    @Column(name = "picture_url")
    private List<String> pictureUrls = new ArrayList<>();

    @Column(nullable = false)
    private String gameCreatorName;

    @Column(nullable = false)
    private String gameUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GameState gameState;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<RuleJpaEntity> rules = new ArrayList<>();

    public GameJpaEntity() {}

    // Getters and Setters
    public UUID getGameId() { return gameId; }
    public void setGameId(UUID gameId) { this.gameId = gameId; }

    public String getGameName() { return gameName; }
    public void setGameName(String gameName) { this.gameName = gameName; }

    public String getGameDescription() { return gameDescription; }
    public void setGameDescription(String gameDescription) { this.gameDescription = gameDescription; }

    public BigDecimal getGamePrice() { return gamePrice; }
    public void setGamePrice(BigDecimal gamePrice) { this.gamePrice = gamePrice; }

    public List<String> getPictureUrls() { return pictureUrls; }
    public void setPictureUrls(List<String> pictureUrls) { this.pictureUrls = pictureUrls; }

    public String getGameCreatorName() { return gameCreatorName; }
    public void setGameCreatorName(String gameCreatorName) { this.gameCreatorName = gameCreatorName; }

    public String getGameUrl() { return gameUrl; }
    public void setGameUrl(String gameUrl) { this.gameUrl = gameUrl; }

    public GameState getGameState() { return gameState; }
    public void setGameState(GameState gameState) { this.gameState = gameState; }

    public List<RuleJpaEntity> getRules() { return rules; }
    public void setRules(List<RuleJpaEntity> rules) { this.rules = rules; }
}
