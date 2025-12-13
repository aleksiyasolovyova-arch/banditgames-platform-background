package be.kdg.team11.content.port.in;

import org.springframework.util.Assert;

public record CreateAchievementCommand(
        String name,
        String description,
        String pictureUrl,
        String type,
        long requiredValue
) {

    public CreateAchievementCommand {
        Assert.hasText(name, "Achievement name cannot be empty");
        Assert.isTrue(name.length() <= 100, "Achievement name cannot exceed 100 characters");
        Assert.hasText(description, "Achievement description cannot be empty");
        Assert.isTrue(description.length() <= 255, "Achievement description cannot exceed 255 characters");
        Assert.hasText(pictureUrl, "Picture URL cannot be empty");
        Assert.hasText(type, "Achievement type cannot be empty");
        Assert.isTrue( requiredValue >= 0 && requiredValue <= 100, "Achievement required value must be between 0 and 100" );
    }

}
