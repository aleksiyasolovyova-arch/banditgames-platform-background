package be.kdg.team11.readmodel.service.achievement;

import be.kdg.team11.readmodel.models.AchievementModel;
import be.kdg.team11.readmodel.models.AchievementModelType;
import be.kdg.team11.readmodel.repository.AchievementModelRepository;
import be.kdg.team11.sharedkernel.events.achievement.AchievementCreatedEvent;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AchievementModelServiceImpl implements AchievementModelService{
    private final AchievementModelRepository achievementModelRepository;
    public AchievementModelServiceImpl(AchievementModelRepository achievementModelRepository) {
        this.achievementModelRepository = achievementModelRepository;
    }

    @Override
    public void project(AchievementCreatedEvent event) {

        AchievementModel achievement = new AchievementModel();
        achievement.setPlatformAchievementId(event.achievementId());
        achievement.setName(event.name());
        achievement.setDescription(event.description());
        achievement.setPictureUrl(event.pictureUrl());
        achievement.setType(AchievementModelType.PLATFORM);
        achievement.setRequiredValue(event.requiredValue());
        achievement.setCreatedAt(event.eventPit());
        achievementModelRepository.save(achievement);

    }
}
