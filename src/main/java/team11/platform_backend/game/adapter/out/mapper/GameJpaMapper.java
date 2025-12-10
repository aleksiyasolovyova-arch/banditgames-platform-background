package team11.platform_backend.game.adapter.out.mapper;

import org.springframework.stereotype.Component;
import team11.platform_backend.game.adapter.out.jpa.GameAchievementEmbeddable;
import team11.platform_backend.game.adapter.out.jpa.GameJpaEntity;
import team11.platform_backend.game.domain.game.Game;
import team11.platform_backend.game.domain.game.GameAchievement;
import team11.platform_backend.game.domain.game.GameId;
import team11.platform_backend.game.domain.game.Rule;
import team11.platform_backend.game.domain.Url;

import java.util.List;

@Component
public class GameJpaMapper {

    public GameJpaEntity toJpaEntity(Game game) {
        GameJpaEntity entity = new GameJpaEntity();
        entity.setGameId(game.getGameId().gameId());
        entity.setName(game.getName());
        entity.setDescription(game.getDescription());
        entity.setPrice(game.getPrice());
        entity.setPictureUrl(game.getPictureUrl().value());
        entity.setGameCreatorName(game.getGameCreatorName());
        entity.setGameUrl(game.getGameUrl().value());
        entity.setRegistrationState(game.getRegistrationState());

        List<String> ruleDescriptions = game.getRules().stream()
                .map(Rule::description)
                .toList();
        entity.setRules(ruleDescriptions);

        List<GameAchievementEmbeddable> achievementEmbeddables = game.getAchievements().stream()
                .map(achievement -> {
                    GameAchievementEmbeddable achievementEmbeddable = new GameAchievementEmbeddable();
                    achievementEmbeddable.setCode(achievement.code());
                    achievementEmbeddable.setDescription(achievement.description());
                    return achievementEmbeddable;
                })
                .toList();
        entity.setAchievements(achievementEmbeddables);

        return entity;
    }

    public Game toDomain(GameJpaEntity entity) {
        List<Rule> rules = entity.getRules().stream()
                .map(Rule::new)
                .toList();

        List<GameAchievement> achievements = entity.getAchievements().stream()
                .map(embeddable -> new GameAchievement(
                        embeddable.getCode(),
                        embeddable.getDescription()
                ))
                .toList();

        Game game = new Game(
                new GameId(entity.getGameId()),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                new Url(entity.getPictureUrl()),
                entity.getGameCreatorName(),
                new Url(entity.getGameUrl()),
                entity.getRegistrationState()
        );

        rules.forEach(rule -> game.getRules().add(rule));
        achievements.forEach(achievement -> game.getAchievements().add(achievement));

        return game;
    }
}