package be.kdg.team11.player.adapter.out.jpa.entity;

import be.kdg.team11.player.domain.friendship.FriendshipState;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(schema = "player_schema")
public class FriendshipJpaEntity {
    @Id
    private UUID friendShipId;

    @Column(nullable = false)
    private UUID requesterId;

    @Column(nullable = false)
    private UUID recipientId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FriendshipState friendshipState;

    public FriendshipJpaEntity() {}

    public UUID getFriendShipId() {
        return friendShipId;
    }

    public void setFriendShipId(UUID friendShipId) {
        this.friendShipId = friendShipId;
    }

    public UUID getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(UUID requesterId) {
        this.requesterId = requesterId;
    }

    public UUID getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(UUID recipientId) {
        this.recipientId = recipientId;
    }

    public FriendshipState getFriendshipState() {
        return friendshipState;
    }

    public void setFriendshipState(FriendshipState friendshipState) {
        this.friendshipState = friendshipState;
    }
}
