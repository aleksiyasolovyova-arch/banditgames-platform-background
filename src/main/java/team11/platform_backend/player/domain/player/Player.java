package team11.platform_backend.player.domain.player;

import team11.platform_backend.sharedkernel.valueobjects.Url;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Player {
    private final PlayerId playerId;
    private final LocalDate joinedDate;
    private final Url profilePictureUrl;
    private final Set<AchievementId> unlockedGlobalAchievements = new HashSet<>();
    private final Set<UnlockedGameAchievement> unlockedGameAchievements = new HashSet<>();
    private final Set<OwnedGame> ownedGames = new HashSet<>();

    // for getting (get methods)
    public Player(PlayerId playerId, LocalDate joinedDate, Url profilePictureUrl, List<UnlockedGameAchievement> unlockedGameAchievements, List<OwnedGame> ownedGames) {
        this.playerId = playerId;
        this.joinedDate = joinedDate;
        this.profilePictureUrl = profilePictureUrl;
        this.unlockedGameAchievements.addAll(unlockedGameAchievements);
        this.ownedGames.addAll(ownedGames);
    }

    // we also need a constructor for creating


    public PlayerId getPlayerId() {
        return playerId;
    }

    public LocalDate getJoinedDate() {
        return joinedDate;
    }

    public Url getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public Set<UnlockedGameAchievement> getUnlockedGameAchievements() {
        return unlockedGameAchievements;
    }

    public Set<OwnedGame> getOwnedGames() {
        return ownedGames;
    }
}
