package be.kdg.team11.readmodel.service.game;

import be.kdg.team11.readmodel.controller.dto.achievement.AchievementModelDto;
import be.kdg.team11.readmodel.controller.dto.game.GameModelDto;
import be.kdg.team11.readmodel.models.GameModel;
import be.kdg.team11.sharedkernel.events.game.*;

import java.util.List;
import java.util.UUID;

public interface GameModelService {

    void project(GameRegisteredEvent event);
    void project(PassedGameReviewEvent event);
    void project(FailedGameReviewEvent event);
    void project(GameToggledPlayableWithAIEvent event);
    void project(GameUrlsModifiedEvent event);

    List<? extends GameModelDto> getAll();
    List<? extends GameModelDto> getAllWithRules(UUID playerId);
    List<? extends GameModelDto> getAllWithRulesAndAchievements();
}
