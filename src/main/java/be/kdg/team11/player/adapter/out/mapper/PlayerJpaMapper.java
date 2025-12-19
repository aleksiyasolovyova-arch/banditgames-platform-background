package be.kdg.team11.player.adapter.out.mapper;

import be.kdg.team11.player.adapter.out.jpa.embeddable.OwnedGameEmbeddable;
import be.kdg.team11.player.adapter.out.jpa.embeddable.UnlockedGameAchievementEmbeddable;
import be.kdg.team11.player.adapter.out.jpa.embeddable.UnlockedPlatformAchievementEmbeddable;
import be.kdg.team11.player.adapter.out.jpa.entity.PlayerJpaEntity;
import be.kdg.team11.player.domain.player.*;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PlayerJpaMapper {

    public Player toDomain(PlayerJpaEntity entity) {
        PlayerId playerId = new PlayerId(entity.getPlayerId());

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
                                new be.kdg.team11.player.domain.projections.GameReference(e.getGameReference()),
                                e.getCode(),
                                e.getUnlockedAt()
                        ))
                        .collect(Collectors.toSet());

        Set<OwnedGame> ownedGames =
                entity.getOwnedGames().stream()
                        .map(e -> {
                            OwnedGame game = OwnedGame.bought(
                                    new be.kdg.team11.player.domain.projections.GameReference(e.getGameReference()),
                                    e.getDateBought()
                            );
                            if (e.isFavorite()) {
                                game.favorite();
                            }
                            return game;
                        })
                        .collect(Collectors.toSet());

        return new Player(
                playerId,
                entity.getUsername(),
                entity.getPictureUrl(),
                entity.getJoinedDate(),
                unlockedPlatformAchievements,
                unlockedGameAchievements,
                ownedGames
        );
    }

    public PlayerJpaEntity toJpaEntity(Player player) {
        PlayerJpaEntity entity = new PlayerJpaEntity();
        entity.setPlayerId(player.getPlayerId().playerId());
        entity.setUsername(player.getUsername());
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

        Set<OwnedGameEmbeddable> ownedGames =
                player.getOwnedGames().stream()
                        .map(g -> {
                            OwnedGameEmbeddable e = new OwnedGameEmbeddable();
                            e.setGameReference(g.getGame().gameId());
                            e.setFavorite(g.isFavorite());
                            e.setDateBought(g.getDateBought());
                            return e;
                        })
                        .collect(Collectors.toSet());
        entity.setOwnedGames(ownedGames);

        return entity;
    }

}