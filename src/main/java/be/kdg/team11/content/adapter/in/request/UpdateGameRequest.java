package be.kdg.team11.content.adapter.in.request;
import jakarta.validation.constraints.*;

public record UpdateGameRequest(

        @NotNull(message = "Picture URL cannot be null")
        @NotBlank(message = "Picture URL cannot be blank")
        String pictureUrl,

        @NotNull(message = "Game URL cannot be null")
        @NotBlank(message = "Game URL cannot be blank")
        String gameUrl
) {}
