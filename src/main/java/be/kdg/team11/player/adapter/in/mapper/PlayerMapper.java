package be.kdg.team11.player.adapter.in.mapper;

import be.kdg.team11.player.adapter.in.request.ChangePlayerPictureUrlRequest;
import be.kdg.team11.player.adapter.in.response.PlayerDto;
import be.kdg.team11.player.adapter.in.response.PlayerInfoDto;
import be.kdg.team11.player.domain.player.Player;
import be.kdg.team11.player.port.in.ChangePlayerPictureUrlCommand;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PlayerMapper {
    public PlayerInfoDto toInfoResponse(Player player){
        return new PlayerInfoDto(
                player.getPlayerId().playerId(),
                player.getUsername(),
                player.getPictureUrl()
        );
    }

    public PlayerDto toResponse(Player player) {
        return new PlayerDto(
                player.getPlayerId().playerId(),
                player.getUsername(),
                player.getPictureUrl(),
                player.getJoinedDate(),
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
                        .collect(Collectors.toSet()),
                player.getFavoriteGame().gameId()
        );
    }
}
