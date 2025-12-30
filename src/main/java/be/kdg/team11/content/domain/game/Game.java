package be.kdg.team11.content.domain.game;

import be.kdg.team11.content.domain.game.exeptions.InvalidGameDataException;
import be.kdg.team11.content.domain.game.exeptions.InvalidGameStateException;
import be.kdg.team11.sharedkernel.events.DomainEvent;
import be.kdg.team11.sharedkernel.events.game.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Aggregate Root for the Game subdomain.
 * Represents a publishable game on the platform.
 */
public class Game {
    private final GameId gameId;
    private final String name;
    private final String description;
    private String pictureUrl;
    private String gameUrl;
    private final String gameCreatorName;
    private GameReviewState reviewState;
    private final List<Rule> rules = new ArrayList<>();
    private final List<GameAchievement> achievements = new ArrayList<>();
    private boolean playableWithAI;

    private final List<DomainEvent> eventStore = new ArrayList<>();


    public Game(
            GameId gameId,
            String name,
            String description,
            String pictureUrl,
            String gameUrl,
            String gameCreatorName,
            GameReviewState reviewState,
            List<Rule> rules,
            List<GameAchievement> achievements,
            boolean playableWithAI
    ) {
        this.gameId = gameId;
        this.name = name;
        this.description = description;
        this.pictureUrl = pictureUrl;
        this.gameUrl = gameUrl;
        this.gameCreatorName = gameCreatorName;
        this.reviewState = reviewState;
        this.rules.addAll(rules);
        this.achievements.addAll(achievements);
        this.playableWithAI = playableWithAI;
    }

    public static Game register(String name,
                                String description,
                                String pictureUrl,
                                String gameUrl,
                                String gameCreatorName,
                                List<Rule> rules,
                                List<GameAchievement> achievements,
                                boolean playableWithAI) {

        Game game = new Game(
                GameId.create(),
                name,
                description,
                pictureUrl,
                gameUrl,
                gameCreatorName,
                GameReviewState.PENDING,
                rules,
                achievements,
                playableWithAI

        );

        List<GameRegisteredEvent.RuleRecord> ruleRecords = rules.stream()
                .map(rule -> GameRegisteredEvent.RuleRecord.of(rule.description()))
                .toList();

        List<GameRegisteredEvent.GameAchievementRecord> achievementRecords = achievements.stream()
                .map(achievement -> GameRegisteredEvent.GameAchievementRecord.of(
                        achievement.code(),
                        achievement.description()
                ))
                .toList();

        GameRegisteredEvent event = new GameRegisteredEvent(
                game.gameId.gameId(),
                name,
                description,
                pictureUrl,
                gameUrl,
                gameCreatorName,
                ruleRecords,
                achievementRecords,
                playableWithAI,
                GameReviewState.PENDING.name()
        );
        game.eventStore.add(event);

        return game;
    }

    public void pass() {
        if (this.reviewState != GameReviewState.PENDING) {
            throw new InvalidGameStateException(
                    "Cannot pass game review: current state is " + this.reviewState + ", expected PENDING"
            );
        }
        this.reviewState = GameReviewState.PASSED;
        PassedGameReviewEvent event = new PassedGameReviewEvent(this.gameId.gameId(), this.gameUrl, GameReviewState.PASSED.name());

        this.eventStore.add(event);
    }

    public void fail() {
        if (this.reviewState != GameReviewState.PENDING) {
            throw new InvalidGameStateException(
                    "Cannot fail game review: current state is " + this.reviewState + ", expected PENDING"
            );
        }
        this.reviewState = GameReviewState.FAILED;
        FailedGameReviewEvent event = new FailedGameReviewEvent(this.gameId.gameId(), GameReviewState.FAILED.name());

        this.eventStore.add(event);
    }

    /**
     * Updates game URLs for maintenance purposes.
     * Allows updating icon/screenshot and playable game links without recreating the aggregate.
     *
     */
    public void modifyUrls(String pictureUrl, String gameUrl) {

        GameUrlsModifiedEvent event = new GameUrlsModifiedEvent(
                this.gameId.gameId(),
                pictureUrl,
                gameUrl
        );

        this.pictureUrl = pictureUrl;
        this.gameUrl = gameUrl;

        this.eventStore.add(event);
    }

    public void togglePlayableWithAI() {
        this.playableWithAI = !this.playableWithAI;
        GameToggledPlayableWithAIEvent event = new GameToggledPlayableWithAIEvent(
                this.gameId.gameId(),
                this.playableWithAI
        );
        this.eventStore.add(event);
    }


    private static void validateGamePrice(BigDecimal price) {

        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidGameDataException(
                    "Game price cannot be negative, received: " + price
            );
        }
    }


    public GameId getGameId() {
        return gameId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public String getGameCreatorName() {
        return gameCreatorName;
    }

    public String getGameUrl() {
        return gameUrl;
    }

    public GameReviewState getReviewState() {
        return reviewState;
    }

    public List<Rule> getRules() {
        return Collections.unmodifiableList(rules);
    }

    public List<GameAchievement> getAchievements() {
        return Collections.unmodifiableList(achievements);
    }

    public boolean isPlayableWithAI() {
        return playableWithAI;
    }

    public List<DomainEvent> getEventStore() {
        return Collections.unmodifiableList(eventStore);
    }
}
