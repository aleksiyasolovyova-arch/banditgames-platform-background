package team11.platform_backend.game.domain.game;

import team11.platform_backend.game.domain.game.exeptions.InvalidGameStateException;
import team11.platform_backend.game.domain.Url;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

//Aggregate
public class Game {
    private final GameId gameId;
    private final String gameName;
    private final String gameDescription;
    private final BigDecimal gamePrice; 
    private final Url pictureUrl;
    private final Url gameUrl;
    private final String gameCreatorName;
    private GameRegistrationState gameRegistrationState;
    private final List<Rule> rules = new ArrayList<>();
    private final List<GameAchievement> achievements = new ArrayList<>();

    public void acceptGame() {
        if (this.gameRegistrationState != GameRegistrationState.PENDING) {
            throw new InvalidGameStateException(
                    "Cannot accept game: current state is " + this.gameRegistrationState + ", expected PENDING"
            );
        }
        this.gameRegistrationState = GameRegistrationState.ACCEPTED;
    }

    public Game(GameId gameId, String gameName, String gameDescription, BigDecimal gamePrice, Url pictureUrl, String gameCreatorName, Url gameUrl, GameRegistrationState gameRegistrationState) {
        this.gameId = gameId;
        this.gameName = gameName;
        this.gameDescription = gameDescription;
        this.gamePrice = gamePrice;
        this.pictureUrl = pictureUrl;
        this.gameCreatorName = gameCreatorName;
        this.gameUrl = gameUrl;
        this.gameRegistrationState = gameRegistrationState;
    }


    public Game(String gameName, String gameDescription, BigDecimal gamePrice, Url pictureUrl, String gameCreatorName, Url gameUrl) {
        this.gameId = GameId.create();
        this.gameName = gameName;
        this.gameDescription = gameDescription;
        this.gamePrice = gamePrice;
        this.pictureUrl = pictureUrl;
        this.gameCreatorName = gameCreatorName;
        this.gameUrl = gameUrl;
        this.gameRegistrationState = GameRegistrationState.PENDING;
    }

    public GameId getGameId() {
        return gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public String getGameDescription() {
        return gameDescription;
    }

    public BigDecimal getGamePrice() {
        return gamePrice;
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

    public GameRegistrationState getGameRegistrationState() {
        return gameRegistrationState;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public List<GameAchievement> getAchievements() {
        return achievements;
    }
}
