package be.kdg.team11.content.adapter.out;

import be.kdg.team11.content.adapter.out.jpa.PlatformAchievementJpaEntity;
import be.kdg.team11.content.adapter.out.jpa.PlatformAchievementJpaRepository;
import be.kdg.team11.content.adapter.out.mapper.PlatformAchievementJpaMapper;
import be.kdg.team11.content.domain.platformachievement.PlatformAchievement;
import be.kdg.team11.content.domain.platformachievement.PlatformAchievementId;
import be.kdg.team11.content.port.out.LoadPlatformAchievementPort;
import be.kdg.team11.content.port.out.SavePlatformAchievementPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PlatformAchievementJpaAdapter implements SavePlatformAchievementPort, LoadPlatformAchievementPort {
    private final PlatformAchievementJpaRepository platformAchievementJpaRepository;
    private final PlatformAchievementJpaMapper platformAchievementJpaMapper;

    public PlatformAchievementJpaAdapter(PlatformAchievementJpaRepository platformAchievementJpaRepository,
                                         PlatformAchievementJpaMapper platformAchievementJpaMapper) {
        this.platformAchievementJpaRepository = platformAchievementJpaRepository;
        this.platformAchievementJpaMapper = platformAchievementJpaMapper;
    }

    @Override
    public PlatformAchievement save(PlatformAchievement platformAchievement) {
        PlatformAchievementJpaEntity entity = platformAchievementJpaMapper.toJpaEntity(platformAchievement);
        PlatformAchievementJpaEntity saved = platformAchievementJpaRepository.save(entity);
        return platformAchievementJpaMapper.toDomain(saved);
    }

    @Override
    public Optional<PlatformAchievement> loadBy(PlatformAchievementId platformAchievementId) {
        return platformAchievementJpaRepository.findById(platformAchievementId.achievementId())
                .map(platformAchievementJpaMapper::toDomain);
    }

    @Override
    public List<PlatformAchievement> loadAllExcept(List<PlatformAchievementId> platFormAchievementIds) {
        return platformAchievementJpaRepository.findAllExcluding(
                platFormAchievementIds
                        .stream()
                        .map(PlatformAchievementId::achievementId)
                        .toList()
                )
                .stream()
                .map(platformAchievementJpaMapper::toDomain)
                .toList();
    }
}
