package be.kdg.team11.content.adapter.in.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

public record RegisterGameRequest(
        @NotNull(message = "Game name cannot be null")
        @NotBlank(message = "Game name cannot be blank")
        @Size(min = 1, max = 255, message = "Game name must be between 1 and 255 characters")
        String name,

        @NotNull(message = "Game description cannot be null")
        @NotBlank(message = "Game description cannot be blank")
        @Size(min = 5, max = 500, message = "Game description must be between 5 and 500 characters")
        String description,

        @NotNull(message = "Game price cannot be null")
        @DecimalMin(value = "0.0", inclusive = true, message = "Game price cannot be negative")
        BigDecimal price,

        @NotNull(message = "Picture URL cannot be null")
        @NotBlank(message = "Picture URL cannot be blank")
        String pictureUrl,

        @NotNull(message = "Game URL cannot be null")
        @NotBlank(message = "Game URL cannot be blank")
        String gameUrl,

        @NotNull(message = "Game creator name cannot be null")
        @NotBlank(message = "Game creator name cannot be blank")
        @Size(min = 1, max = 100, message = "Creator name must be between 1 and 100 characters")
        String gameCreatorName,

        @NotEmpty(message = "At least one rule must be provided")
        @Valid
        List<RuleRequest> rules,

        @NotEmpty(message = "At least one achievement must be provided")
        @Valid
        List<GameAchievementRequest> achievements
) {

    public record RuleRequest(
            @NotNull(message = "Rule description cannot be null")
            @NotBlank(message = "Rule description cannot be blank")
            @Size(min = 1, max = 255, message = "Rule description must be between 1 and 255 characters")
            String description
    ) {}

    public record GameAchievementRequest(
            @NotNull(message = "Achievement code cannot be null")
            @NotBlank(message = "Achievement code cannot be blank")
            @Size(min = 1, max = 100, message = "Achievement code must be between 1 and 100 characters")
            String code,

            @NotNull(message = "Achievement description cannot be null")
            @NotBlank(message = "Achievement description cannot be blank")
            @Size(min = 1, max = 500, message = "Achievement description must be between 1 and 500 characters")
            String description
    ) {}
}
