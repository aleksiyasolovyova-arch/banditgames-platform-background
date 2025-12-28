package be.kdg.team11.readmodel.controller;

import be.kdg.team11.readmodel.controller.dto.AchievementModelDto;
import be.kdg.team11.readmodel.service.achievement.AchievementModelService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/achievements")
public class AchievementsModelController {
    private final AchievementModelService achievementModelService;
    public AchievementsModelController(AchievementModelService achievementModelService) {
        this.achievementModelService = achievementModelService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AchievementModelDto>> getAllAchievements() {
        return ResponseEntity.ok(achievementModelService.getAll());
    }
}
