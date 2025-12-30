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
     * Creates a new platform-wide achievement definition.
     * Endpoint: POST /gameAchievements
     * Required Role: ADMIN
     * <p>
     * REQUEST BODY (CreatePlatformAchievementRequest):
     * - platformAchievementName (String, required): Name of the achievement (1-100 characters, not blank)
     * - description (String, required): Achievement description (5-500 characters, not blank)
     * - pictureUrl (String, required): URL to the achievement badge/image (must be a valid URL, not blank)
     * - platformAchievementType (PlatformAchievementType, required): Type of achievement (enum)
     * - requiredValue (long, required): Threshold value to unlock the achievement (non-null; must be a valid non-negative domain value)
     * <p>
     * RESPONSE BODY (PlatformAchievementDto):
     * - platformAchievementId (UUID): Unique identifier for the created achievement
     * - name (String): Achievement name
     * - description (String): Achievement description
     * - pictureUrl (String): URL to the achievement image
     * - platformAchievementType (String): Achievement type
     * - requiredValue (long): Required value to unlock the achievement
     * <p>
     * HTTP Status Codes:
     * - 201 Created: Achievement successfully created
     * - 400 Bad Request: Validation failed (e.g., invalid or missing fields, invalid URL, or length constraints violated)
     * - 403 Forbidden: User lacks ADMIN role required for this operation
     * - 500 Internal Server Error: Unexpected server error during achievement creation
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PlatformAchievementDto> createPlatformAchievement(
            @Valid @RequestBody CreatePlatformAchievementRequest request) {
        PlatformAchievement createdPlatformAchievement = createPlatformAchievementPort.create(
                platformAchievementMapper.toCommand(request)
        );
        PlatformAchievementDto response = platformAchievementMapper.toResponse(createdPlatformAchievement);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}