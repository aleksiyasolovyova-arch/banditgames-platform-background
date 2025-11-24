package team11.platform_backend.game.core;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import team11.platform_backend.game.domain.achievement.Achievement;
import team11.platform_backend.game.domain.achievement.AchievementId;
import team11.platform_backend.game.domain.achievement.AchievementThreshold;
import team11.platform_backend.game.port.in.UpdateAchievementCommand;
import team11.platform_backend.game.port.in.UpdateAchievementPort;
import team11.platform_backend.game.port.out.LoadAchievementPort;
import team11.platform_backend.game.port.out.SaveAchievementPort;
import team11.platform_backend.sharedkernel.valueobjects.Url;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UpdateAchievementUseCaseImpl implements UpdateAchievementPort{

    private final List<LoadAchievementPort> loadAchievementPorts;
    private final List<SaveAchievementPort> saveAchievementPorts;

    public UpdateAchievementUseCaseImpl(List<LoadAchievementPort> loadAchievementPorts,
                                        List<SaveAchievementPort> saveAchievementPorts) {
        this.loadAchievementPorts = loadAchievementPorts;
        this.saveAchievementPorts = saveAchievementPorts;
    }

    @Override
    public Achievement updateAchievement(UpdateAchievementCommand command) {
        // 1. Load the existing achievement aggregate
        AchievementId achievementId = new AchievementId(command.achievementId());
        Achievement existingAchievement = loadAchievementPorts.stream()
                .map(port -> port.findById(achievementId))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Achievement not found with ID: " + command.achievementId()));

        // 2. Convert pictureUrl to Url value object
        Url pictureUrl = new Url(command.pictureUrl());

        // 3. Create new AchievementThreshold value object
        AchievementThreshold achievementThreshold = new AchievementThreshold(
                command.achievementType(),
                command.threshold()
        );

        // 4. Create new Achievement instance with updated values using the loading constructor
        //    Keep unchanged: achievementId, gameId
        //    Update: achievementName, achievementDescription, pictureUrl, achievementThreshold
        Achievement updatedAchievement = new Achievement(
                existingAchievement.getAchievementId(),
                existingAchievement.getGameId(),
                command.achievementName(),
                command.achievementDescription(),
                pictureUrl,
                achievementThreshold
        );

        // 5. Persist the new aggregate
        saveAchievementPorts.forEach(savePort -> savePort.save(updatedAchievement));

        return updatedAchievement;
    }
}