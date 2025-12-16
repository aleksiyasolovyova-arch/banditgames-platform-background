package be.kdg.team11.player.domain.lobby;

import be.kdg.team11.player.domain.player.PlayerId;

public class Slot {
    private final PlayerId playerId;
    private ParticipationStatus participationStatus;

    public Slot(PlayerId playerId, ParticipationStatus participationStatus) {
        this.playerId = playerId;
        this.participationStatus = participationStatus;
    }

    public static Slot accepted(PlayerId playerId) {
        return new Slot(playerId, ParticipationStatus.ACCEPTED);
    }

    public static Slot pending(PlayerId playerId) {
        return new Slot(playerId, ParticipationStatus.PENDING);
    }

    public void accept() {
        if (participationStatus != ParticipationStatus.PENDING) {
            throw new IllegalStateException("Slot cannot be accepted");
        }
        participationStatus = ParticipationStatus.ACCEPTED;
    }

    public void reject() {
        if (participationStatus != ParticipationStatus.PENDING) {
            throw new IllegalStateException("Slot cannot be refused");
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
