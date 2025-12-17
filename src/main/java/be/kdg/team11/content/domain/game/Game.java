package be.kdg.team11.content.domain.game;

import be.kdg.team11.content.domain.game.exeptions.InvalidGameDataException;
import be.kdg.team11.content.domain.game.exeptions.InvalidGameStateException;
import be.kdg.team11.content.domain.Url;
import be.kdg.team11.content.domain.game.exeptions.InvalidGameUrlException;
import be.kdg.team11.sharedkernel.events.*;
import be.kdg.team11.sharedkernel.events.game.PassedGameReviewEvent;
import be.kdg.team11.sharedkernel.events.game.GameRegisteredEvent;
import be.kdg.team11.sharedkernel.events.game.FailedGameReviewEvent;
import be.kdg.team11.sharedkernel.events.game.GameUrlsModifiedEvent;

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
    private final BigDecimal price;
    private Url pictureUrl;
    private Url gameUrl;
    private final String gameCreatorName;
    private GameReviewState reviewState;
    private final List<Rule> rules = new ArrayList<>();
    private final List<GameAchievement> achievements = new ArrayList<>();

    private final List<DomainEvent> eventStore = new ArrayList<>();


    public Game(
            GameId gameId,
            String name,
            String description,
            BigDecimal price,
            Url pictureUrl,
            Url gameUrl,
            String gameCreatorName,
            GameReviewState reviewState,
            List<Rule> rules,
            List<GameAchievement> achievements
    ) {
        this.gameId = gameId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.pictureUrl = pictureUrl;
        this.gameUrl = gameUrl;
        this.gameCreatorName = gameCreatorName;
        this.reviewState = reviewState;
        this.rules.addAll(rules);
        this.achievements.addAll(achievements);
    }

    public static Game register(String name, String description, BigDecimal price, Url pictureUrl, Url gameUrl, String gameCreatorName, List<Rule> rules, List<GameAchievement> achievements) {
        validateGameName(name);
        validateGameDescription(description);
        validateGamePrice(price);
        validateGamePictureUrl(pictureUrl);
        validateGameUrl(gameUrl);
        validateGameCreatorName(gameCreatorName);

        Game game = new Game(
                GameId.create(),
                name,
                description,
                price,
                pictureUrl,
                gameUrl,
                gameCreatorName,
                GameReviewState.PENDING,
                rules,
                achievements
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
                price,
                pictureUrl.toString(),
                gameUrl.toString(),
                gameCreatorName,
                ruleRecords,
                achievementRecords
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
        PassedGameReviewEvent event = new PassedGameReviewEvent(this.gameId.gameId());
        this.reviewState = GameReviewState.PASSED;
        this.eventStore.add(event);
    }

    public void fail() {
        if (this.reviewState != GameReviewState.PENDING) {
            throw new InvalidGameStateException(
                    "Cannot fail game review: current state is " + this.reviewState + ", expected PENDING"
            );
        }
        FailedGameReviewEvent event = new FailedGameReviewEvent(this.gameId.gameId());
        this.reviewState = GameReviewState.FAILED;
        this.eventStore.add(event);
    }

/**
 * Updates game URLs for maintenance purposes.
 * Allows updating icon/screenshot and playable game links without recreating the aggregate.
 * */
    public void modifyUrls(Url pictureUrl, Url gameUrl){
        validateGamePictureUrl(pictureUrl);
        validateGameUrl(gameUrl);

        GameUrlsModifiedEvent event = new GameUrlsModifiedEvent(
                this.gameId.gameId(),
                pictureUrl.toString(),
                gameUrl.toString()
        );

        this.pictureUrl = pictureUrl;
        this.gameUrl = gameUrl;

       this.eventStore.add(event);
    }


    private static void validateGameName(String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidGameDataException("Game name cannot be null or empty");
        }
    }


    private static void validateGameDescription(String description) {
        if (description == null || description.isBlank()) {
            throw new InvalidGameDataException("Game description cannot be null or empty");
        }
    }

    private static void validateGamePrice(BigDecimal price) {
        if (price == null) {
            throw new InvalidGameDataException("Game price cannot be null");
        }

        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidGameDataException(
                    "Game price cannot be negative, received: " + price
            );
        }
    }


    private static void validateGamePictureUrl(Url pictureUrl) {
        if (pictureUrl == null) {
            throw new InvalidGameUrlException("Game picture URL cannot be null");
        }
    }


    private static void validateGameUrl(Url gameUrl) {
        if (gameUrl == null) {
            throw new InvalidGameUrlException("Game playable URL cannot be null");
        }
    }


    private static void validateGameCreatorName(String gameCreatorName) {
        if (gameCreatorName == null || gameCreatorName.isBlank()) {
            throw new InvalidGameDataException("Game creator name cannot be null or empty");
        }
    }

    //TODO Could wrap getters of Collections in Collections.unmodifiableList to protect aggregate invariants:

    public GameId getGameId() {
        return gameId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Url getPictureUrl() {
        return pictureUrl;
    }

    public String getGameCreatorName() {
        return gameCreatorName;
    }

    public Url getGameUrl() {
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

    public List<DomainEvent> getEventStore() {
        return Collections.unmodifiableList(eventStore);
    }
}
