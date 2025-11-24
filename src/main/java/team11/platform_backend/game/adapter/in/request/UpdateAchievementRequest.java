package team11.platform_backend.game.adapter.in.request;
import jakarta.validation.constraints.*;
import team11.platform_backend.game.domain.achievement.AchievementType;

import java.math.BigDecimal;

public record UpdateAchievementRequest(
        @NotNull(message = "Achievement name cannot be null")
        @NotBlank(message = "Achievement name cannot be blank")
        @Size(max = 100, message = "Achievement name cannot exceed 100 characters")
        String achievementName,

        @NotNull(message = "Achievement description cannot be null")
        @NotBlank(message = "Achievement description cannot be blank")
        @Size(max = 500, message = "Achievement description cannot exceed 500 characters")
        String achievementDescription,

        @NotNull(message = "Picture URL cannot be null")
        @NotBlank(message = "Picture URL cannot be blank")
        String pictureUrl,

        @NotNull(message = "Achievement type cannot be null")
        AchievementType achievementType,

        @NotNull(message = "Threshold cannot be null")
        @DecimalMin(value = "0.0", inclusive = false, message = "Threshold must be greater than zero")
        BigDecimal threshold
) {
}
