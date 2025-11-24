package team11.platform_backend.game.adapter.out.jpa;

import jakarta.persistence.*;
import team11.platform_backend.game.domain.game.RuleCategory;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "rules")
public class RuleJpaEntity {

    @Id
    private UUID ruleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", nullable = false)
    private GameJpaEntity game;

    @Column(nullable = false)
    private String ruleName;

    @Column(nullable = false)
    private String ruleDescription;

    @ElementCollection(targetClass = RuleCategory.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "rule_categories", joinColumns = @JoinColumn(name = "rule_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Set<RuleCategory> ruleCategories = new HashSet<>();

    // Constructors
    public RuleJpaEntity() {}

    public RuleJpaEntity(GameJpaEntity game, String ruleName, String ruleDescription, Set<RuleCategory> ruleCategories) {
        this.ruleId = UUID.randomUUID();
        this.game = game;
        this.ruleName = ruleName;
        this.ruleDescription = ruleDescription;
        this.ruleCategories = ruleCategories;
    }

    // Getters and Setters
    public UUID getRuleId() { return ruleId; }
    public void setRuleId(UUID ruleId) { this.ruleId = ruleId; }

    public GameJpaEntity getGame() { return game; }
    public void setGame(GameJpaEntity game) { this.game = game; }

    public String getRuleName() { return ruleName; }
    public void setRuleName(String ruleName) { this.ruleName = ruleName; }

    public String getRuleDescription() { return ruleDescription; }
    public void setRuleDescription(String ruleDescription) { this.ruleDescription = ruleDescription; }

    public Set<RuleCategory> getRuleCategories() { return ruleCategories; }
    public void setRuleCategories(Set<RuleCategory> ruleCategories) { this.ruleCategories = ruleCategories; }
}
