package be.kdg.team11.content.adapter.in.mapper;

import org.springframework.stereotype.Component;

import be.kdg.team11.content.adapter.in.request.RegisterGameRequest;
import be.kdg.team11.content.adapter.in.request.UpdateGameRequest;
import be.kdg.team11.content.adapter.in.response.GameDto;
import be.kdg.team11.content.domain.game.Game;
import be.kdg.team11.content.port.in.RegisterGameCommand;
import be.kdg.team11.content.port.in.ModifyGameUrlsCommand;
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
                        .toList()
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
                        .toList()
        );
    }

    public ModifyGameUrlsCommand toUpdateCommand(UUID gameId, UpdateGameRequest request) {
        return new ModifyGameUrlsCommand(
                gameId,
                request.gameName(),
                request.gameDescription(),
                request.gamePrice(),
                request.pictureUrls(),
                request.gameUrl()
        );
    }

}
