package be.kdg.team11.game.core;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import be.kdg.team11.game.domain.achievement.Achievement;
import team11.platform_backend.game.domain.achievement.AchievementThreshold;
import be.kdg.team11.game.domain.achievement.AchievementType;
import be.kdg.team11.game.domain.game.GameId;
import be.kdg.team11.game.port.in.CreateAchievementCommand;
import be.kdg.team11.game.port.in.CreateAchievementPort;
import be.kdg.team11.game.port.out.LoadGamePort;
import be.kdg.team11.game.port.out.SaveAchievementPort;
import team11.platform_backend.sharedkernel.valueobjects.Url;

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
        AchievementType achievementType = AchievementType.valueOf(command.type());
        AchievementThreshold threshold = new AchievementThreshold(
                achievementType,
                command.threshold()
        );

        // 3. Create Achievement aggregate (new, so no ID yet)
        Achievement achievement = new Achievement(
                command.name(),
                gameId,
                command.description(),
                pictureUrl,
                threshold
        );

        // 4. Save to persistence (follows Restaurant pattern)
        saveAchievementPorts.forEach(saveAchievementPort ->
                saveAchievementPort.save(achievement));

        return achievement;
    }
}
