package team11.platform_backend.game.core;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import team11.platform_backend.game.domain.achievement.Achievement;
import team11.platform_backend.game.domain.achievement.AchievementThreshold;
import team11.platform_backend.game.domain.achievement.AchievementType;
import team11.platform_backend.game.domain.game.GameId;
import team11.platform_backend.game.port.in.CreateAchievementCommand;
import team11.platform_backend.game.port.in.CreateAchievementPort;
import team11.platform_backend.game.port.out.LoadGamePort;
import team11.platform_backend.game.port.out.SaveAchievementPort;
import team11.platform_backend.game.domain.Url;

import java.util.List;

@Service
@Transactional
public class CreateAchievementUseCaseImpl implements CreateAchievementPort {

    private final List<SaveAchievementPort> saveAchievementPorts;
    private final LoadGamePort loadGamePort;

    public CreateAchievementUseCaseImpl(List<SaveAchievementPort> saveAchievementPorts,
                                        LoadGamePort loadGamePort) {
        this.saveAchievementPorts = saveAchievementPorts;
        this.loadGamePort = loadGamePort;
    }

    @Override
    public Achievement createAchievement(CreateAchievementCommand command) {
        // 1. Verify game exists (business rule validation)
        GameId gameId = new GameId(command.gameId());
        loadGamePort.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Game not found with ID: " + command.gameId()));

        // 2. Convert command to domain value objects
        Url pictureUrl = new Url(command.pictureUrl());
        AchievementType achievementType = AchievementType.valueOf(command.achievementType());
        AchievementThreshold threshold = new AchievementThreshold(
                achievementType,
                command.threshold()
        );

        // 3. Create Achievement aggregate (new, so no ID yet)
        Achievement achievement = Achievement.create(
                command.achievementName(),
                command.achievementDescription(),
                pictureUrl,
                threshold
        );

        // 4. Save to persistence (follows Restaurant pattern)
        saveAchievementPorts.forEach(saveAchievementPort ->
                saveAchievementPort.save(achievement));

        return achievement;
    }
}
