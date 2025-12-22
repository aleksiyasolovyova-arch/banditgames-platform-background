package be.kdg.team11.readmodel.service.game;

import be.kdg.team11.readmodel.models.GameModel;
import be.kdg.team11.readmodel.models.GameModelAchievementEmbeddable;

import java.util.List;
import java.util.UUID;

public interface GameModelService {

    //TODO take event for projection methods and write logic in implementation
    void project(
      UUID gameId,
      String name,
      String description,
      String pictureUrl,
      String gameUrl,
      String gameCreatorName,
      String reviewState,
      List<String> rules,
      List<GameModelAchievementEmbeddable> achievements,
      boolean playableWithAI
    );

    //TODO return dto instead of object and do mapping logic in service!
    List<GameModel> getAll();
    List<GameModel> getAllWithRules();
    List<GameModel> getAllWithRulesAndAchievements();
}
