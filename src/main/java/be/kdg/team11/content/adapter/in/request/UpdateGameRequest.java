package be.kdg.team11.content.adapter.in.request;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

public record UpdateGameRequest(
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

        @NotNull(message = "Game URL cannot be null")
        @NotBlank(message = "Game URL cannot be blank")
        String gameUrl
) {}
