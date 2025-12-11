package be.kdg.team11.content.adapter.out.mapper;

import be.kdg.team11.content.adapter.out.jpa.GameAchievementEmbeddable;
import be.kdg.team11.content.adapter.out.jpa.GameJpaEntity;
import be.kdg.team11.content.domain.Url;
import be.kdg.team11.content.domain.game.Game;
import be.kdg.team11.content.domain.game.GameAchievement;
import be.kdg.team11.content.domain.game.GameId;
import be.kdg.team11.content.domain.game.Rule;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GameJpaMapper {

    public GameJpaEntity toJpaEntity(Game game) {
        GameJpaEntity entity = new GameJpaEntity();
        entity.setGameId(game.getGameId().gameId());
        entity.setName(game.getName());
        entity.setDescription(game.getDescription());
        entity.setPrice(game.getPrice());
        entity.setPictureUrl(game.getPictureUrl().value());
        entity.setGameUrl(game.getGameUrl().value());
        entity.setGameCreatorName(game.getGameCreatorName());
        entity.setRegistrationState(game.getRegistrationState());

        // Map Rules (simple strings)
        List<String> ruleDescriptions = game.getRules().stream()
                .map(Rule::description)
                .collect(Collectors.toList());
        entity.setRules(ruleDescriptions);

        // Map Achievements (embeddables)
        List<GameAchievementEmbeddable> achievementEmbeddables = game.getAchievements().stream()
                .map(achievement -> {
                    GameAchievementEmbeddable embeddable =
                            new GameAchievementEmbeddable();
                    embeddable.setCode(achievement.code());
                    embeddable.setDescription(achievement.description());
                    return embeddable;
                })
                .collect(Collectors.toList());
        entity.setAchievementEmbeddables(achievementEmbeddables);

        return entity;
    }

    public Game toDomain(GameJpaEntity entity) {
        // Map Rules (from strings to Rule value objects)
        List<Rule> rules = entity.getRules().stream()
                .map(Rule::new)
                .collect(Collectors.toList());

        // Map Achievements (from embeddables to GameAchievement value objects)
        List<GameAchievement> achievements = entity.getAchievementEmbeddables().stream()
                .map(embeddable -> new GameAchievement(
                        embeddable.getCode(),
                        embeddable.getDescription()
                ))
                .collect(Collectors.toList());

        return new Game(
                new GameId(entity.getGameId()),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                new Url(entity.getPictureUrl()),
                new Url(entity.getGameUrl()),
                entity.getGameCreatorName(),
                entity.getRegistrationState(),
                rules,
                achievements
        );
    }
}