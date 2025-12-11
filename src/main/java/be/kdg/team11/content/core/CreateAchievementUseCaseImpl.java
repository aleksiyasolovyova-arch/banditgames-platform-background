package be.kdg.team11.content.core;

import be.kdg.team11.content.domain.Url;
import be.kdg.team11.content.domain.achievement.Achievement;
import be.kdg.team11.content.domain.achievement.AchievementType;
import be.kdg.team11.content.port.in.CreateAchievementCommand;
import be.kdg.team11.content.port.in.CreateAchievementPort;
import be.kdg.team11.content.port.out.SaveAchievementPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CreateAchievementUseCaseImpl implements CreateAchievementPort {

    private final List<SaveAchievementPort> saveAchievementPorts;

    public CreateAchievementUseCaseImpl(List<SaveAchievementPort> saveAchievementPorts) {
        this.saveAchievementPorts = saveAchievementPorts;
    }

    @Override
    public Achievement createAchievement(CreateAchievementCommand command) {
        Url pictureUrl = new Url(command.pictureUrl());
        AchievementType type = AchievementType.valueOf(command.type());

        Achievement achievement = Achievement.create(
                command.name(),
                command.description(),
                pictureUrl,
                type,
                command.requiredValue()
        );

        saveAchievementPorts.forEach(saveAchievementPort ->
                saveAchievementPort.save(achievement));

        return achievement;
    }
}
