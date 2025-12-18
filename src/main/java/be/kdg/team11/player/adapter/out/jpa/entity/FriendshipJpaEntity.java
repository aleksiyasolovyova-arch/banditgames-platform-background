package be.kdg.team11.player.adapter.out.jpa.entity;

import be.kdg.team11.player.domain.friendship.FriendshipState;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "friendship", schema = "player_schema")
public class FriendshipJpaEntity {
    @Id
    private UUID friendshipId;

    @Column(nullable = false)
    private UUID requesterId;

    @Column(nullable = false)
    private UUID recipientId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FriendshipState friendshipState;

    public FriendshipJpaEntity() {
    }

    public UUID getFriendshipId() {
        return friendshipId;
    }

    public void setFriendshipId(UUID friendhipId) {
        this.friendshipId = friendhipId;
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
