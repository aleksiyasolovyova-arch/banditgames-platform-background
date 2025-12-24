package be.kdg.team11.content.adapter.in.mapper;

import be.kdg.team11.content.adapter.in.request.RegisterGameRequest;
import be.kdg.team11.content.adapter.in.request.ModifyGameUrlsRequest;
import be.kdg.team11.content.adapter.in.response.GameDto;
import be.kdg.team11.content.domain.game.Game;
import be.kdg.team11.content.port.in.ModifyGameUrlsCommand;
import be.kdg.team11.content.port.in.RegisterGameCommand;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GameMapper {

    public RegisterGameCommand toRegisterGameCommand(RegisterGameRequest request) {
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

    public GameDto toResponse(Game game) {
        return new GameDto(
                game.getGameId().gameId(),
                game.getName(),
                game.getDescription(),
                game.getPictureUrl(),
                game.getGameUrl(),
                game.getGameCreatorName(),
                game.getReviewState().name(),
                game.getRules().stream()
                        .map(rule -> new GameDto.RuleDto(rule.description()))
                        .toList(),
                game.getAchievements().stream()
                        .map(achievement -> new GameDto.GameAchievementDto(
                                achievement.code(),
                                achievement.description()
                        ))
                        .toList(),
                game.isPlayableWithAI()
        );
    }

    public ModifyGameUrlsCommand toModifyGameUrlsCommand(UUID gameId, ModifyGameUrlsRequest request) {
        return new ModifyGameUrlsCommand(
                gameId,
                request.pictureUrl(),
                request.gameUrl()
        );
    }


}
