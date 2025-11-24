package team11.platform_backend.game.adapter.in.mapper;

import org.springframework.stereotype.Component;

import team11.platform_backend.game.adapter.in.request.RegisterGameRequest;
import team11.platform_backend.game.adapter.in.request.UpdateGameRequest;
import team11.platform_backend.game.adapter.in.response.GameDto;
import team11.platform_backend.game.domain.game.Game;
import team11.platform_backend.game.port.in.RegisterGameCommand;
import team11.platform_backend.game.port.in.UpdateGameCommand;
import team11.platform_backend.sharedkernel.valueobjects.Url;

import java.util.UUID;

@Component
public class GameMapper {

    public RegisterGameCommand toCommand(RegisterGameRequest request) {
        return new RegisterGameCommand(
                request.gameName(),
                request.gameDescription(),
                request.gamePrice(),
                request.pictureUrls(),
                request.gameCreatorName(),
                request.gameUrl(),
                request.rules().stream()
                        .map(r -> new RegisterGameCommand.RuleCommand(
                                r.ruleName(),
                                r.ruleDescription(),
                                r.ruleCategories()
                        ))
                        .toList(),
                request.aiPlayerUrl()
        );
    }

    public GameDto toResponse(Game game) {
        return new GameDto(
                game.getGameId().gameId(),
                game.getGameName(),
                game.getGameDescription(),
                game.getGamePrice(),
                game.getPictureUrls().stream()
                        .map(Url::value)
                        .toList(),
                game.getGameCreatorName(),
                game.getGameUrl().value(),
                game.getGameState().name(),
                game.getRules().stream()
                        .map(rule -> new GameDto.RuleDto(
                                rule.ruleName(),
                                rule.ruleDescription(),
                                rule.ruleCategories().stream()
                                        .map(Enum::name)
                                        .toList()
                        ))
                        .toList(),
                game.getAiPlayerUrl() != null ? game.getAiPlayerUrl().value() : null
        );
    }

    public UpdateGameCommand toUpdateCommand(UUID gameId, UpdateGameRequest request) {
        return new UpdateGameCommand(
                gameId,
                request.gameName(),
                request.gameDescription(),
                request.gamePrice(),
                request.pictureUrls(),
                request.gameUrl(),
                request.aiPlayerUrl()
        );
    }

}
