package be.kdg.team11.readmodel.service.platformachievement;

import be.kdg.team11.readmodel.controller.dto.PlatformAchievementDto;
import be.kdg.team11.readmodel.models.PlatformAchievementModel;
import be.kdg.team11.readmodel.repository.PlatformAchievementModelRepository;
import be.kdg.team11.readmodel.repository.GameModelRepository;
import be.kdg.team11.readmodel.repository.AchievementModelRepository;
import be.kdg.team11.sharedkernel.events.achievement.PlatformAchievementCreatedEvent;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
public class PlatformPlatformAchievementModelServiceImpl implements PlatformAchievementModelService {
    private final PlatformAchievementModelRepository platformAchievementModelRepository;
    private final AchievementModelRepository achievementModelRepository;
    private final PlatformAchievementModelMapper platformAchievementModelMapper;
    private final GameModelRepository gameModelRepository;
    public PlatformPlatformAchievementModelServiceImpl(PlatformAchievementModelRepository platformAchievementModelRepository,
                                                       AchievementModelRepository achievementModelRepository,
                                                       PlatformAchievementModelMapper platformAchievementModelMapper,
                                                       GameModelRepository gameModelRepository
                                       ) {
        this.platformAchievementModelRepository = platformAchievementModelRepository;
        this.achievementModelRepository = achievementModelRepository;
        this.platformAchievementModelMapper = platformAchievementModelMapper;
        this.gameModelRepository = gameModelRepository;
    }

    @Override
    public void project(PlatformAchievementCreatedEvent event) {

        PlatformAchievementModel platformAchievement = new PlatformAchievementModel();
        platformAchievement.setAchievementId(event.achievementId());
        platformAchievement.setName(event.name());
        platformAchievement.setDescription(event.description());
        platformAchievement.setPictureUrl(event.pictureUrl());
        platformAchievement.setType(event.type());
        platformAchievement.setRequiredValue(event.requiredValue());

        platformAchievementModelRepository.save(platformAchievement);

    }

    @Override
    public List<PlatformAchievementDto> getAll() {
        return platformAchievementModelRepository.findAll().stream().map(
                platformAchievementModelMapper::toAdminPlatformAchievementModelDto
        ).toList();
    }
}
