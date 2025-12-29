package be.kdg.team11.content.adapter.in;

import be.kdg.team11.content.adapter.in.mapper.PlatformAchievementMapper;
import be.kdg.team11.content.adapter.in.request.CreatePlatformAchievementRequest;
import be.kdg.team11.content.adapter.in.response.PlatformAchievementDto;
import be.kdg.team11.content.domain.platformachievement.PlatformAchievement;
import be.kdg.team11.content.port.in.CreatePlatformAchievementPort;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("platform-achievements")
public class PlatformAchievementsController {
    private final CreatePlatformAchievementPort createPlatformAchievementPort;
    private final PlatformAchievementMapper platformAchievementMapper;

    public PlatformAchievementsController(CreatePlatformAchievementPort createPlatformAchievementPort,
                                          PlatformAchievementMapper platformAchievementMapper) {
        this.createPlatformAchievementPort = createPlatformAchievementPort;
        this.platformAchievementMapper = platformAchievementMapper;
    }

    /**
     * Creates a new achievement definition.
     * Endpoint: POST /gameAchievements
     * Required Role: ADMIN
     *
     * REQUEST BODY (CreateAchievementRequest):
     * - platformAchievementName (String, required): Name of the achievement (1-100 chars)
     * - description (String, required): Achievement description (5-500 chars)
     * - pictureUrl (String, required): URL to achievement badge/image
     * - platformAchievementType (AchievementType, required): Type of achievement (enum)
     * - requiredValue (long, required): Threshold value to unlock achievement (non-negative)
     * -
     * RESPONSE BODY (AchievementDto):
     * - platformAchievementId (UUID): Unique identifier for the created achievement
     * - name (String): Achievement name
     * - description (String): Achievement description
     * - pictureUrl (String): URL to achievement image
     * - platformAchievementType (String): Achievement type
     * - requiredValue (long): Required value to unlock
     * -
     * HTTP Status Codes:
     * - 201 Created: Achievement successfully created
     * - 400 Bad Request: Validation failed (invalid/missing fields)
     * - 403 Forbidden: User lacks ADMIN role required for this operation
     * - 500 Internal Server Error: Unexpected server error
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PlatformAchievementDto> createPlatformAchievement(
            @Valid @RequestBody CreatePlatformAchievementRequest request) {
        PlatformAchievement createdPlatformAchievement = createPlatformAchievementPort.createPlatformAchievement(
                platformAchievementMapper.toCommand(request)
        );
        PlatformAchievementDto response = platformAchievementMapper.toResponse(createdPlatformAchievement);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}