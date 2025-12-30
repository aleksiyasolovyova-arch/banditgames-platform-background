package be.kdg.team11.content.port.in;

import be.kdg.team11.content.domain.platformachievement.PlatformAchievement;

public interface CreatePlatformAchievementPort {
    PlatformAchievement create(CreatePlatformAchievementCommand command);
}
