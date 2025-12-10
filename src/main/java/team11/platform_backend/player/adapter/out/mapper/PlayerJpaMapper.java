package team11.platform_backend.player.adapter.out.mapper;

import org.springframework.stereotype.Component;
import team11.platform_backend.player.adapter.out.jpa.embeddable.OwnedGameEmbeddable;
import team11.platform_backend.player.adapter.out.jpa.embeddable.UnlockedGameAchievementEmbeddable;
import team11.platform_backend.player.adapter.out.jpa.embeddable.UnlockedPlatformAchievementEmbeddable;
import team11.platform_backend.player.adapter.out.jpa.entity.PlayerJpaEntity;
import team11.platform_backend.player.domain.player.*;
import team11.platform_backend.player.domain.projections.GameId;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PlayerJpaMapper {

    public Player toDomain(PlayerJpaEntity entity) {
        PlayerId playerId = new PlayerId(entity.getPlayerId());

        // Map unlocked platform achievements
        Set<UnlockedPlatformAchievement> unlockedPlatformAchievements = entity.getUnlockedPlatformAchievements().stream()
                .map(embeddable -> new UnlockedPlatformAchievement(
                        new AchievementId(embeddable.getAchievementId()),
                        embeddable.getUnlockedAt()
                ))
                .collect(Collectors.toSet());

        // Map unlocked game achievements
        Set<UnlockedGameAchievement> unlockedGameAchievements = entity.getUnlockedGameAchievements().stream()
                .map(embeddable -> new UnlockedGameAchievement(
                        new GameId(embeddable.getGameId()),
                        embeddable.getCode(),
                        embeddable.getUnlockedAt()
                ))
                .collect(Collectors.toSet());

        // Map owned games
        Set<OwnedGame> ownedGames = entity.getOwnedGames().stream()
                .map(embeddable -> new OwnedGame(
                        new GameId(embeddable.getGameId()),
                        embeddable.isFavourite(),
                        embeddable.getDateBought()
                ))
                .collect(Collectors.toSet());

        return new Player(
                playerId,
                entity.getJoinedDate(),
                unlockedPlatformAchievements,
                unlockedGameAchievements,
                ownedGames
        );
    }

    public PlayerJpaEntity toJpaEntity(Player player) {
        PlayerJpaEntity entity = new PlayerJpaEntity();
        entity.setPlayerId(player.getPlayerId().playerId());
        entity.setJoinedDate(player.getJoinedDate());

        // Map unlocked platform achievements
        Set<UnlockedPlatformAchievementEmbeddable> platformAchievements = player.getUnlockedPlatformAchievements().stream()
                .map(achievement -> {
                    UnlockedPlatformAchievementEmbeddable embeddable = new UnlockedPlatformAchievementEmbeddable();
                    embeddable.setAchievementId(achievement.achievementId().achievementId());
                    embeddable.setUnlockedAt(achievement.unlockedAt());
                    return embeddable;
                })
                .collect(Collectors.toSet());
        entity.setUnlockedPlatformAchievements(platformAchievements);

        // Map unlocked game achievements
        Set<UnlockedGameAchievementEmbeddable> gameAchievements = player.getUnlockedGameAchievements().stream()
                .map(achievement -> {
                    UnlockedGameAchievementEmbeddable embeddable = new UnlockedGameAchievementEmbeddable();
                    embeddable.setGameId(achievement.gameId().gameId());
                    embeddable.setCode(achievement.code());
                    embeddable.setUnlockedAt(achievement.unlockedAt());
                    return embeddable;
                }
                )
                .collect(Collectors.toSet());
        entity.setUnlockedGameAchievements(gameAchievements);

        // Map owned games
        Set<OwnedGameEmbeddable> ownedGames = player.getOwnedGames().stream()
                .map(game -> {
                    OwnedGameEmbeddable embeddable = new OwnedGameEmbeddable();
                    embeddable.setGameId(game.gameId().gameId());
                    embeddable.setFavourite(game.favourite());
                    embeddable.setDateBought(game.dateBought());
                    return embeddable;
                })
                .collect(Collectors.toSet());
        entity.setOwnedGames(ownedGames);

        return entity;
    }
}