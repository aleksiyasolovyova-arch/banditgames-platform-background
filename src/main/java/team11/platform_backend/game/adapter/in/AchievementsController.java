package team11.platform_backend.game.adapter.in;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team11.platform_backend.game.adapter.in.mapper.AchievementMapper;
import team11.platform_backend.game.adapter.in.request.CreateAchievementRequest;
import team11.platform_backend.game.adapter.in.request.UpdateAchievementRequest;
import team11.platform_backend.game.adapter.in.response.AchievementDto;
import team11.platform_backend.game.domain.achievement.Achievement;
import team11.platform_backend.game.port.in.CreateAchievementPort;
import team11.platform_backend.game.port.in.UpdateAchievementPort;

import java.util.UUID;

@RestController
@RequestMapping("achievements")
public class AchievementsController {
    private final CreateAchievementPort createAchievementPort;
    private final AchievementMapper achievementMapper;
    private final UpdateAchievementPort updateAchievementPort;

    public AchievementsController(CreateAchievementPort createAchievementPort,
                                  AchievementMapper achievementMapper,
                                  UpdateAchievementPort updateAchievementPort) {
        this.createAchievementPort = createAchievementPort;
        this.achievementMapper = achievementMapper;
        this.updateAchievementPort = updateAchievementPort;
    }

    @PostMapping
    public ResponseEntity<AchievementDto> createAchievement(
            @Valid @RequestBody CreateAchievementRequest request) {
        Achievement createdAchievement = createAchievementPort.createAchievement(
                achievementMapper.toCommand(request)
        );
        AchievementDto response = achievementMapper.toResponse(createdAchievement);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{achievementId}")
    public ResponseEntity<AchievementDto> updateAchievement(
            @PathVariable UUID achievementId,
            @Valid @RequestBody UpdateAchievementRequest request) {
        Achievement updatedAchievement = updateAchievementPort.updateAchievement(
                achievementMapper.toUpdateCommand(achievementId, request)
        );
        AchievementDto response = achievementMapper.toResponse(updatedAchievement);
        return ResponseEntity.ok(response);
    }
}
