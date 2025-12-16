package be.kdg.team11.player.domain.lobby;

import be.kdg.team11.player.domain.lobby.exceptions.InvalidLobbyException;
import be.kdg.team11.player.domain.lobby.exceptions.InvalidLobbyStateException;
import be.kdg.team11.player.domain.player.PlayerId;

/**
 * Value Object for a player slot in a lobby.
 * Manages player identity and participation status (PENDING → ACCEPTED | REJECTED).
 */
 public class Slot {
    private final PlayerId playerId;
    private ParticipationStatus participationStatus;

    public Slot(PlayerId playerId, ParticipationStatus participationStatus) {
        if (playerId == null) {
            throw new InvalidLobbyException("Player ID cannot be null");
        }
        if (participationStatus == null) {
            throw new InvalidLobbyException("Participation status cannot be null");
        }
        this.playerId = playerId;
        this.participationStatus = participationStatus;
    }

    public static Slot accepted (PlayerId playerId) {
        return new Slot (playerId,ParticipationStatus.ACCEPTED);
    }

    public static Slot pending (PlayerId playerId) {
        return new Slot (playerId,ParticipationStatus.PENDING);
    }

    public void accept() {
        if (participationStatus != ParticipationStatus.PENDING) {
            throw InvalidLobbyStateException.invalidStateTransition(
                    participationStatus.name(),
                    "PENDING",
                    "accept slot"
            );
        }
        participationStatus = ParticipationStatus.ACCEPTED;
    }

    public void reject() {
        if (participationStatus != ParticipationStatus.PENDING) {
            throw InvalidLobbyStateException.invalidStateTransition(
                    participationStatus.name(),
                    "PENDING",
                    "reject slot"
            );
        }
        participationStatus = ParticipationStatus.REJECTED;
    }

    public boolean isAccepted() {
        return participationStatus == ParticipationStatus.ACCEPTED;
    }

    public boolean isPending() {
        return participationStatus == ParticipationStatus.PENDING;
    }

    public boolean isRejected() {
        return participationStatus == ParticipationStatus.REJECTED;
    }

    public PlayerId getPlayerId() {
        return playerId;
    }

    public ParticipationStatus getParticipationStatus() {
        return participationStatus;
    }
}
