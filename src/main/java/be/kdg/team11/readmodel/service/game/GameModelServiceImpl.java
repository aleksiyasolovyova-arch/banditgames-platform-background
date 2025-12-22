package be.kdg.team11.readmodel.service.game;

import be.kdg.team11.readmodel.models.AchievementModel;
import be.kdg.team11.readmodel.models.AchievementModelType;
import be.kdg.team11.readmodel.models.GameModel;
import be.kdg.team11.readmodel.models.RuleModel;
import be.kdg.team11.readmodel.repository.AchievementModelRepository;
import be.kdg.team11.readmodel.repository.GameModelRepository;
import be.kdg.team11.readmodel.repository.RuleModelRepository;
import be.kdg.team11.sharedkernel.events.game.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class GameModelServiceImpl implements GameModelService {
    private final GameModelRepository gameModelRepository;
    private final RuleModelRepository ruleModelRepository;
    private final AchievementModelRepository achievementModelRepository;

    public GameModelServiceImpl(GameModelRepository gameModelRepository,
                                RuleModelRepository ruleModelRepository,
                                AchievementModelRepository achievementModelRepository) {
        this.gameModelRepository = gameModelRepository;
        this.ruleModelRepository = ruleModelRepository;
        this.achievementModelRepository = achievementModelRepository;
    }


    @Override
    public void project(GameRegisteredEvent event) {
        GameModel game = new GameModel();
        game.setGameId(event.gameId());
        game.setName(event.name());
        game.setDescription(event.description());
        game.setPictureUrl(event.pictureUrl());
        game.setGameUrl(event.gameUrl());
        game.setCreatorName(event.gameCreatorName());
        game.setReviewState("PENDING");
        game.setPlayableWithAI(event.playableWithAI());
        game.setCreatedAt(event.eventPit());

        gameModelRepository.save(game);

        if (event.rules() != null && !event.rules().isEmpty()) {
            event.rules().forEach(ruleData -> {
                RuleModel rule = new RuleModel();
                rule.setRuleId(UUID.randomUUID());
                rule.setGameId(event.gameId());
                rule.setDescription(ruleData.description());

                ruleModelRepository.save(rule);
            });
        }

        if (event.achievements() != null && !event.achievements().isEmpty()) {
            event.achievements().forEach(achievementData -> {
                AchievementModel achievement = new AchievementModel();
                achievement.setDescription(achievementData.description());
                achievement.setType(AchievementModelType.GAME);
                // Link to game
                achievement.setGameId(event.gameId());
                achievement.setAchievementCode(achievementData.code());
                achievement.setGameName(event.name());

                achievement.setCreatedAt(event.eventPit());

                achievementModelRepository.save(achievement);
            });
        }
    }

    @Override
    public void project(PassedGameReviewEvent event) {
        gameModelRepository.findById(event.gameId())
                .ifPresent(game -> {
                    game.setReviewState("PASSED");
                    game.setUpdatedAt(event.eventPit());
                    gameModelRepository.save(game);
                });
    }

    @Override
    public void project(FailedGameReviewEvent event) {
        gameModelRepository.findById(event.gameId())
                .ifPresent(game -> {
                    game.setReviewState("FAILED");
                    game.setUpdatedAt(event.eventPit());
                    gameModelRepository.save(game);
                });
    }

    @Override
    public void project(GameToggledPlayableWithAIEvent event) {
        gameModelRepository.findById(event.gameId())
                .ifPresent(game -> {
                    game.setPlayableWithAI(event.playableWithAI());
                    game.setUpdatedAt(event.eventPit());
                    gameModelRepository.save(game);
                });
    }

    @Override
    public void project(GameUrlsModifiedEvent event) {
        gameModelRepository.findById(event.gameId())
                .ifPresent(game -> {
                    game.setGameUrl(event.newGameUrl());
                    game.setUpdatedAt(LocalDateTime.now());
                    gameModelRepository.save(game);
                });
    }

    @Override
    public List<GameModel> getAll() {
        return gameModelRepository.findAll();
    }

    @Override
    public List<GameModel> getAllWithRules() {
        return gameModelRepository.findAllWithRules();
    }

    @Override
    public List<GameModel> getAllWithRulesAndAchievements() {
        return gameModelRepository.findAllWithRulesAndAchievements();
    }
}
