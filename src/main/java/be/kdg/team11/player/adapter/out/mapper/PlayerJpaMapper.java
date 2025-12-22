package be.kdg.team11.player.adapter.out.mapper;

import be.kdg.team11.player.adapter.out.jpa.embeddable.UnlockedGameAchievementEmbeddable;
import be.kdg.team11.player.adapter.out.jpa.embeddable.UnlockedPlatformAchievementEmbeddable;
import be.kdg.team11.player.adapter.out.jpa.entity.PlayerJpaEntity;
import be.kdg.team11.player.domain.player.*;
import be.kdg.team11.player.domain.projections.GameReference;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PlayerJpaMapper {

    public Player toDomain(PlayerJpaEntity entity) {
        PlayerId playerId = PlayerId.of(entity.getPlayerId());
        Username username = Username.of(entity.getUsername());
        Set<UnlockedPlatformAchievement> unlockedPlatformAchievements =
                entity.getUnlockedPlatformAchievements().stream()
                        .map(e -> new UnlockedPlatformAchievement(
                                new AchievementId(e.getAchievementId()),
                                e.getUnlockedAt()
                        ))
                        .collect(Collectors.toSet());

        Set<UnlockedGameAchievement> unlockedGameAchievements =
                entity.getUnlockedGameAchievements().stream()
                        .map(e -> new UnlockedGameAchievement(
                                new GameReference(e.getGameReference()),
                                e.getCode(),
                                e.getUnlockedAt()
                        ))
                        .collect(Collectors.toSet());

        GameReference favoriteGame = entity.getFavoriteGameId() != null ? new GameReference(entity.getFavoriteGameId()) : null;



        return new Player(
                playerId,
                username,
                entity.getPictureUrl(),
                entity.getJoinedDate(),
                unlockedPlatformAchievements,
                unlockedGameAchievements,
                favoriteGame
        );
    }

    public PlayerJpaEntity toJpaEntity(Player player) {
        PlayerJpaEntity entity = new PlayerJpaEntity();
        entity.setPlayerId(player.getPlayerId().playerId());
        entity.setUsername(player.getUsername().username());
        entity.setPictureUrl(player.getPictureUrl());
        entity.setJoinedDate(player.getJoinedDate());

        Set<UnlockedPlatformAchievementEmbeddable> platformAchievements =
                player.getUnlockedPlatformAchievements().stream()
                        .map(a -> {
                            UnlockedPlatformAchievementEmbeddable e =
                                    new UnlockedPlatformAchievementEmbeddable();
                            e.setAchievementId(a.achievementId().achievementId());
                            e.setUnlockedAt(a.unlockedAt());
                            return e;
                        })
                        .collect(Collectors.toSet());
        entity.setUnlockedPlatformAchievements(platformAchievements);

        Set<UnlockedGameAchievementEmbeddable> gameAchievements =
                player.getUnlockedGameAchievements().stream()
                        .map(a -> {
                            UnlockedGameAchievementEmbeddable e =
                                    new UnlockedGameAchievementEmbeddable();
                            e.setGameReference(a.gameReference().gameId());
                            e.setCode(a.code());
                            e.setUnlockedAt(a.unlockedAt());
                            return e;
                        })
                        .collect(Collectors.toSet());
        entity.setUnlockedGameAchievements(gameAchievements);
        entity.setFavoriteGameId(player.getFavoriteGame().gameId());

        return entity;
    }

}