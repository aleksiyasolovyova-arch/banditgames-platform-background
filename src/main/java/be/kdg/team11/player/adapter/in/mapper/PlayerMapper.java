package be.kdg.team11.player.adapter.in.mapper;

import be.kdg.team11.player.adapter.in.request.CreatePlayerRequest;
import be.kdg.team11.player.adapter.in.response.PlayerDto;
import be.kdg.team11.player.domain.player.Player;
import be.kdg.team11.player.port.in.CreatePlayerCommand;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PlayerMapper {
    public CreatePlayerCommand toCreateCommand(CreatePlayerRequest request) {
        return new CreatePlayerCommand(
                request.username(),
                request.pictureUrl()
        );
    }


    public PlayerDto toResponse(Player player) {
        return new PlayerDto(
                player.getPlayerId().playerId(),
                player.getUsername(),
                player.getPictureUrl(),
                player.getJoinedDate(),
                player.getOwnedGames().stream()
                        .map(ownedGame -> new PlayerDto.OwnedGameDto(
                                ownedGame.getGame().gameId(),
                                ownedGame.isFavorite(),
                                ownedGame.getDateBought()
                        ))
                        .collect(Collectors.toSet()),
                player.getUnlockedPlatformAchievements().stream()
                        .map(achievement -> new PlayerDto.UnlockedPlatformAchievementDto(
                                achievement.achievementId().achievementId(),
                                achievement.unlockedAt()
                        ))
                        .collect(Collectors.toSet()),
                player.getUnlockedGameAchievements().stream()
                        .map(achievement -> new PlayerDto.UnlockedGameAchievementDto(
                                achievement.gameReference().gameId(),
                                achievement.code(),
                                achievement.unlockedAt()
                        ))
                        .collect(Collectors.toSet())
        );
    }
}
