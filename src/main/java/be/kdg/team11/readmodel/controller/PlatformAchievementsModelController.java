package be.kdg.team11.readmodel.controller;

import be.kdg.team11.readmodel.controller.dto.PlatformAchievementDto;
import be.kdg.team11.readmodel.service.platformachievement.PlatformAchievementModelService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/platform-achievements")
public class PlatformAchievementsModelController {
    private final PlatformAchievementModelService platformAchievementModelService;

    public PlatformAchievementsModelController(PlatformAchievementModelService platformAchievementModelService) {
        this.platformAchievementModelService = platformAchievementModelService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PlatformAchievementDto>> getPlatformAchievements() {
        return ResponseEntity.ok(platformAchievementModelService.getAll());
    }
}
