package be.kdg.team11.readmodel.service.game;

import be.kdg.team11.readmodel.models.GameModel;
import be.kdg.team11.readmodel.models.GameModelAchievementEmbeddable;
import be.kdg.team11.readmodel.repository.GameModelRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class GameModelServiceImpl implements GameModelService {
    private final GameModelRepository gameModelRepository;

    public GameModelServiceImpl(GameModelRepository gameModelRepository) {
        this.gameModelRepository = gameModelRepository;
    }


    @Override
    public void project(UUID gameId, String name, String description, String pictureUrl, String gameUrl, String gameCreatorName, String reviewState, List<String> rules, List<GameModelAchievementEmbeddable> achievements, boolean playableWithAI) {

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
