package be.kdg.team11.content.domain.game;

import be.kdg.team11.content.domain.game.exeptions.InvalidGameStateException;
import be.kdg.team11.content.domain.Url;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private final GameId gameId;
    private final String name;
    private final String description;
    private final BigDecimal price;
    private final Url pictureUrl;
    private final Url gameUrl;
    private final String gameCreatorName;
    private GameRegistrationState registrationState;
    private final List<Rule> rules = new ArrayList<>();
    private final List<GameAchievement> achievements = new ArrayList<>();

    public void acceptGame() {
        if (this.registrationState != GameRegistrationState.PENDING) {
            throw new InvalidGameStateException(
                    "Cannot accept game: current state is " + this.registrationState + ", expected PENDING"
            );
        }
        this.registrationState = GameRegistrationState.ACCEPTED;
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
