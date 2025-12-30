package be.kdg.team11.content.port.out;

import be.kdg.team11.content.domain.platformachievement.PlatformAchievement;

public interface SavePlatformAchievementPort {
    PlatformAchievement save(PlatformAchievement platformAchievement);
}
