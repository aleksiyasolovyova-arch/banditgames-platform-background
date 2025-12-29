package be.kdg.team11.readmodel.service.achievement;

import be.kdg.team11.readmodel.controller.dto.AchievementModelDto;
import be.kdg.team11.readmodel.models.AchievementModel;
import be.kdg.team11.readmodel.repository.AchievementModelRepository;
import be.kdg.team11.readmodel.repository.GameModelRepository;
import be.kdg.team11.readmodel.repository.UnlockedAchievementModelRepository;
import be.kdg.team11.sharedkernel.events.achievement.PlatformAchievementCreatedEvent;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
public class AchievementModelServiceImpl implements AchievementModelService{
    private final AchievementModelRepository achievementModelRepository;
    private final UnlockedAchievementModelRepository unlockedAchievementModelRepository;
    private final AchievementModelMapper achievementModelMapper;
    private final GameModelRepository gameModelRepository;
    public AchievementModelServiceImpl(AchievementModelRepository achievementModelRepository,
                                       UnlockedAchievementModelRepository unlockedAchievementModelRepository,
                                       AchievementModelMapper achievementModelMapper,
                                       GameModelRepository gameModelRepository
                                       ) {
        this.achievementModelRepository = achievementModelRepository;
        this.unlockedAchievementModelRepository = unlockedAchievementModelRepository;
        this.achievementModelMapper = achievementModelMapper;
        this.gameModelRepository = gameModelRepository;
    }

    @Override
    public void project(PlatformAchievementCreatedEvent event) {

        AchievementModel achievement = new AchievementModel();
        achievement.setAchievementId(event.achievementId());
        achievement.setName(event.name());
        achievement.setDescription(event.description());
        achievement.setPictureUrl(event.pictureUrl());
        achievement.setType(event.type());
        achievement.setRequiredValue(event.requiredValue());

        achievementModelRepository.save(achievement);

    }

    @Override
    public List<AchievementModelDto> getAll() {
        return achievementModelRepository.findAll().stream().map(
                achievementModelMapper::toAdminAchievementModelDto
        ).toList();
    }
}
