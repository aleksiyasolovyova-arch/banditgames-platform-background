package be.kdg.team11.player.domain.lobby;
import be.kdg.team11.player.domain.lobby.exceptions.InvalidLobbyException;
import be.kdg.team11.player.domain.lobby.exceptions.InvalidLobbyStateException;
import be.kdg.team11.player.domain.lobby.exceptions.PlayerNotInLobbyException;
import be.kdg.team11.player.domain.player.PlayerId;
import be.kdg.team11.player.domain.projections.GameReference;
import be.kdg.team11.sharedkernel.events.DomainEvent;
import be.kdg.team11.sharedkernel.events.lobby.*;
import org.springframework.data.util.Pair;

import java.time.LocalDateTime;
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
    private final Pair<Slot, Slot> slotPair;
    private LobbyResult lobbyResult;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private final List<DomainEvent> eventStore = new ArrayList<>();

    /**
     * Constructs a Lobby for recreation from persistent storage.
     * All parameters must be valid - no event publishing here.
     * Used by repositories to load lobbies from database.
     */
    public Lobby(LobbyId lobbyId, GameReference gameReference, Pair<Slot, Slot> slotPair, LobbyResult lobbyResult, LocalDateTime startTime, LocalDateTime endTime) {
        validateTimeConsistency(lobbyResult, startTime, endTime);

        this.lobbyId = lobbyId;
        this.gameReference = gameReference;
        this.slotPair = slotPair;
        this.lobbyResult = lobbyResult;
        this.startTime = startTime;
        this.endTime = endTime;
    }


    /**
     * The following 3 methods are for creating Game lobbies in specific use cases

     * Creates a new lobby for random strangers.

     * Initial State:
     * - Lobby: DID_NOT_START
     * - Player 1 Slot: PENDING (must accept/reject)
     * - Player 2 Slot: PENDING (must accept/reject)
     * - Both players must accept before game starts
     */
    public static Lobby createForStrangers(GameReference gameReference, Pair<PlayerId, PlayerId> playerIdPair) {

        Lobby lobby = new Lobby(LobbyId.create(),
                gameReference,
                Pair.of(Slot.pending(playerIdPair.getFirst()), Slot.pending(playerIdPair.getSecond())),
                LobbyResult.DID_NOT_START,
                LocalDateTime.now(),
                null);

        LobbyCreatedEvent event = new LobbyCreatedEvent(
                lobby.lobbyId.lobbyId(),
                gameReference.gameId(),
                playerIdPair.getFirst().playerId(),
                playerIdPair.getSecond().playerId(),
                ParticipationStatus.PENDING.name(),
                ParticipationStatus.PENDING.name(),
                "STRANGERS",
                "DID_NOT_START"
        );
        lobby.eventStore.add(event);

        return lobby;
    }


    /**
     * Creates a new lobby for friends playing together.

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
                Pair.of(Slot.accepted(playerIdPair.getFirst()), Slot.pending(playerIdPair.getSecond())),
                LobbyResult.DID_NOT_START,
                LocalDateTime.now(),
                null);

        LobbyCreatedEvent event = new LobbyCreatedEvent(
                lobby.lobbyId.lobbyId(),
                gameReference.gameId(),
                playerIdPair.getFirst().playerId(),
                playerIdPair.getSecond().playerId(),
                ParticipationStatus.ACCEPTED.name(),
                ParticipationStatus.PENDING.name(),
                "FRIENDS",
                "DID_NOT_START"
        );
        lobby.eventStore.add(event);

        return lobby;
    }

    /**
     * Creates a new lobby for a player vs AI opponent.
     *
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
                Pair.of(Slot.accepted(playerIdPair.getFirst()), Slot.accepted(playerIdPair.getSecond())),
                LobbyResult.DID_NOT_START,
                LocalDateTime.now(),
                null
        );

        LobbyCreatedEvent createdEvent = new LobbyCreatedEvent(
                lobby.lobbyId.lobbyId(),
                gameReference.gameId(),
                lobby.getSlotPair().getFirst().getPlayerId().playerId(),
                lobby.getSlotPair().getSecond().getPlayerId().playerId(),
                ParticipationStatus.ACCEPTED.name(),
                ParticipationStatus.ACCEPTED.name(),
                "AI",
                "DID_NOT_START"
        );
        lobby.eventStore.add(createdEvent);



        lobby.start();
        return lobby;
    }

    /**
     * The following two methods are for changing the participation status of a player inside a slot
     * Accepts a pending participation slot for a player.
     * Allows a player to confirm they want to participate.
     * If both players then accept, the lobby automatically starts the game.
     */

    public void acceptBy(PlayerId playerId) {
        int playerNumber = whichPlayer(playerId);  // Throws PlayerNotInLobbyException

        Slot playerSlot = playerNumber == 1
                ? slotPair.getFirst()
                : slotPair.getSecond();

        if (!playerSlot.isPending()) {
            throw InvalidLobbyStateException.invalidStateTransition(
                    playerSlot.getParticipationStatus().name(),
                    "PENDING",
                    "accept slot"
            );
        }
        playerSlot.accept();

        // Publish event
        LobbyAcceptedEvent event = new LobbyAcceptedEvent(
                lobbyId.lobbyId(),
                playerId.playerId(),
                "ACCEPTED"
        );
        eventStore.add(event);

        if (bothPlayersAccepted() && lobbyResult.equals(LobbyResult.DID_NOT_START)) {
            start();
        }
    }

    /**
     * Rejects a pending participation slot for a player.
     * Allows a player to decline participation.
     * Typically ends the lobby session.
     */
    public void rejectBy(PlayerId playerId) {
        int playerNumber = whichPlayer(playerId);
        Slot playerSlot = playerNumber == 1
                ? slotPair.getFirst()
                : slotPair.getSecond();

        if (!playerSlot.isPending()) {
            throw InvalidLobbyStateException.invalidStateTransition(
                    playerSlot.getParticipationStatus().name(),
                    "PENDING",
                    "reject slot"
            );
        }
        playerSlot.reject();

        // Publish event
        LobbyRejectedEvent event = new LobbyRejectedEvent(
                lobbyId.lobbyId(),
                playerId.playerId(),
                "REJECTED"
        );
        eventStore.add(event);
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

        if (!bothPlayersAccepted()) {
            throw new IllegalArgumentException(
                    "Cannot start lobby: both players must accept before starting"
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

        String status = winnerNumber == 1 ? "PLAYER_1_WINNER" : "PLAYER_2_WINNER";
        this.lobbyResult = winnerNumber == 1
                ? LobbyResult.PLAYER_1_WINNER
                : LobbyResult.PLAYER_2_WINNER;
        this.endTime = LocalDateTime.now();

        LobbyEndedWithWinnerEvent event = new LobbyEndedWithWinnerEvent(
                lobbyId.lobbyId(),
                winnerId.playerId(),
                status
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
                "DRAW"
        );
        eventStore.add(event);
    }

    public int whichPlayer (PlayerId playerId) {
        if (slotPair.getFirst().getPlayerId().equals(playerId)) {
            return 1;
        } else if (slotPair.getSecond().getPlayerId().equals(playerId)) {
            return 2;
        } else {
            throw new PlayerNotInLobbyException("Player " + playerId + " is not participant in this lobby!");
        }
    }

    public boolean bothPlayersAccepted() {
        return slotPair.getFirst().isAccepted() && slotPair.getSecond().isAccepted();
    }

    public boolean isAgainstAi() {
        return slotPair.getSecond().getPlayerId().isAI();
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

    public LobbyId getLobbyId() {
        return lobbyId;
    }

    public GameReference getGameReference() {
        return gameReference;
    }

    public Pair<Slot, Slot> getSlotPair() {
        return Pair.of(slotPair.getFirst(), slotPair.getSecond());
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
