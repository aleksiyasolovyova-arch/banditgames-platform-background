package be.kdg.team11.content.adapter.in.request;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record UpdateGameRequest(

        @NotNull(message = "Picture URL cannot be null")
        @NotBlank(message = "Picture URL cannot be blank")
        String pictureUrl,

        @NotNull(message = "Game URL cannot be null")
        @NotBlank(message = "Game URL cannot be blank")
        String gameUrl
) {}
