package be.kdg.team11.game.adapter.in.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

public record RegisterGameRequest(
        @NotNull(message = "Game name cannot be null")
        @NotBlank(message = "Game name cannot be blank")
        @Size(max = 100, message = "Game name cannot exceed 100 characters")
        String gameName,

        @NotNull(message = "Game description cannot be null")
        @NotBlank(message = "Game description cannot be blank")
        @Size(max = 500, message = "Game description cannot exceed 500 characters")
        String gameDescription,

        @NotNull(message = "Game price cannot be null")
        @DecimalMin(value = "0.0", inclusive = true, message = "Game price cannot be negative")
        BigDecimal gamePrice,

        @NotEmpty(message = "At least one picture URL must be provided")
        List<String> pictureUrls,

        @NotNull(message = "Game creator name cannot be null")
        @NotBlank(message = "Game creator name cannot be blank")
        String gameCreatorName,

        @NotNull(message = "Game URL cannot be null")
        @NotBlank(message = "Game URL cannot be blank")
        String gameUrl,

        @NotEmpty(message = "At least one rule must be provided")
        @Valid
        List<RuleRequest> rules
) {

    public record RuleRequest(
            @NotNull(message = "Rule name cannot be null")
            @NotBlank(message = "Rule name cannot be blank")
            @Size(min = 1, max = 255, message = "Rule name must be between 1 and 255 characters")
            String ruleName,

            @NotNull(message = "Rule description cannot be null")
            @NotBlank(message = "Rule description cannot be blank")
            @Size(min = 1, max = 255, message = "Rule description must be between 1 and 255 characters")
            String ruleDescription,

            @NotEmpty(message = "At least one rule category must be provided")
            List<String> ruleCategories // SETUP, GAME_PLAY, WINNING
    ) {}
}
