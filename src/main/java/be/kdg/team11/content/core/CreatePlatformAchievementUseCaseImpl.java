package be.kdg.team11.content.core;

import be.kdg.team11.content.domain.platformachievement.PlatformAchievement;
import be.kdg.team11.content.domain.platformachievement.PlatformAchievementType;
import be.kdg.team11.content.port.in.CreatePlatformAchievementCommand;
import be.kdg.team11.content.port.in.CreatePlatformAchievementPort;
import be.kdg.team11.content.port.out.SavePlatformAchievementPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CreatePlatformAchievementUseCaseImpl implements CreatePlatformAchievementPort {

    private final List<SavePlatformAchievementPort> savePlatformAchievementPorts;

    public CreatePlatformAchievementUseCaseImpl(List<SavePlatformAchievementPort> savePlatformAchievementPorts) {
        this.savePlatformAchievementPorts = savePlatformAchievementPorts;
    }

    @Override
    public PlatformAchievement create(CreatePlatformAchievementCommand command) {
        PlatformAchievementType type = PlatformAchievementType.valueOf(command.type());

        PlatformAchievement platformAchievement = PlatformAchievement.create(
                command.name(),
                command.description(),
                command.pictureUrl(),
                type,
                command.requiredValue()
        );

        savePlatformAchievementPorts.forEach(savePlatformAchievementPort ->
                savePlatformAchievementPort.save(platformAchievement));

        return platformAchievement;
    }
}
