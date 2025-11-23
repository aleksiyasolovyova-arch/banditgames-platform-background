package team11.platform_backend.game.domain.game;

import java.util.UUID;

public record RuleId(
        UUID ruleId
) {

    public static RuleId createRuleId() {
        return new RuleId(UUID.randomUUID());
    }
}
