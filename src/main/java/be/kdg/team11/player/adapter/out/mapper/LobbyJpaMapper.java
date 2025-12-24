package be.kdg.team11.player.adapter.out.mapper;

import be.kdg.team11.player.adapter.out.jpa.embeddable.SlotJpaEmbeddable;
import be.kdg.team11.player.adapter.out.jpa.entity.LobbyJpaEntity;
import be.kdg.team11.player.domain.lobby.Lobby;
import be.kdg.team11.player.domain.lobby.LobbyId;
import be.kdg.team11.player.domain.lobby.Slot;
import be.kdg.team11.player.domain.player.PlayerId;
import be.kdg.team11.player.domain.projections.GameReference;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

@Component
public class LobbyJpaMapper {
    //TODO Make sure it is ok to inject this mapper into here
    /// as long as the mappers are avoid circular dependencies (both of them calling each other) it is not violating any principles
    /// its used often with mapstruct mappers
    private final GameReferenceJpaMapper gameReferenceJpaMapper;

    public LobbyJpaMapper(GameReferenceJpaMapper gameReferenceJpaMapper) {
        this.gameReferenceJpaMapper = gameReferenceJpaMapper;
    }

    public LobbyJpaEntity toJpaEntity(Lobby lobby) {
        LobbyJpaEntity entity = new LobbyJpaEntity();

        entity.setLobbyId(lobby.getLobbyId().lobbyId());
        entity.setGameReference(gameReferenceJpaMapper.toJpaEntity(lobby.getGameReference()));

        SlotJpaEmbeddable slotJpaEmbeddable1 = new SlotJpaEmbeddable();
        SlotJpaEmbeddable slotJpaEmbeddable2 = new SlotJpaEmbeddable();

        slotJpaEmbeddable1.setPlayerId(lobby.getSlotPair().getFirst().getPlayerId().playerId());
        slotJpaEmbeddable2.setPlayerId(lobby.getSlotPair().getSecond().getPlayerId().playerId());
        slotJpaEmbeddable1.setParticipationStatus(lobby.getSlotPair().getFirst().getParticipationStatus());
        slotJpaEmbeddable2.setParticipationStatus(lobby.getSlotPair().getSecond().getParticipationStatus());

        entity.setSlot1(slotJpaEmbeddable1);
        entity.setSlot2(slotJpaEmbeddable2);

        entity.setLobbyResult(lobby.getLobbyResult());
        entity.setStartTime(lobby.getStartTime());
        entity.setEndTime(lobby.getEndTime());

        return entity;
    }

    public Lobby toDomain(LobbyJpaEntity entity) {
        LobbyId lobbyId = new LobbyId(entity.getLobbyId());
        GameReference gameReference = gameReferenceJpaMapper.toDomain(entity.getGameReference());

        SlotJpaEmbeddable slotJpaEmbeddable1 = entity.getSlot1();
        SlotJpaEmbeddable slotJpaEmbeddable2 = entity.getSlot2();

        Slot slot1 = new Slot(new PlayerId(slotJpaEmbeddable1.getPlayerId()), slotJpaEmbeddable1.getParticipationStatus());
        Slot slot2 = new Slot(new PlayerId(slotJpaEmbeddable2.getPlayerId()), slotJpaEmbeddable2.getParticipationStatus());

        Pair<Slot, Slot> slotPair = Pair.of(slot1, slot2);


        return new Lobby(
                lobbyId,
                gameReference,
                slotPair,
                entity.getLobbyResult(),
                entity.getStartTime(),
                entity.getEndTime()
        );
    }
}