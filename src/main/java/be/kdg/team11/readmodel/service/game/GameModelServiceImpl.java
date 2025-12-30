package be.kdg.team11.readmodel.service.game;

import be.kdg.team11.readmodel.controller.dto.game.AdminGameModelDto;
import be.kdg.team11.readmodel.controller.dto.game.PlayerGameModelDto;
import be.kdg.team11.readmodel.controller.dto.game.PublicGameModelDto;
import be.kdg.team11.readmodel.models.GameModel;
import be.kdg.team11.readmodel.models.PlayerModel;
import be.kdg.team11.readmodel.repository.GameModelRepository;
import be.kdg.team11.readmodel.repository.PlayerModelRepository;
import be.kdg.team11.sharedkernel.events.game.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class GameModelServiceImpl implements GameModelService {
    private final GameModelRepository gameModelRepository;
    private final PlayerModelRepository playerModelRepository;
    private final GameModelMapper gameModelMapper;

    public GameModelServiceImpl(GameModelRepository gameModelRepository,
                                PlayerModelRepository playerModelRepository,
                                GameModelMapper gameModelMapper) {
        this.gameModelRepository = gameModelRepository;
        this.playerModelRepository = playerModelRepository;
        this.gameModelMapper = gameModelMapper;
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
        game.setPending(true);
        game.setPlayableWithAI(event.playableWithAI());
        game.setCreatedAt(event.eventPit());


        if (event.rules() != null && !event.rules().isEmpty()) {
            List<GameModel.GameRule> rules = new ArrayList<>();
            event.rules().forEach(ruleData -> {
                GameModel.GameRule rule = new GameModel.GameRule();
                rule.setRuleId(UUID.randomUUID());
                rule.setDescription(ruleData.description());
                rules.add(rule);
            });
            game.setRules(rules);
        }

        if (event.achievements() != null && !event.achievements().isEmpty()) {
            List<GameModel.GameAchievement> gameAchievements = new ArrayList<>();
            event.achievements().forEach(achievementData -> {
                GameModel.GameAchievement gameAchievement = new GameModel.GameAchievement();
                gameAchievement.setCode(achievementData.code());
                gameAchievement.setDescription(achievementData.description());
                gameAchievements.add(gameAchievement);
            });
            game.setAchievements(gameAchievements);
        }

        gameModelRepository.save(game);
    }

    @Override
    public void project(PassedGameReviewEvent event) {
        gameModelRepository.findById(event.gameId())
                .ifPresent(game -> {
                    game.setPending(false);
                    game.setUpdatedAt(event.eventPit());
                    gameModelRepository.save(game);
                });
    }

    @Override
    public void project(FailedGameReviewEvent event) {
        gameModelRepository.findById(event.gameId())
                .ifPresent(gameModelRepository::delete);
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
                    game.setPictureUrl(event.newPictureUrl());
                    game.setGameUrl(event.newGameUrl());
                    game.setUpdatedAt(event.eventPit());
                    gameModelRepository.save(game);
                });
    }

    @Override
    public List<AdminGameModelDto> getAllForAdmin() {
        return gameModelRepository.findAll().stream().map(
                gameModelMapper::toAdminGameDto).toList();
    }

    @Override
    public List<PlayerGameModelDto> getAllForPlayer(UUID playerId) {

        UUID favouriteGameId = playerModelRepository.findById(playerId)
                .map(PlayerModel::getFavouriteGameId)
                .orElse(null);

        return gameModelRepository.findByPending(false).stream()
                .map(gameRM -> gameModelMapper.toPlayerGamesDto(gameRM, favouriteGameId))
                .toList();
    }

    @Override
    public List<PublicGameModelDto> getAllForPublic() {
        return gameModelRepository.findByPending(false).stream().map(gameModelMapper::toPublicGameDto).toList();
    }
}
