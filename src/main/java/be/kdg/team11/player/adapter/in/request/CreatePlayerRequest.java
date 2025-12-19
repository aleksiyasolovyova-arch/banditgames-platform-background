package be.kdg.team11.player.adapter.in.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

public record CreatePlayerRequest(
        @NotNull(message = "Username cannot be null")
        @NotBlank(message = "Username cannot be blank")
        @Size(min = 1, max = 50, message = "Username must be between 1 and 50 characters")
        String username,

        @NotNull(message = "Picture URL cannot be null")
        @NotBlank(message = "Picture URL cannot be blank")
        @URL(message = "Picture URL must be a valid URL")
        String pictureUrl
) {
}
