package team11.platform_backend.game.adapter.out.mapper;

import org.springframework.stereotype.Component;
import team11.platform_backend.game.adapter.out.jpa.GameJpaEntity;
import team11.platform_backend.game.adapter.out.jpa.RuleJpaEntity;
import team11.platform_backend.game.domain.game.Game;
import team11.platform_backend.game.domain.game.GameId;
import team11.platform_backend.game.domain.game.Rule;
import team11.platform_backend.sharedkernel.valueobjects.Url;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GameJpaMapper {
    public GameJpaEntity toJpaEntity(Game game) {
        GameJpaEntity entity = new GameJpaEntity();
        entity.setGameId(game.getGameId().gameId());
        entity.setGameName(game.getGameName());
        entity.setGameDescription(game.getGameDescription());
        entity.setGamePrice(game.getGamePrice());
        entity.setPictureUrls(game.getPictureUrls().stream()
                .map(Url::value)
                .collect(Collectors.toList()));
        entity.setGameCreatorName(game.getGameCreatorName());
        entity.setGameUrl(game.getGameUrl().value());
        entity.setGameState(game.getGameState());
        entity.setAiPlayerUrl(game.getAiPlayerUrl() != null ? game.getAiPlayerUrl().value() : null);

        // Map Rules (OneToMany relationship)
        List<RuleJpaEntity> ruleEntities = game.getRules().stream()
                .map(rule -> new RuleJpaEntity(
                        entity,
                        rule.ruleName(),
                        rule.ruleDescription(),
                        new HashSet<>(rule.ruleCategories())
                ))
                .toList();
        entity.setRules(ruleEntities);

        return entity;
    }

    public Game toDomain(GameJpaEntity entity) {
        List<Url> pictureUrls = entity.getPictureUrls().stream()
                .map(Url::new)
                .collect(Collectors.toList());

        List<Rule> rules = entity.getRules().stream()
                .map(ruleEntity -> new Rule(
                        ruleEntity.getRuleName(),
                        ruleEntity.getRuleDescription(),
                        new ArrayList<>(ruleEntity.getRuleCategories())
                ))
                .toList();

        Url aiPlayerUrl = entity.getAiPlayerUrl() != null ? new Url(entity.getAiPlayerUrl()) : null;

        return new Game(
                new GameId(entity.getGameId()),
                entity.getGameName(),
                entity.getGameDescription(),
                entity.getGamePrice(),
                pictureUrls,
                entity.getGameCreatorName(),
                new Url(entity.getGameUrl()),
                entity.getGameState(),
                rules,
                aiPlayerUrl
        );
    }
}
