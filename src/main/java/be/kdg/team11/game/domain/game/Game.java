package be.kdg.team11.game.domain.game;

import be.kdg.team11.game.domain.game.exeptions.InvalidGameStateException;
import be.kdg.team11.game.domain.Url;
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

    public Game(GameId gameId, String name, String description, BigDecimal price, Url pictureUrl, String gameCreatorName, Url gameUrl, GameRegistrationState registrationState) {
        this.gameId = gameId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.pictureUrl = pictureUrl;
        this.gameCreatorName = gameCreatorName;
        this.gameUrl = gameUrl;
        this.registrationState = registrationState;
    }


    public Game(String name, String description, BigDecimal price, Url pictureUrl, String gameCreatorName, Url gameUrl) {
        this.gameId = GameId.create();
        this.name = name;
        this.description = description;
        this.price = price;
        this.pictureUrl = pictureUrl;
        this.gameCreatorName = gameCreatorName;
        this.gameUrl = gameUrl;
        this.registrationState = GameRegistrationState.PENDING;
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
