package be.kdg.team11.readmodel.service.game;

import be.kdg.team11.readmodel.controller.dto.game.AdminGameModelDto;
import be.kdg.team11.readmodel.controller.dto.game.PlayerGameModelDto;
import be.kdg.team11.readmodel.controller.dto.game.PublicGameModelDto;
import be.kdg.team11.readmodel.models.GameModel;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class GameModelMapper {
    public AdminGameModelDto toAdminGameDto(GameModel game) {
        return new AdminGameModelDto(
                game.getGameId(),
                game.getName(),
                game.getDescription(),
                game.getPictureUrl(),
                game.getGameUrl(),
                game.getCreatorName(),
                game.getReviewState(),
                game.getRules().stream()
                        .map(rule -> new AdminGameModelDto.RuleDto(rule.getDescription()))
                        .collect(Collectors.toList()),
                game.getAchievements().stream()
                        .map(achievement -> new AdminGameModelDto.GameAchievementDto(achievement.getCode(),achievement.getDescription()))
                        .collect(Collectors.toList()),
                game.isPlayableWithAI()
        );
    }

    public PlayerGameModelDto toPlayerGamesDto(GameModel game, UUID favouriteGameId) {
        return new PlayerGameModelDto(
                game.getGameId(),
                game.getName(),
                game.getDescription(),
                game.getPictureUrl(),
                game.getGameUrl(),
                game.getCreatorName(),
                game.getRules().stream()
                        .map(rule -> new PlayerGameModelDto.RuleDto(rule.getDescription()))
                        .collect(Collectors.toList()),
                game.getGameId().equals(favouriteGameId),
                game.isPlayableWithAI()
        );
    }

    public PublicGameModelDto toPublicGameDto(GameModel game) {
        return new PublicGameModelDto(
                game.getGameId(),
                game.getName(),
                game.getDescription(),
                game.getPictureUrl(),
                game.getCreatorName()
        );
    }
}
