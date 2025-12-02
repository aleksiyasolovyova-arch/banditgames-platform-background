package team11.platform_backend.game.adapter.in.response;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record GameDto(
        UUID gameId,
        String gameName,
        String gameDescription,
        BigDecimal gamePrice,
        List<String> pictureUrls,
        String gameCreatorName,
        String gameUrl,
        String gameState,
        List<RuleDto> rules
) {
    public record RuleDto(
            String ruleName,
            String ruleDescription,
            List<String> ruleCategories
    ) {}
}
