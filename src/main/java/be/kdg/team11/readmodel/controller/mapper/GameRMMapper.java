package be.kdg.team11.readmodel.controller.mapper;

import be.kdg.team11.readmodel.controller.dto.AdminGameDto;
import be.kdg.team11.readmodel.controller.dto.PlayerGamesDto;
import be.kdg.team11.readmodel.controller.dto.PublicGameDto;
import be.kdg.team11.readmodel.models.GameModel;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GameRMMapper {
    public AdminGameDto toAdminGameDto(GameModel game) {
        return new AdminGameDto(
                game.getGameId(),
                game.getName(),
                game.getDescription(),
                game.getPictureUrl(),
                game.getGameUrl(),
                game.getGameCreatorName(),
                game.getReviewState(),
                game.getRules().stream()
                        .map(AdminGameDto.RuleDto::new)
                        .toList(),
                game.getAchievementEmbeddables().stream()
                        .map(a -> new AdminGameDto.GameAchievementDto(a.getCode(), a.getDescription()))
                        .toList(),
                game.isPlayableWithAI()
        );
    }

    public PlayerGamesDto toPlayerGamesDto(GameModel game, UUID favouriteGameId) {
        return new PlayerGamesDto(
                game.getGameId(),
                game.getName(),
                game.getDescription(),
                game.getPictureUrl(),
                game.getGameUrl(),
                game.getGameCreatorName(),
                game.getRules().stream()
                        .map(PlayerGamesDto.RuleDto::new)
                        .toList(),
                game.getGameId().equals(favouriteGameId),
                game.isPlayableWithAI()
        );
    }

    public PublicGameDto toPublicGameDto(GameModel game) {
        return new PublicGameDto(
                game.getGameId(),
                game.getName(),
                game.getDescription(),
                game.getPictureUrl(),
                game.getGameCreatorName()
        );
    }
}
