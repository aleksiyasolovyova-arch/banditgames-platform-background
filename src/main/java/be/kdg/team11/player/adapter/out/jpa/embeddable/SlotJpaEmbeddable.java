package be.kdg.team11.player.adapter.out.jpa.embeddable;

import be.kdg.team11.player.domain.lobby.ParticipationStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public class SlotJpaEmbeddable {
    @Column
    private UUID playerId;

    @Column
    private ParticipationStatus participationStatus;

    public SlotJpaEmbeddable() {}

    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }

    public ParticipationStatus getParticipationStatus() {
        return participationStatus;
    }

    public void setParticipationStatus(ParticipationStatus participationStatus) {
        this.participationStatus = participationStatus;
    }
}
