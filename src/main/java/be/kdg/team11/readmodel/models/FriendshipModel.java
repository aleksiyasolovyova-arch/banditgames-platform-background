package be.kdg.team11.readmodel.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "friendship", schema = "read_model_schema")
public class FriendshipModel {
    @Id
    private UUID friendshipId;

    @Column
    private UUID requesterId;

    @Column
    private String requesterUsername;

    @Column
    private String requesterPictureUrl;

    @Column
    private UUID recipientId;

    @Column
    private String recipientUsername;

    @Column
    private String recipientPictureUrl;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime acceptedAt;

    public FriendshipModel() {}

    public UUID getFriendshipId() {
        return friendshipId;
    }

    public void setFriendshipId(UUID friendshipId) {
        this.friendshipId = friendshipId;
    }

    public UUID getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(UUID requesterId) {
        this.requesterId = requesterId;
    }

    public String getRequesterUsername() {
        return requesterUsername;
    }

    public void setRequesterUsername(String requesterUsername) {
        this.requesterUsername = requesterUsername;
    }

    public String getRequesterPictureUrl() {
        return requesterPictureUrl;
    }

    public void setRequesterPictureUrl(String requesterPictureUrl) {
        this.requesterPictureUrl = requesterPictureUrl;
    }

    public UUID getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(UUID recipientId) {
        this.recipientId = recipientId;
    }

    public String getRecipientUsername() {
        return recipientUsername;
    }

    public void setRecipientUsername(String recipientUsername) {
        this.recipientUsername = recipientUsername;
    }

    public String getRecipientPictureUrl() {
        return recipientPictureUrl;
    }

    public void setRecipientPictureUrl(String recipientPictureUrl) {
        this.recipientPictureUrl = recipientPictureUrl;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getAcceptedAt() {
        return acceptedAt;
    }

    public void setAcceptedAt(LocalDateTime acceptedAt) {
        this.acceptedAt = acceptedAt;
    }
}
