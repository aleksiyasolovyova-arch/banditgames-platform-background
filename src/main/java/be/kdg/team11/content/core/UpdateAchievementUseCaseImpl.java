package be.kdg.team11.content.core;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import be.kdg.team11.content.domain.achievement.Achievement;
import be.kdg.team11.content.domain.achievement.AchievementId;
import team11.platform_backend.game.domain.achievement.AchievementThreshold;
import be.kdg.team11.content.port.in.UpdateAchievementCommand;
import be.kdg.team11.content.port.in.UpdateAchievementPort;
import be.kdg.team11.content.port.out.LoadAchievementPort;
import be.kdg.team11.content.port.out.SaveAchievementPort;

@Service
@Transactional
public class UpdateAchievementUseCaseImpl implements UpdateAchievementPort {

    private final LoadAchievementPort loadAchievementPort;
    private final SaveAchievementPort saveAchievementPort;

    public UpdateAchievementUseCaseImpl(LoadAchievementPort loadAchievementPort,
                                        SaveAchievementPort saveAchievementPort) {
        this.loadAchievementPort = loadAchievementPort;
        this.saveAchievementPort = saveAchievementPort;
    }

    @Override
    public Achievement updateAchievement(UpdateAchievementCommand command) {
        AchievementId achievementId = new AchievementId(command.achievementId());

        Achievement existingAchievement = loadAchievementPort.loadBy(achievementId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Achievement not found with ID: " + command.achievementId()));

        Achievement updatedAchievement = new Achievement(
                existingAchievement.getAchievementId(),
                command.achievementName(),
                command.achievementDescription(),
                pictureUrl,
                achievementThreshold
        );

        saveAchievementPort.save(updatedAchievement);
        return updatedAchievement;
    }
}
