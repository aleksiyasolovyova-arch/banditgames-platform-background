package be.kdg.team11.readmodel.service.game;

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

    //TODO return dto instead of object and do mapping logic in service!
    List<GameModel> getAll();
    List<GameModel> getAllWithRules();
    List<GameModel> getAllWithRulesAndAchievements();
}
