package be.kdg.team11.content.adapter.in;

import be.kdg.team11.content.adapter.in.mapper.AchievementMapper;
import be.kdg.team11.content.adapter.in.request.CreateAchievementRequest;
import be.kdg.team11.content.adapter.in.response.AchievementDto;
import be.kdg.team11.content.domain.achievement.Achievement;
import be.kdg.team11.content.port.in.CreateAchievementPort;
import be.kdg.team11.content.port.out.LoadAchievementsPort;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("achievements")
public class AchievementsController {
    private final CreateAchievementPort createAchievementPort;
    private final LoadAchievementsPort loadAllAchievementsPort;
    private final AchievementMapper achievementMapper;

    public AchievementsController(CreateAchievementPort createAchievementPort,
                                  LoadAchievementsPort loadAllAchievementsPort,
                                  AchievementMapper achievementMapper) {
        this.createAchievementPort = createAchievementPort;
        this.loadAllAchievementsPort = loadAllAchievementsPort;
        this.achievementMapper = achievementMapper;
    }

    /**
     * Retrieves all achievements in the system.
     * FULL PATH: /achievements (GET)
     * RESPONSE BODY (List<AchievementDto>):
     * - achievementId (UUID): Unique achievement identifier
     * - name (String): Achievement name
     * - description (String): Achievement description
     * - pictureUrl (String): Achievement picture URL
     * - type (String): Achievement type (PLAY_COUNT, WIN_COUNT, FRIEND_COUNT, RECORD_TIME)
     * - requiredValue (long): Value required to unlock the achievement
     * HTTP Status Codes:
     * - 200 OK: Achievements retrieved successfully
     * - 500 Internal Server Error: Unexpected server error
     */
    @GetMapping()
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<AchievementDto>> loadAllAchievements() {
        List<Achievement> achievements = loadAllAchievementsPort.loadAll();
        List<AchievementDto> response = achievements.stream()
                .map(achievementMapper::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    /**
     * Creates a new achievement definition.
     * FULL PATH: /achievements (POST)
     * REQUEST BODY (CreateAchievementRequest):
     * - achievementName (String, required): Name of the achievement (1-100 chars)
     * - description (String, required): Achievement description (5-500 chars)
     * - pictureUrl (String, required): URL to achievement badge/image
     * - achievementType (AchievementType, required): Type of achievement (enum)
     * - requiredValue (long, required): Threshold value to unlock achievement (non-negative)
     * -
     * RESPONSE BODY (AchievementDto):
     * - achievementId (UUID): Unique identifier for the created achievement
     * - name (String): Achievement name
     * - description (String): Achievement description
     * - pictureUrl (String): URL to achievement image
     * - achievementType (String): Achievement type
     * - requiredValue (long): Required value to unlock
     * -
     * HTTP Status Codes:
     * - 201 Created: Achievement successfully created
     * - 400 Bad Request: Validation failed (invalid/missing fields)
     * - 500 Internal Server Error: Unexpected server error
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AchievementDto> createAchievement(
            @Valid @RequestBody CreateAchievementRequest request) {
        Achievement createdAchievement = createAchievementPort.createAchievement(
                achievementMapper.toCommand(request)
        );
        AchievementDto response = achievementMapper.toResponse(createdAchievement);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}