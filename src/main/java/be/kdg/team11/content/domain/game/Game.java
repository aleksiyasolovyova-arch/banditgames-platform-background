package be.kdg.team11.content.domain.game;

import be.kdg.team11.content.domain.game.exeptions.InvalidGameDataException;
import be.kdg.team11.content.domain.game.exeptions.InvalidGameStateException;
import be.kdg.team11.content.domain.Url;
import be.kdg.team11.content.domain.game.exeptions.InvalidGameUrlException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
/**
 * Aggregate Root for the Game subdomain.
 * Represents a publishable game in the platform.
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

    public void accept() {
        if (this.registrationState != GameRegistrationState.PENDING) {
            throw new InvalidGameStateException(
                    "Cannot accept game: current state is " + this.registrationState + ", expected PENDING"
            );
        }
        this.registrationState = GameRegistrationState.ACCEPTED;
    }

    public void reject() {
        if (this.registrationState != GameRegistrationState.PENDING) {
            throw new InvalidGameStateException(
                    "Cannot reject game: current state is " + this.registrationState + ", expected PENDING"
            );
        }
        this.registrationState = GameRegistrationState.REJECTED;
    }

    public Game(GameId gameId, String name, String description, BigDecimal price, Url pictureUrl, Url gameUrl, String gameCreatorName, GameRegistrationState registrationState, List<Rule> rules, List<GameAchievement> achievements) {
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
        return new Game(
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
    }

/**
 * Updates game URLs for maintenance purposes.
 * Allows updating icon/screenshot and playable game links without recreating the aggregate.
 * */
    public void modifyUrls(Url pictureUrl, Url gameUrl){
        validateGamePictureUrl(pictureUrl);
        validateGameUrl(gameUrl);
        this.pictureUrl = pictureUrl;
        this.gameUrl = gameUrl;
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
}
