package be.kdg.team11.content.adapter.out.mapper;

import be.kdg.team11.content.adapter.out.jpa.PlayerStatisticsJpaEntity;
import be.kdg.team11.content.domain.achievement.AchievementId;
import be.kdg.team11.content.domain.projections.PlayerStatistics;
import org.springframework.stereotype.Component;

@Component
public class PlayerStatisticsJpaMapper {
    public PlayerStatistics toDomain(PlayerStatisticsJpaEntity entity) {
        return new PlayerStatistics(
                entity.getPlayerId(),
                entity.getTotalGamesPlayed(),
                entity.getTotalWins(),
                entity.getTotalFriends(),
                entity.getBestRecordTime(),
                entity.getUnlockedAchievements().stream().map(
                        AchievementId::of
                ).toList()
        );
    }

    public PlayerStatisticsJpaEntity toJpaEntity(PlayerStatistics playerStatistics) {
        PlayerStatisticsJpaEntity entity = new PlayerStatisticsJpaEntity();
        entity.setPlayerId(playerStatistics.getPlayerId());
        entity.setTotalGamesPlayed(playerStatistics.getTotalGamesPlayed());
        entity.setTotalWins(playerStatistics.getTotalWins());
        entity.setTotalFriends(playerStatistics.getTotalFriends());
        entity.setBestRecordTime(playerStatistics.getBestRecordTime());
        entity.setUnlockedAchievements(playerStatistics.getUnlockedAchievements().stream().map(AchievementId::achievementId).toList());
        return entity;
    }
}