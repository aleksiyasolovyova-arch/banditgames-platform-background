package team11.platform_backend.game.domain.game;

import java.util.ArrayList;
import java.util.List;

public record Rule(
        String ruleName,
        String ruleDescription,
        List<RuleCategory> ruleCategories

){
    public Rule(String ruleName, String ruleDescription, List<RuleCategory> ruleCategories) {
        if (ruleName == null || ruleDescription == null || ruleCategories == null) {
            throw new IllegalArgumentException("Rule name, description and categories cannot be null");
        } if (!isStringLengthInRange(ruleName, 1, 255) || !isStringLengthInRange(ruleDescription, 1, 255)) {
            throw new IllegalArgumentException("Rule name and description must be between 1 and 255 characters");
        }
        this.ruleName = ruleName;
        this.ruleDescription = ruleDescription;
        this.ruleCategories = new ArrayList<>(ruleCategories);
    }

    private boolean isStringLengthInRange(String string, int min, int max) {
        return string.length() >= min && string.length() <= max;
    }
}
