package be.kdg.team11.readmodel.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "game", schema = "read_model_schema")
public class GameModel {
    @Id
    @Column()
    private UUID gameId;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column()
    private String pictureUrl;

    @Column()
    private String gameUrl;

    @Column()
    private String creatorName;

    @Column
    private boolean pending;

    @Column()
    private boolean playableWithAI;

    // Timestamps
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column()
    private LocalDateTime updatedAt;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "game_rule",
            schema = "read_model_schema",
            joinColumns = @JoinColumn(name = "game_id")
    )
    private List<GameRule> rules = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "game_achievement",
            schema = "read_model_schema",
            joinColumns = @JoinColumn(name = "game_id")
    )
    private List<GameAchievement> achievements = new ArrayList<>();




    public GameModel() {
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

    public boolean isPending() {
        return pending;
    }

    public void setPending(boolean pending) {
        this.pending = pending;
    }

    public boolean isPlayableWithAI() {
        return playableWithAI;
    }

    public void setPlayableWithAI(boolean playableWithAI) {
        this.playableWithAI = playableWithAI;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<GameRule> getRules() {
        return rules;
    }

    public void setRules(List<GameRule> rules) {
        this.rules = rules;
    }

    public List<GameAchievement> getAchievements() {
        return achievements;
    }
    public void setAchievements(List<GameAchievement> achievements) {
        this.achievements = achievements;
    }

    @Embeddable
    public static class GameRule {
        private UUID ruleId;
        private String description;

        public UUID getRuleId() {
            return ruleId;
        }

        public void setRuleId(UUID ruleId) {
            this.ruleId = ruleId;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

    }

    @Embeddable
    public static class GameAchievement {
        private String code;
        private String description;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
