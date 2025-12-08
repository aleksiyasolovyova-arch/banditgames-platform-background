package team11.platform_backend.player.domain.player;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Player {
    private final PlayerId playerId;
    private final LocalDate joinedDate;
    private final Set<AchievementId> unlockedGlobalAchievements = new HashSet<>();
    private final Set<UnlockedGameAchievement> unlockedGameAchievements = new HashSet<>();
    private final Set<OwnedGame> ownedGames = new HashSet<>();

    // for getting (get methods)


    // we also need a constructor for creating


}
