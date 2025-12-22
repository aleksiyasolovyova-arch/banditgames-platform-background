package be.kdg.team11.content.adapter.in.mapper;

import be.kdg.team11.content.adapter.in.request.RegisterGameRequest;
import be.kdg.team11.content.adapter.in.request.UpdateGameRequest;
import be.kdg.team11.content.adapter.in.response.AdminGameDto;
import be.kdg.team11.content.adapter.in.response.PublicGameDto;
import be.kdg.team11.content.domain.game.Game;
import be.kdg.team11.content.port.in.ModifyGameUrlsCommand;
import be.kdg.team11.content.port.in.RegisterGameCommand;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GameMapper {

    public RegisterGameCommand toCommand(RegisterGameRequest request) {
        return new RegisterGameCommand(
                request.name(),
                request.description(),
                request.pictureUrl(),
                request.gameUrl(),
                request.gameCreatorName(),
                request.rules().stream()
                        .map(RegisterGameRequest.RuleRequest::description)
                        .toList(),
                request.achievements().stream()
                        .map(achievementReq -> new RegisterGameCommand.GameAchievementCommand(
                                achievementReq.code(),
                                achievementReq.description()
                        ))
                        .toList(),
                request.playableWithAI()
        );
    }

    public AdminGameDto toAdminResponse(Game game) {
        return new AdminGameDto(
                game.getGameId().gameId(),
                game.getName(),
                game.getDescription(),
                game.getPictureUrl(),
                game.getGameUrl(),
                game.getGameCreatorName(),
                game.getReviewState().name(),
                game.getRules().stream()
                        .map(rule -> new AdminGameDto.RuleDto(rule.description()))
                        .toList(),
                game.getAchievements().stream()
                        .map(achievement -> new AdminGameDto.GameAchievementDto(
                                achievement.code(),
                                achievement.description()
                        ))
                        .toList(),
                game.isPlayableWithAI()
        );
    }

    public PublicGameDto toPlayerResponse(Game game){
        return new PublicGameDto(
                game.getGameId().gameId(),
                game.getName(),
                game.getDescription(),
                game.getPictureUrl(),
                game.getGameCreatorName()
        );

    }

    public ModifyGameUrlsCommand toUpdateCommand(UUID gameId, UpdateGameRequest request) {
        return new ModifyGameUrlsCommand(
                gameId,
                request.pictureUrl(),
                request.gameUrl()
        );
    }


}
