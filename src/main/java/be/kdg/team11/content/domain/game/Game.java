package be.kdg.team11.content.domain.game;

import be.kdg.team11.content.domain.game.exeptions.InvalidGameDataException;
import be.kdg.team11.content.domain.game.exeptions.InvalidGameStateException;
import be.kdg.team11.content.domain.Url;
import be.kdg.team11.content.domain.game.exeptions.InvalidGameUrlException;
import be.kdg.team11.sharedkernel.events.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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
    private GameRegistrationState registrationState;
    private final List<Rule> rules = new ArrayList<>();
    private final List<GameAchievement> achievements = new ArrayList<>();

    private final List<DomainEvent> eventStore = new ArrayList<>();


    private Game(
            GameId gameId,
            String name,
            String description,
            BigDecimal price,
            Url pictureUrl,
            Url gameUrl,
            String gameCreatorName,
            GameRegistrationState registrationState,
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
        this.registrationState = registrationState;
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
                GameRegistrationState.PENDING,
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

    public void accept() {
        if (this.registrationState != GameRegistrationState.PENDING) {
            throw new InvalidGameStateException(
                    "Cannot accept game: current state is " + this.registrationState + ", expected PENDING"
            );
        }
        GameAcceptedEvent event = new GameAcceptedEvent(this.gameId.gameId());
        this.registrationState = GameRegistrationState.ACCEPTED;
        this.eventStore.add(event);
    }

    public void reject() {
        if (this.registrationState != GameRegistrationState.PENDING) {
            throw new InvalidGameStateException(
                    "Cannot reject game: current state is " + this.registrationState + ", expected PENDING"
            );
        }
        GameRejectedEvent event = new GameRejectedEvent(this.gameId.gameId());
        this.registrationState = GameRegistrationState.REJECTED;
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

    public GameRegistrationState getRegistrationState() {
        return registrationState;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public List<GameAchievement> getAchievements() {
        return achievements;
    }

    public List<DomainEvent> getEventStore() {
        return eventStore;
    }
}
