package be.kdg.team11.readmodel.service.game;

import be.kdg.team11.readmodel.models.GameRM;
import be.kdg.team11.readmodel.repository.GameRMRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class GameServiceImpl implements GameService{
    private final GameRMRepository gameRMRepository;

    public GameServiceImpl(GameRMRepository gameRMRepository) {
        this.gameRMRepository = gameRMRepository;
    }

    @Override
    public void project(UUID gameId, String name, String description, String pictureUrl, String gameUrl, String gameCreatorName, List<String> rules, boolean playableWithAI) {
        GameRM entity = new GameRM();
        entity.setGameId(gameId);
        entity.setName(name);
        entity.setDescription(description);
        entity.setPictureUrl(pictureUrl);
        entity.setGameUrl(gameUrl);
        entity.setGameCreatorName(gameCreatorName);
        entity.setRules(rules);
        entity.setPlayableWithAI(playableWithAI);
        gameRMRepository.save(entity);
    }
}
