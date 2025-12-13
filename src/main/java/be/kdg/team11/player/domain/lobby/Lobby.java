package be.kdg.team11.player.domain.lobby;


import be.kdg.team11.player.domain.lobby.exceptions.PlayerNotInLobbyException;
import be.kdg.team11.player.domain.player.PlayerId;
import be.kdg.team11.player.domain.projections.GameReference;
import org.springframework.data.util.Pair;

import java.time.LocalDateTime;

public class Lobby {
    private final LobbyId lobbyId;
    private final GameReference gameReference;
    private final Pair<Slot, Slot> slotPair;
    private LobbyResult lobbyResult;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Lobby(LobbyId lobbyId, GameReference gameReference, Pair<Slot, Slot> slotPair, LobbyResult lobbyResult, LocalDateTime startTime, LocalDateTime endTime) {
        this.lobbyId = lobbyId;
        this.gameReference = gameReference;
        this.slotPair = slotPair;
        this.lobbyResult = lobbyResult;
        this.startTime = startTime;
        this.endTime = endTime;
    }


    /**
     * The following 3 methods are for creating Game lobbies in specific use cases
     */
    public static Lobby createForStrangers(GameReference gameReference, Pair<PlayerId, PlayerId> playerIdPair) {
        return new Lobby(
                LobbyId.create(),
                gameReference,
                Pair.of(Slot.pending(playerIdPair.getFirst()), Slot.pending(playerIdPair.getSecond())),
                LobbyResult.DID_NOT_START,
                LocalDateTime.now(),
                null
        );
    }

    public static Lobby createForFriends(GameReference gameReference, Pair<PlayerId, PlayerId> playerIdPair) {
        return new Lobby(
                LobbyId.create(),
                gameReference,
                Pair.of(Slot.accepted(playerIdPair.getFirst()), Slot.pending(playerIdPair.getSecond())),
                LobbyResult.DID_NOT_START,
                LocalDateTime.now(),
                null
        );
    }

    public static Lobby createForAI(GameReference gameReference, PlayerId playerId) {
        Pair<PlayerId, PlayerId> playerIdPair = Pair.of(playerId, PlayerId.ai());
        Lobby lobby = new Lobby(
                LobbyId.create(),
                gameReference,
                Pair.of(Slot.accepted(playerIdPair.getFirst()), Slot.accepted(playerIdPair.getSecond())),
                LobbyResult.DID_NOT_START,
                LocalDateTime.now(),
                null
        );
        lobby.start();
        return lobby;
    }

    /**
     * The following two methods are for changing the participation status of a player inside a slot
     */

    public void acceptBy(PlayerId playerId) {
        int playerNumber = whichPlayer(playerId);
        if (playerNumber == 1 && slotPair.getFirst().isPending()) {
            slotPair.getFirst().accept();
        } else if (playerNumber == 2 && slotPair.getSecond().isPending()) {
            slotPair.getSecond().accept();
        } else {
            throw new IllegalArgumentException("Player " + playerId + " is not in the lobby");
        }
        if (bothPlayersAccepted() && lobbyResult.equals(LobbyResult.DID_NOT_START)) {
            start();
        }
    }

    public void rejectBy(PlayerId playerId) {
        int playerNumber = whichPlayer(playerId);
        if (playerNumber == 1 && slotPair.getFirst().isPending()) {
            slotPair.getFirst().reject();
        } else if (playerNumber == 2 && slotPair.getSecond().isPending()) {
            slotPair.getSecond().reject();
        } else  {
            throw new IllegalArgumentException("Player " + playerId + " is not in the lobby");
        }
    }

    /**
     * The next two methods are for marking the start / end of the lobby
     */

    public void start() {
        if (lobbyResult.equals(LobbyResult.DID_NOT_START) && bothPlayersAccepted()) {
            this.lobbyResult = LobbyResult.DID_NOT_FINISH;
            this.startTime = LocalDateTime.now();
        } else {
            throw new IllegalArgumentException("Cannot start unless both players agree to start a the lobby!");
        }
    }

    public void end(PlayerId winnerId) {
        if (!lobbyResult.equals(LobbyResult.DID_NOT_FINISH)) {
            throw new IllegalArgumentException("To finish a game it must have started!");
        }

        int winnerNumber = whichPlayer(winnerId);

        if (winnerNumber == 1) {
            this.lobbyResult = LobbyResult.PLAYER_1_WINNER;
        } else if (winnerNumber == 2) {
            this.lobbyResult = LobbyResult.PLAYER_2_WINNER;
        } else {
            throw new PlayerNotInLobbyException("Invalid player id provided");
        }

        this.endTime = LocalDateTime.now();
    }

    public void end() {
        if (!lobbyResult.equals(LobbyResult.DID_NOT_FINISH)) {
            throw new IllegalArgumentException("To finish a game it must have started!");
        }
        this.lobbyResult = LobbyResult.DRAW;
        this.endTime = LocalDateTime.now();
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

    public LobbyId getLobbyId() {
        return lobbyId;
    }

    public GameReference getGameReference() {
        return gameReference;
    }

    public Pair<Slot,Slot> getSlotPair() {
        return slotPair;
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
}
