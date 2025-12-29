package be.kdg.team11.content.port.out;

import be.kdg.team11.content.domain.platformachievement.PlatformAchievement;
import be.kdg.team11.content.domain.platformachievement.PlatformAchievementId;

import java.util.List;
import java.util.Optional;

public interface LoadPlatformAchievementPort {
    Optional<PlatformAchievement> loadBy(PlatformAchievementId platformAchievementId);
    List<PlatformAchievement> loadAllExcept(List<PlatformAchievementId> platFormAchievementIds);
}
