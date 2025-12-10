package be.kdg.team11.game.core;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import be.kdg.team11.game.domain.achievement.Achievement;
import be.kdg.team11.game.domain.achievement.AchievementId;
import team11.platform_backend.game.domain.achievement.AchievementThreshold;
import be.kdg.team11.game.port.in.UpdateAchievementCommand;
import be.kdg.team11.game.port.in.UpdateAchievementPort;
import be.kdg.team11.game.port.out.LoadAchievementPort;
import be.kdg.team11.game.port.out.SaveAchievementPort;
import be.kdg.team11.game.domain.Url;

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
                .map(port -> port.loadBy(achievementId))
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
        //    Update: name, description, pictureUrl, achievementThreshold
        Achievement updatedAchievement = new Achievement(
                existingAchievement.getAchievementId(),
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