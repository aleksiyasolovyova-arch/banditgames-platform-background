package be.kdg.team11.readmodel.controller;

import be.kdg.team11.readmodel.controller.dto.AchievementPlayerResponseDto;
import be.kdg.team11.readmodel.service.achievement.AchievementModelService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/achievements")
public class AchievementsModelController {
    private final AchievementModelService achievementModelService;
    public AchievementsModelController(AchievementModelService achievementModelService) {
        this.achievementModelService = achievementModelService;
    }

    /**
     * GET /achievements
     *
     * Retrieves all platform and game-specific achievements available in the platform,
     * along with the player's unlock status for each achievement.
     *
     * Response Body Structure (List of PlayerAchievementDto):
     *
     * --- SHARED FIELDS (all achievements have these) ---
     * - achievementId (UUID): Unique identifier for the achievement
     * - achievementType (String): Type of achievement - "PLATFORM" or "GAME"
     * - achievementDescription (String): Description of what the achievement requires
     * - unlocked (boolean): Whether this specific player has unlocked this achievement
     * - unlockedAt (LocalDateTime): Timestamp when player unlocked it (null if not unlocked)
     *
     * --- GAME ACHIEVEMENT FIELDS (only if achievementType = "GAME") ---
     * - gameId (UUID): ID of the game this achievement belongs to
     * - gameName (String): Name of the game
     * - gameAchievementCode (String): Code/identifier for achievement within the game
     *
     * --- PLATFORM ACHIEVEMENT FIELDS (only if achievementType = "PLATFORM") ---
     * - platformAchievementId (UUID): Same as achievementId (included for clarity in API)
     * - platformAchievementName (String): Name of the achievement
     * - platformAchievementPictureUrl (String): Icon/image URL for the achievement
     * - platformAchievementType (String): Platform achievement type (PLAYCOUNT, WINCOUNT, FRIENDCOUNT, RECORDTIME)
     * - platformAchievementRequiredValue (long): Value needed to unlock (e.g., "10 wins")
     * - platformAchievementCurrentValue (long): Player's current progress toward this achievement
     *
     * @return ResponseEntity with List of PlayerAchievementDto containing all achievements with unlock status
     */

    @GetMapping
    public ResponseEntity<List<AchievementPlayerResponseDto>> getPlayerAchievements(@AuthenticationPrincipal Jwt token) {
        UUID playerId = UUID.fromString(token.getSubject());
        List<AchievementPlayerResponseDto> achievements = achievementModelService.getPlayerAchievements(playerId);
        return ResponseEntity.ok(achievements);
    }
}
