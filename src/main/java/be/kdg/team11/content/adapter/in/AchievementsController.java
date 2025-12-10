package be.kdg.team11.content.adapter.in;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import be.kdg.team11.content.adapter.in.mapper.AchievementMapper;
import be.kdg.team11.content.adapter.in.request.CreateAchievementRequest;
import be.kdg.team11.content.adapter.in.request.UpdateAchievementRequest;
import be.kdg.team11.content.adapter.in.response.AchievementDto;
import be.kdg.team11.content.domain.achievement.Achievement;
import be.kdg.team11.content.port.in.CreateAchievementPort;

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
