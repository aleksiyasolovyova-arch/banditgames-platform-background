package team11.platform_backend.game.adapter.in.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateAchievementRequest(
        @NotNull(message = "Game ID cannot be null")
        UUID gameId,

        @NotNull(message = "Achievement name cannot be null")
        @NotBlank(message = "Achievement name cannot be blank")
        @Size(max = 100, message = "Achievement name cannot exceed 100 characters")
        String achievementName,

        @NotNull(message = "Achievement description cannot be null")
        @NotBlank(message = "Achievement description cannot be blank")
        @Size(max = 255, message = "Achievement description cannot exceed 255 characters")
        String achievementDescription,

        @NotNull(message = "Picture URL cannot be null")
        @NotBlank(message = "Picture URL cannot be blank")
        String pictureUrl,

        @NotNull(message = "Achievement type cannot be null")
        @NotBlank(message = "Achievement type cannot be blank")
        String achievementType, // GAME_PLAYED, GAME_WON, FRIENDS_MADE, RECORD_TIME

        @NotNull(message = "Threshold cannot be null")
        @DecimalMin(value = "0.01", message = "Threshold must be greater than 0")
        BigDecimal threshold
) {
}
