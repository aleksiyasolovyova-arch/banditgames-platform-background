package team11.platform_backend.game.domain.game;

import team11.platform_backend.game.domain.game.exeptions.InvalidGameStateException;
import team11.platform_backend.sharedkernel.valueobjects.Url;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

//Aggregate
public class Game {
    private final GameId gameId;
    private final String gameName;
    private final String gameDescription;
    private final BigDecimal gamePrice; 
    private final List<Url> pictureUrls;
    private final String gameCreatorName;
    private final Url gameUrl;
    private GameState gameState;
    private final List<Rule> rules= new ArrayList<>();
    private Url aiPlayerUrl;

    // for loading (get methods)
    public Game(GameId gameId, String gameName, String gameDescription, BigDecimal gamePrice, List<Url> pictureUrls, String gameCreatorName, Url gameUrl, GameState gameState, List<Rule> rules,Url aiPlayerUrl) {
        this.gameId = gameId;
        this.gameName = gameName;
        this.gameDescription = gameDescription;
        this.gamePrice = gamePrice;
        this.pictureUrls = pictureUrls;
        this.gameCreatorName = gameCreatorName;
        this.gameUrl = gameUrl;
        this.gameState = gameState;
        this.rules.addAll(rules);
	    this.aiPlayerUrl = aiPlayerUrl;
    }

    // for creating (post methods)
    public Game(String gameName, String gameDescription, BigDecimal gamePrice, List<Url> pictureUrls, String gameCreatorName, Url gameUrl, List<Rule> rules) {
        this.gameId = GameId.createGameId();
        this.gameName = gameName;
        this.gameDescription = gameDescription;
        this.gamePrice = gamePrice;
        this.pictureUrls = pictureUrls;
        this.gameCreatorName = gameCreatorName;
        this.gameUrl= gameUrl;
        this.gameState = GameState.PENDING;
        this.rules.addAll(rules);
	    this.aiPlayerUrl = null;
    }

    //TODO remove if not necesarry
    public void changeAiPlayerUrl(Url aiPlayerUrl) {
	this.aiPlayerUrl = aiPlayerUrl;
    }

    public void acceptGame() {
        if (this.gameState != GameState.PENDING) {
            throw new InvalidGameStateException(
                    "Cannot accept game: current state is " + this.gameState + ", expected PENDING"
            );
        }
        this.gameState = GameState.ACCEPTED;
    }

    public void rejectGame() {
        if (this.gameState != GameState.PENDING) {
            throw new InvalidGameStateException(
                    "Cannot reject game: current state is " + this.gameState + ", expected PENDING"
            );
        }
        this.gameState = GameState.REJECTED;
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

    public List<Url> getPictureUrls() {
        return pictureUrls;
    }

    public String getGameCreatorName() {
        return gameCreatorName;
    }

    public Url getGameUrl() {
        return gameUrl;
    }

    public GameState getGameState() {
        return gameState;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public Url getAiPlayerUrl() {
        return aiPlayerUrl;
    }
}
