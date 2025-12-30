package be.kdg.team11.readmodel.service.game;

import be.kdg.team11.readmodel.controller.dto.game.AdminGameModelDto;
import be.kdg.team11.readmodel.controller.dto.game.PlayerGameModelDto;
import be.kdg.team11.readmodel.controller.dto.game.PublicGameModelDto;
import be.kdg.team11.sharedkernel.events.game.*;

import java.util.List;
import java.util.UUID;

public interface GameModelService {

    void project(GameRegisteredEvent event);

    void project(PassedGameReviewEvent event);

    void project(FailedGameReviewEvent event);

    void project(GameToggledPlayableWithAIEvent event);

    void project(GameUrlsModifiedEvent event);

    List<AdminGameModelDto> getAllForAdmin();

    List<PlayerGameModelDto> getAllForPlayer(UUID playerId);

    List<PublicGameModelDto> getAllForPublic();
}
