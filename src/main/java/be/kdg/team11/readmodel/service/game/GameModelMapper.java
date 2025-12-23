package be.kdg.team11.readmodel.service.game;

import be.kdg.team11.readmodel.controller.dto.game.AdminGameDto;
import be.kdg.team11.readmodel.controller.dto.game.PlayerGameDto;
import be.kdg.team11.readmodel.controller.dto.game.PublicGameDto;
import be.kdg.team11.readmodel.models.GameModel;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GameModelMapper {
//    public AdminGameDto toAdminGameDto(GameModel game) {
//        return new AdminGameDto(
//                game.getGameId(),
//                game.getName(),
//                game.getDescription(),
//                game.getPictureUrl(),
//                game.getGameUrl(),
//                game.getCreatorName()
//               // game.getReviewState()
//               // game.getRules().stream()
//               //         .map(AdminGameDto.RuleDto::new)
//               //         .toList(),
//               // game.getAchievementEmbeddables().stream()
//               //         .map(a -> new AdminGameDto.GameAchievementDto(a.getCode(), a.getDescription()))
//               //         .toList(),
//               // game.isPlayableWithAI()
//        );
//    }

//    public PlayerGameDto toPlayerGamesDto(GameModel game, UUID favouriteGameId) {
//        return new PlayerGameDto(
//                game.getGameId(),
//                game.getName(),
//                game.getDescription(),
//                game.getPictureUrl(),
//                game.getGameUrl(),
//                game.getCreatorName()
//               // game.getRules().stream()
//               //         .map(PlayerGameDto.RuleDto::new)
//               //         .toList(),
//               // game.getGameId().equals(favouriteGameId),
//               // game.isPlayableWithAI()
//        );
//    }

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
