package team11.platform_backend.player.domain.player;

import team11.platform_backend.sharedkernel.valueobjects.Url;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private final PlayerId playerId;
    private final NameInfo nameInfo;
    private final Email email;
    private final LocalDate joinedDate;
    private final Url profilePictureUrl;
    private final List<AchievementId> unlockedAchievements = new ArrayList<>();



    // for getting (get methods)
    public Player(PlayerId playerId, NameInfo nameInfo, Email email, LocalDate joinedDate, Url profilePictureUrl, List<AchievementId> unlockedAchievements) {
        this.playerId = playerId;
        this.nameInfo = nameInfo;
        this.email = email;
        this.joinedDate = joinedDate;
        this.profilePictureUrl = profilePictureUrl;
        this.unlockedAchievements.addAll(unlockedAchievements);
    }

    //for creating (post methods)
    public Player(NameInfo nameInfo, Email email, Url profilePictureUrl) {
        this.playerId = PlayerId.createPlayerId();
        this.nameInfo = nameInfo;
        this.email = email;
        this.joinedDate = LocalDate.now();
        this.profilePictureUrl = profilePictureUrl;
    }

    public PlayerId getPlayerId() {
        return playerId;
    }

    public NameInfo getNameInfo() {
        return nameInfo;
    }

    public Email getEmail() {
        return email;
    }

    public LocalDate getJoinedDate() {
        return joinedDate;
    }

    public Url getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public List<AchievementId> getUnlockedAchievements() {
        return unlockedAchievements;
    }
}
