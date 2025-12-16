package be.kdg.team11.content.adapter.in;

import be.kdg.team11.content.adapter.in.mapper.AchievementMapper;
import be.kdg.team11.content.adapter.in.request.CreateAchievementRequest;
import be.kdg.team11.content.adapter.in.response.AchievementDto;
import be.kdg.team11.content.domain.achievement.Achievement;
import be.kdg.team11.content.port.in.CreateAchievementPort;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("achievements")
public class AchievementsController {
    private final CreateAchievementPort createAchievementPort;
    private final AchievementMapper achievementMapper;

    public AchievementsController(CreateAchievementPort createAchievementPort,
                                  AchievementMapper achievementMapper) {
        this.createAchievementPort = createAchievementPort;
        this.achievementMapper = achievementMapper;
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
    public ResponseEntity<AchievementDto> createAchievement(
            @Valid @RequestBody CreateAchievementRequest request) {
        Achievement createdAchievement = createAchievementPort.createAchievement(
                achievementMapper.toCommand(request)
        );
        AchievementDto response = achievementMapper.toResponse(createdAchievement);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}