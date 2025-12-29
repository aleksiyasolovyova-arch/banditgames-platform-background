package be.kdg.team11.player.domain.player;

import be.kdg.team11.player.domain.player.exceptions.InvalidPlatformAchievementForPlayerException;
import be.kdg.team11.player.domain.player.exceptions.InvalidPlayerException;
import be.kdg.team11.player.domain.projections.GameReference;
import be.kdg.team11.sharedkernel.events.DomainEvent;
import be.kdg.team11.sharedkernel.events.player.*;

import java.time.LocalDate;
import java.util.*;
/**
 * Aggregate Root for the Player subdomain.
 * Represents a player with their games, gameAchievements, and profile information.
 * Manages the complete lifecycle: registration, game purchases, gameAchievements.
 */
public class Player {
    private static final String DEFAULT_PICTURE_URL="https://static.vecteezy.com/system/resources/thumbnails/020/765/399/small/default-profile-account-unknown-icon-black-silhouette-free-vector.jpg";

    private final PlayerId playerId;
    private final Username username;
    private String pictureUrl;
    private final LocalDate joinedDate;
    private GameReference favoriteGame;
    private final Set<UnlockedPlatformAchievement> unlockedPlatformAchievements = new HashSet<>();
    private final Set<UnlockedGameAchievement> unlockedGameAchievements = new HashSet<>();
    private final List<DomainEvent> eventStore = new ArrayList<>();



    /**
     * Private constructor for recreating player from persistent storage.
     */
    public Player(PlayerId playerId,Username username,String pictureUrl, LocalDate joinedDate, Set<UnlockedPlatformAchievement> unlockedPlatformAchievements, Set<UnlockedGameAchievement> unlockedGameAchievements, GameReference favoriteGame) {
        validateJoinedDate(joinedDate);

        this.playerId = playerId;
        this.username = username;
        this.pictureUrl = pictureUrl;
        this.joinedDate = joinedDate;
        this.unlockedPlatformAchievements.addAll(unlockedPlatformAchievements);
        this.unlockedGameAchievements.addAll(unlockedGameAchievements);
        this.favoriteGame = favoriteGame;
    }

    /**
     * Factory method for creating a new player.
     * Initial state: no games, no gameAchievements.
     */
    public static Player create(PlayerId playerId, Username username) {

        Player player = new Player(
                playerId,
                username,
                DEFAULT_PICTURE_URL,
                LocalDate.now(),
                Collections.emptySet(),
                Collections.emptySet(),
                null);

        PlayerCreatedEvent event = new PlayerCreatedEvent(playerId.playerId(),username.username(), DEFAULT_PICTURE_URL, player.joinedDate);
        player.eventStore.add(event);

        return player;
    }

    public void changePictureUrl(String pictureUrl){
        this.pictureUrl = pictureUrl;
        PlayerChangedPictureUrlEvent event = new PlayerChangedPictureUrlEvent(playerId.playerId(),pictureUrl);
        this.eventStore.add(event);
    }

    /**
     * Player marks a game as favorite.
     */
    public void changeFavoriteGame(GameReference gameReference) {
        this.favoriteGame = gameReference;

        PlayerChangedFavoriteGameEvent event = new PlayerChangedFavoriteGameEvent(
                this.playerId.playerId(),
                gameReference.gameId()
        );
        this.eventStore.add(event);
    }

    /**
     * Player removes a game from favorites.
     */
    public void removeFavoriteGame() {
        this.favoriteGame = null;

        PlayerRemovedFavoriteGameEvent event = new PlayerRemovedFavoriteGameEvent(
                this.playerId.playerId()
        );
        this.eventStore.add(event);
    }

    /**
     * Player unlocks an achievement.
     * Throws InvalidAchievementForPlayerException if achievement operation fails.
     */
    public void unlockPlatformAchievement(PlatformAchievementId platformAchievementId) {
        validatePlatformAchievementNotAlreadyUnlocked(platformAchievementId);

        UnlockedPlatformAchievement achievement = UnlockedPlatformAchievement.now(platformAchievementId);
        this.unlockedPlatformAchievements.add(achievement);
    }

    /**
     * Player unlocks a game-specific achievement.
    */
    public void unlockGameAchievement(GameReference gameReference, String achievementCode) {
        validateGameAchievementNotAlreadyUnlocked(gameReference, achievementCode);

        UnlockedGameAchievement achievement = UnlockedGameAchievement.now(gameReference, achievementCode);
        this.unlockedGameAchievements.add(achievement);
    }


    private static void validateJoinedDate(LocalDate joinedDate) {
        if (joinedDate.isAfter(LocalDate.now())) {
            throw new InvalidPlayerException("Joined date cannot be in the future");
        }
    }


    private void validatePlatformAchievementNotAlreadyUnlocked(PlatformAchievementId platformAchievementId) {
        boolean alreadyUnlocked = unlockedPlatformAchievements.stream()
                .anyMatch(ufa -> ufa.platformAchievementId().equals(platformAchievementId));

        if (alreadyUnlocked) {
            throw new InvalidPlatformAchievementForPlayerException(
                    String.format("Player %s already unlocked achievement %s",
                            playerId.playerId(), platformAchievementId.achievementId())
            );
        }
    }


    private void validateGameAchievementNotAlreadyUnlocked(GameReference gameReference, String achievementCode) {
        boolean alreadyUnlocked = unlockedGameAchievements.stream()
                .anyMatch(uga -> uga.gameReference().equals(gameReference) &&
                        uga.code().equals(achievementCode));

        if (alreadyUnlocked) {
            throw new InvalidPlatformAchievementForPlayerException(
                    String.format("Player %s already unlocked achievement %s in game %s",
                            playerId.playerId(), achievementCode, gameReference.gameId())
            );
        }
    }

    public UUID getFavoriteGameId() {
        return favoriteGame == null ? null : favoriteGame.gameId();
    }

    public PlayerId getPlayerId() {
        return playerId;
    }

    public Username getUsername() {
        return username;
    }

    public String getPictureUrl() {
        return pictureUrl;
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
    public List<DomainEvent> getEventStore() {
        return Collections.unmodifiableList(eventStore);
    }
}
