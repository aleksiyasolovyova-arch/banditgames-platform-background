package be.kdg.team11.player.adapter.out.jpa.entity;

import be.kdg.team11.player.adapter.out.jpa.embeddable.SlotJpaEmbeddable;
import be.kdg.team11.player.domain.lobby.LobbyResult;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "lobby" ,schema = "player_schema")
public class LobbyJpaEntity {
    @Id
    private UUID lobbyId;

    @ManyToOne(optional = false)
    private GameReferenceJpaEntity gameReference;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "playerId", column = @Column(name = "slot1_player_id")),
            @AttributeOverride(name = "participationStatus", column = @Column(name = "slot1_participation_status"))
    })
    private SlotJpaEmbeddable slot1;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "playerId", column = @Column(name = "slot2_player_id")),
            @AttributeOverride(name = "participationStatus", column = @Column(name = "slot2_participation_status"))
    })
    private SlotJpaEmbeddable slot2;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LobbyResult lobbyResult;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column()
    private LocalDateTime endTime;

    public LobbyJpaEntity() {
    }

    public UUID getLobbyId() {
        return lobbyId;
    }

    public void setLobbyId(UUID lobbyId) {
        this.lobbyId = lobbyId;
    }

    public GameReferenceJpaEntity getGameReference() {
        return gameReference;
    }

    public void setGameReference(GameReferenceJpaEntity gameReference) {
        this.gameReference = gameReference;
    }

    public SlotJpaEmbeddable getSlot1() {
        return slot1;
    }

    public void setSlot1(SlotJpaEmbeddable slot1) {
        this.slot1 = slot1;
    }

    public SlotJpaEmbeddable getSlot2() {
        return slot2;
    }

    public void setSlot2(SlotJpaEmbeddable slot2) {
        this.slot2 = slot2;
    }

    public LobbyResult getLobbyResult() {
        return lobbyResult;
    }

    public void setLobbyResult(LobbyResult lobbyResult) {
        this.lobbyResult = lobbyResult;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}