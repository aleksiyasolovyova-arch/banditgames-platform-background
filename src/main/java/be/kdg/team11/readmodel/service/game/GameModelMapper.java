package be.kdg.team11.readmodel.service.game;

import be.kdg.team11.content.adapter.in.response.AchievementDto;
import be.kdg.team11.content.adapter.in.response.GameDto;
import be.kdg.team11.readmodel.controller.AchievementsModelController;
import be.kdg.team11.readmodel.controller.dto.game.AdminGameDto;
import be.kdg.team11.readmodel.controller.dto.game.PlayerGameDto;
import be.kdg.team11.readmodel.controller.dto.game.PublicGameDto;
import be.kdg.team11.readmodel.models.AchievementModel;
import be.kdg.team11.readmodel.models.GameModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class GameModelMapper {
    public AdminGameDto toAdminGameDto(GameModel game) {
        return new AdminGameDto(
                game.getGameId(),
                game.getName(),
                game.getDescription(),
                game.getPictureUrl(),
                game.getGameUrl(),
                game.getCreatorName(),
                game.getReviewState(),
                game.getRules().stream()
                        .map(rule -> new AdminGameDto.RuleDto(rule.getDescription()))
                        .collect(Collectors.toList()),
                game.getAchievements().stream()
                        .map(achievement -> new AdminGameDto.GameAchievementDto(achievement.getCode(),achievement.getDescription()))
                        .collect(Collectors.toList()),
                game.isPlayableWithAI()
        );
    }

    public PlayerGameDto toPlayerGamesDto(GameModel game, UUID favouriteGameId) {
        return new PlayerGameDto(
                game.getGameId(),
                game.getName(),
                game.getDescription(),
                game.getPictureUrl(),
                game.getGameUrl(),
                game.getCreatorName(),
                game.getRules().stream()
                        .map(rule -> new PlayerGameDto.RuleDto(rule.getDescription()))
                        .collect(Collectors.toList()),
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
                game.getCreatorName()
        );
    }
}
