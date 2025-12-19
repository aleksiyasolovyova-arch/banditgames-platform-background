package be.kdg.team11.player.domain.player;

import be.kdg.team11.player.domain.player.exceptions.InvalidAchievementForPlayerException;
import be.kdg.team11.player.domain.player.exceptions.InvalidGameForPlayerException;
import be.kdg.team11.player.domain.player.exceptions.InvalidPlayerException;
import be.kdg.team11.player.domain.projections.GameReference;
import be.kdg.team11.sharedkernel.events.DomainEvent;
import be.kdg.team11.sharedkernel.events.player.PlayerBoughtGameEvent;
import be.kdg.team11.sharedkernel.events.player.PlayerCreatedEvent;
import be.kdg.team11.sharedkernel.events.player.PlayerFavoritedGameEvent;
import be.kdg.team11.sharedkernel.events.player.PlayerUnfavoritedGameEvent;

import java.time.LocalDate;
import java.util.*;

/**
 * Aggregate Root for the Player subdomain.
 * Represents a player with their games, achievements, and profile information.
 * Manages the complete lifecycle: registration, game purchases, achievements.
 */
public class Player {
    private final PlayerId playerId;
    private final LocalDate joinedDate;
    private final Set<UnlockedPlatformAchievement> unlockedPlatformAchievements = new HashSet<>();
    private final Set<UnlockedGameAchievement> unlockedGameAchievements = new HashSet<>();
    private final Set<OwnedGame> ownedGames = new HashSet<>();
    private final List<DomainEvent> eventStore = new ArrayList<>();


    /**
     * Private constructor for recreating player from persistent storage.
     */
    public Player(PlayerId playerId, LocalDate joinedDate, Set<UnlockedPlatformAchievement> unlockedPlatformAchievements, Set<UnlockedGameAchievement> unlockedGameAchievements, Set<OwnedGame> ownedGames) {
        validateJoinedDate(joinedDate);

        this.playerId = playerId;
        this.joinedDate = joinedDate;
        this.unlockedPlatformAchievements.addAll(unlockedPlatformAchievements);
        this.unlockedGameAchievements.addAll(unlockedGameAchievements);
        this.ownedGames.addAll(ownedGames);
    }

    /**
     * Factory method for creating a new player.
     * Initial state: no games, no achievements.
     */
    public static Player create(PlayerId playerId) {

        Player player = new Player(playerId,
                LocalDate.now(),
                Collections.emptySet(),
                Collections.emptySet(),
                Collections.emptySet());

        PlayerCreatedEvent event = new PlayerCreatedEvent(playerId.playerId(), player.joinedDate);
        player.eventStore.add(event);

        return player;
    }

    public void buyGame(GameReference gameReference) {
        validateGameNotAlreadyOwned(gameReference);

        ownedGames.add(OwnedGame.bought(gameReference, LocalDate.now()));
        PlayerBoughtGameEvent event = new PlayerBoughtGameEvent(
                playerId.playerId(),
                gameReference.gameId(),
                LocalDate.now());

    }

    /**
     * Player marks a game as favorite.
     * Throws InvalidGameForPlayerException if game operation fails.
     */
    public void favoriteGame(GameReference gameReference) {
        OwnedGame ownedGame = validateGameOwned(gameReference);
        validateGameNotAlreadyFavorited(ownedGame, gameReference);

        ownedGame.favorite();

        PlayerFavoritedGameEvent event = new PlayerFavoritedGameEvent(
                playerId.playerId(),
                gameReference.gameId()
        );
        eventStore.add(event);
    }

    /**
     * Player removes a game from favorites.
     * Throws InvalidGameForPlayerException if game operation fails.
     */
    public void unfavoriteGame(GameReference gameReference) {

        OwnedGame ownedGame = validateGameOwned(gameReference);
        validateGameIsFavorite(ownedGame, gameReference);

        ownedGame.unfavorite();
        PlayerUnfavoritedGameEvent event = new PlayerUnfavoritedGameEvent(
                playerId.playerId(),
                gameReference.gameId()
        );
        eventStore.add(event);
    }

    /**
     * Player unlocks a platform achievement.
     * Throws InvalidAchievementForPlayerException if achievement operation fails.
     */
    public void unlockPlatformAchievement(AchievementId achievementId) {
        validatePlatformAchievementNotAlreadyUnlocked(achievementId);

        UnlockedPlatformAchievement achievement = UnlockedPlatformAchievement.now(achievementId);
        this.unlockedPlatformAchievements.add(achievement);
    }

    /**
     * Player unlocks a game-specific achievement.
    */
    public void unlockGameAchievement(GameReference gameReference, String achievementCode) {
        validateGameOwnedForAchievement(gameReference);
        validateGameAchievementNotAlreadyUnlocked(gameReference, achievementCode);

        UnlockedGameAchievement achievement = UnlockedGameAchievement.now(gameReference, achievementCode);
        this.unlockedGameAchievements.add(achievement);
    }

    private OwnedGame findOwnedGame(GameReference gameReference) {
        return ownedGames.stream()
                .filter(og -> og.getGame().equals(gameReference))
                .findFirst()
                .orElse(null);
    }
    public boolean ownsGame(GameReference gameReference) {
        return findOwnedGame(gameReference) != null;
    }


    private static void validateJoinedDate(LocalDate joinedDate) {
        if (joinedDate.isAfter(LocalDate.now())) {
            throw new InvalidPlayerException("Joined date cannot be in the future");
        }
    }

    private void validateGameNotAlreadyOwned(GameReference gameReference) {
        if (ownsGame(gameReference)) {
            throw new InvalidGameForPlayerException(
                    String.format("Player %s already owns game %s",
                            playerId.playerId(), gameReference.gameId())
            );
        }
    }

    private OwnedGame validateGameOwned(GameReference gameReference) {
        OwnedGame ownedGame = findOwnedGame(gameReference);
        if (ownedGame == null) {
            throw new InvalidGameForPlayerException(
                    String.format("Player %s does not own game %s",
                            playerId.playerId(), gameReference.gameId())
            );
        }
        return ownedGame;
    }


    private void validateGameNotAlreadyFavorited(OwnedGame ownedGame, GameReference gameReference) {
        if (ownedGame.isFavorite()) {
            throw new InvalidGameForPlayerException(
                    String.format("Game %s is already favorited", gameReference.gameId())
            );
        }
    }

    private void validateGameIsFavorite(OwnedGame ownedGame, GameReference gameReference) {
        if (!ownedGame.isFavorite()) {
            throw new InvalidGameForPlayerException(
                    String.format("Game %s is not favorited", gameReference.gameId())
            );
        }
    }


    private void validatePlatformAchievementNotAlreadyUnlocked(AchievementId achievementId) {
        boolean alreadyUnlocked = unlockedPlatformAchievements.stream()
                .anyMatch(ufa -> ufa.achievementId().equals(achievementId));

        if (alreadyUnlocked) {
            throw new InvalidAchievementForPlayerException(
                    String.format("Player %s already unlocked achievement %s",
                            playerId.playerId(), achievementId.achievementId())
            );
        }
    }


    private void validateGameOwnedForAchievement(GameReference gameReference) {
        if (!ownsGame(gameReference)) {
            throw new InvalidGameForPlayerException(
                    String.format("Player %s does not own game %s",
                            playerId.playerId(), gameReference.gameId())
            );
        }
    }

    private void validateGameAchievementNotAlreadyUnlocked(GameReference gameReference, String achievementCode) {
        boolean alreadyUnlocked = unlockedGameAchievements.stream()
                .anyMatch(uga -> uga.gameReference().equals(gameReference) &&
                        uga.code().equals(achievementCode));

        if (alreadyUnlocked) {
            throw new InvalidAchievementForPlayerException(
                    String.format("Player %s already unlocked achievement %s in game %s",
                            playerId.playerId(), achievementCode, gameReference.gameId())
            );
        }
    }

    public PlayerId getPlayerId() {
        return playerId;
    }

    public LocalDate getJoinedDate() {
        return joinedDate;
    }

    public Set<UnlockedPlatformAchievement> getUnlockedPlatformAchievements() {
        return Collections.unmodifiableSet(unlockedPlatformAchievements);
    }

    public Set<UnlockedGameAchievement> getUnlockedGameAchievements() {
        return Collections.unmodifiableSet(unlockedGameAchievements);
    }

    public Set<OwnedGame> getOwnedGames() {
        return Collections.unmodifiableSet(ownedGames);
    }
    public List<DomainEvent> getEventStore() {
        return Collections.unmodifiableList(eventStore);
    }
}
