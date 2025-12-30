package be.kdg.team11.content.adapter.in.request;

import be.kdg.team11.content.domain.platformachievement.PlatformAchievementType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

public record CreatePlatformAchievementRequest(
        @NotNull(message = "Achievement name cannot be null")
        @NotBlank(message = "Achievement name cannot be blank")
        @Size(min = 1, max = 100, message = "Achievement name must be between 1 and 100 characters")
        String platformAchievementName,

        @NotNull(message = "Achievement description cannot be null")
        @NotBlank(message = "Achievement description cannot be blank")
        @Size(min = 5, max = 500, message = "Achievement description must be between 5 and 500 characters")
        String description,

        @NotNull(message = "Picture URL cannot be null")
        @NotBlank(message = "Picture URL cannot be blank")
        @URL(message = "Picture URL must be a valid URL")
        String pictureUrl,

        @NotNull(message = "Achievement type cannot be null")
        PlatformAchievementType platformAchievementType,

        @NotNull(message = "Required value cannot be null")
        long requiredValue
) {
}
