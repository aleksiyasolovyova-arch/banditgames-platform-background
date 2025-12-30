package be.kdg.team11.player.domain.lobby;

import be.kdg.team11.player.domain.lobby.exceptions.InvalidLobbyException;
import be.kdg.team11.player.domain.lobby.exceptions.InvalidLobbyStateException;
import be.kdg.team11.player.domain.lobby.exceptions.PlayerNotInLobbyException;
import be.kdg.team11.player.domain.player.PlayerId;
import be.kdg.team11.player.domain.projections.GameReference;
import be.kdg.team11.sharedkernel.events.DomainEvent;
import be.kdg.team11.sharedkernel.events.lobby.LobbyCreatedEvent;
import be.kdg.team11.sharedkernel.events.lobby.LobbyEndedWithDrawEvent;
import be.kdg.team11.sharedkernel.events.lobby.LobbyEndedWithWinnerEvent;
import be.kdg.team11.sharedkernel.events.lobby.LobbyStartedEvent;
import org.springframework.data.util.Pair;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
/**
 * Aggregate Root for the Lobby subdomain.
 * Represents a game lobby session between two players.
 * Manages the complete lifecycle: creation, player acceptance/rejection, game start/end.
 */
public class Lobby {
    private final LobbyId lobbyId;
    private final GameReference gameReference;
    private final Pair<PlayerId, PlayerId> playerIdPair;
    private LobbyResult lobbyResult;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private final List<DomainEvent> eventStore = new ArrayList<>();

    /**
     * Constructs a Lobby for recreation from persistent storage.
     * All parameters must be valid - no event publishing here.
     * Used by repositories to load lobbies from database.
     */
    public Lobby(LobbyId lobbyId, GameReference gameReference, Pair<PlayerId, PlayerId> playerIdPair, LobbyResult lobbyResult, LocalDateTime startTime, LocalDateTime endTime) {
        validateTimeConsistency(lobbyResult, startTime, endTime);

        this.lobbyId = lobbyId;
        this.gameReference = gameReference;
        this.playerIdPair = playerIdPair;
        this.lobbyResult = lobbyResult;
        this.startTime = startTime;
        this.endTime = endTime;
    }


    /**
     * The following 3 methods are for creating Game lobbies in specific use cases
     * <p>
     * Creates a new lobby for random strangers.
     * <p>
     * Initial State:
     * - Lobby: DID_NOT_START
     * - Player 1 Slot: PENDING (must accept/reject)
     * - Player 2 Slot: PENDING (must accept/reject)
     * - Both players must accept before game starts
     */
    public static Lobby createForStrangers(GameReference gameReference, Pair<PlayerId, PlayerId> playerIdPair) {

        Lobby lobby = new Lobby(LobbyId.create(),
                gameReference,
                playerIdPair,
                LobbyResult.DID_NOT_START,
                LocalDateTime.now(),
                null);

        LobbyCreatedEvent event = new LobbyCreatedEvent(
                lobby.lobbyId.lobbyId(),
                gameReference.gameId(),
                playerIdPair.getFirst().playerId(),
                playerIdPair.getSecond().playerId(),
                "STRANGERS",
                "DID_NOT_START"
        );
        lobby.eventStore.add(event);

        return lobby;
    }


    /**
     * Creates a new lobby for friends playing together.
     * <p>
     * Initial State:
     * - Lobby: DID_NOT_START
     * - Player 1 (requester) Slot: ACCEPTED (already confirmed)
     * - Player 2 (recipient) Slot: PENDING (must accept/reject)
     * - Waits for player2 decision before starting
     */
    public static Lobby createForFriends(GameReference gameReference, Pair<PlayerId, PlayerId> playerIdPair) {

        Lobby lobby = new Lobby(
                LobbyId.create(),
                gameReference,
                playerIdPair,
                LobbyResult.DID_NOT_START,
                LocalDateTime.now(),
                null);

        LobbyCreatedEvent event = new LobbyCreatedEvent(
                lobby.lobbyId.lobbyId(),
                gameReference.gameId(),
                playerIdPair.getFirst().playerId(),
                playerIdPair.getSecond().playerId(),
                "FRIENDS",
                "DID_NOT_START"
        );
        lobby.eventStore.add(event);

        return lobby;
    }

    /**
     * Creates a new lobby for a player vs AI opponent.
     * <p>
     * Initial State:
     * - Lobby: STARTS AUTOMATICALLY (no player confirmation needed)
     * - Player 1 Slot: ACCEPTED
     * - Player 2 (AI) Slot: ACCEPTED
     * - Game starts immediately
     */
    public static Lobby createForAI(GameReference gameReference, PlayerId playerId) {
        if (playerId == null) {
            throw new InvalidLobbyException("Player ID cannot be null");
        }

        Pair<PlayerId, PlayerId> playerIdPair = Pair.of(playerId, PlayerId.ai());
        Lobby lobby = new Lobby(
                LobbyId.create(),
                gameReference,
                playerIdPair,
                LobbyResult.DID_NOT_START,
                LocalDateTime.now(),
                null
        );

        LobbyCreatedEvent createdEvent = new LobbyCreatedEvent(
                lobby.lobbyId.lobbyId(),
                gameReference.gameId(),
                lobby.getPlayerIdPair().getFirst().playerId(),
                lobby.getPlayerIdPair().getSecond().playerId(),
                "AI",
                "DID_NOT_START"
        );
        lobby.eventStore.add(createdEvent);

        return lobby;
    }

    /**
     * The next two methods are for marking the start / end of the lobby
     * Starts the game when both players have accepted.
     * Transitions lobby from waiting state to in-progress.
     * Can be called manually or automatically when both players accept.
     */

    public void start() {
        if (!lobbyResult.equals(LobbyResult.DID_NOT_START)) {
            throw InvalidLobbyStateException.invalidStateTransition(
                    lobbyResult.name(),
                    "DID_NOT_START",
                    "start lobby"
            );
        }
        this.lobbyResult = LobbyResult.DID_NOT_FINISH;
        this.startTime = LocalDateTime.now();

        LobbyStartedEvent event = new LobbyStartedEvent(
                lobbyId.lobbyId(),
                "DID_NOT_FINISH"
        );
        eventStore.add(event);
    }

    /**
     * Finishes the game with a winner.
     * Records that one player won the game.
     */
    public void end(PlayerId winnerId) {
        int winnerNumber = whichPlayer(winnerId);
        if (!lobbyResult.equals(LobbyResult.DID_NOT_FINISH)) {
            throw InvalidLobbyStateException.invalidStateTransition(
                    lobbyResult.name(),
                    "DID_NOT_FINISH",
                    "finish lobby"
            );
        }

        this.lobbyResult = winnerNumber == 1
                ? LobbyResult.PLAYER_1_WINNER
                : LobbyResult.PLAYER_2_WINNER;
        this.endTime = LocalDateTime.now();

        LobbyEndedWithWinnerEvent event = new LobbyEndedWithWinnerEvent(
                lobbyId.lobbyId(),
                winnerId.playerId(),
                playerIdPair.getFirst().playerId(),
                playerIdPair.getSecond().playerId(),
                ChronoUnit.SECONDS.between(startTime, endTime)
        );
        eventStore.add(event);
    }

    /**
     * Finishes the game in a draw.
     * Records that neither player won - game ended in tie.
     */
    public void end() {
        if (!lobbyResult.equals(LobbyResult.DID_NOT_FINISH)) {
            throw InvalidLobbyStateException.invalidStateTransition(
                    lobbyResult.name(),
                    "DID_NOT_FINISH",
                    "finish lobby as draw"
            );
        }

        this.lobbyResult = LobbyResult.DRAW;
        this.endTime = LocalDateTime.now();

        LobbyEndedWithDrawEvent event = new LobbyEndedWithDrawEvent(
                lobbyId.lobbyId(),
                playerIdPair.getFirst().playerId(),
                playerIdPair.getSecond().playerId(),
                ChronoUnit.SECONDS.between(startTime, endTime)
        );
        eventStore.add(event);
    }

    public int whichPlayer(PlayerId playerId) {
        if (playerIdPair.getFirst().equals(playerId)) {
            return 1;
        } else if (playerIdPair.getSecond().equals(playerId)) {
            return 2;
        } else {
            throw new PlayerNotInLobbyException("Player " + playerId + " is not participant in this lobby!");
        }
    }

    public boolean isAgainstAi() {
        return playerIdPair.getSecond().isAI();
    }


    private static void validateTimeConsistency(LobbyResult result, LocalDateTime startTime, LocalDateTime endTime) {
        if (!result.equals(LobbyResult.DID_NOT_START) && startTime == null) {
            throw new InvalidLobbyStateException(
                    "Lobby with result " + result + " must have start time"
            );
        }

        if (result.equals(LobbyResult.PLAYER_1_WINNER) ||
                result.equals(LobbyResult.PLAYER_2_WINNER) ||
                result.equals(LobbyResult.DRAW)) {
            if (endTime == null) {
                throw new InvalidLobbyStateException(
                        "Finished lobby must have end time"
                );
            }
        }
    }

    public String getLink() {
        return gameReference.gameUrl() + lobbyId.lobbyId();
    }

    public LobbyId getLobbyId() {
        return lobbyId;
    }

    public GameReference getGameReference() {
        return gameReference;
    }

    public Pair<PlayerId, PlayerId> getPlayerIdPair() {
        return Pair.of(playerIdPair.getFirst(), playerIdPair.getSecond());
    }

    public LobbyResult getLobbyResult() {
        return lobbyResult;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public List<DomainEvent> getEventStore() {
        return eventStore;
    }
}
