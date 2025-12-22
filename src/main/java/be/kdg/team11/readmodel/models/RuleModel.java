package be.kdg.team11.readmodel.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "rule", schema = "read_model_schema")
public class RuleModel {
    @Id
    @Column(name = "rule_id")
    private UUID ruleId;

    @Column(name = "game_id", nullable = false)
    private UUID gameId;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    public RuleModel() {}

    public UUID getRuleId() {
        return ruleId;
    }

    public void setRuleId(UUID ruleId) {
        this.ruleId = ruleId;
    }

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
