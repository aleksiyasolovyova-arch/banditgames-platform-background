package team11.platform_backend.game.adapter.in.response;
import java.math.BigDecimal;
import java.util.UUID;

public record AchievementDto(
        UUID achievementId,
        UUID gameId,
        String achievementName,
        String achievementDescription,
        String pictureUrl,
        String achievementType,
        BigDecimal threshold
) {}
