package be.kdg.team11.readmodel.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "friendship", schema = "read_model_schema")
public class FriendshipModel {
    @Id
    @Column(name = "friendship_id")
    private UUID friendshipId;

    @Column(name = "requester_id", nullable = false)
    private UUID requesterId;

    @Column(name = "recipient_id", nullable = false)
    private UUID recipientId;

    //TODO can be added if use case is changed but im not sure if it is ddd
    // Denormalized player info
  //  @Column(name = "requester_username")
  //  private String requesterUsername;
//
  //  @Column(name = "recipient_username")
  //  private String recipientUsername;
//
  //  @Column(name = "requester_picture_url")
  //  private String requesterPictureUrl;
//
  //  @Column(name = "recipient_picture_url")
  //  private String recipientPictureUrl;

    @Column(name = "state", nullable = false)
    private String state;

    // Timestamps
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "accepted_at")
    private LocalDateTime acceptedAt;

    // Constructors
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

    public UUID getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(UUID recipientId) {
        this.recipientId = recipientId;
    }

  //  public String getRequesterUsername() {
  //      return requesterUsername;
  //  }
//
  //  public void setRequesterUsername(String requesterUsername) {
  //      this.requesterUsername = requesterUsername;
  //  }
//
  //  public String getRecipientUsername() {
  //      return recipientUsername;
  //  }
//
  //  public void setRecipientUsername(String recipientUsername) {
  //      this.recipientUsername = recipientUsername;
  //  }
//
  //  public String getRequesterPictureUrl() {
  //      return requesterPictureUrl;
  //  }

  // public void setRequesterPictureUrl(String requesterPictureUrl) {
  //     this.requesterPictureUrl = requesterPictureUrl;
  // }

  // public String getRecipientPictureUrl() {
  //     return recipientPictureUrl;
  // }

  // public void setRecipientPictureUrl(String recipientPictureUrl) {
  //     this.recipientPictureUrl = recipientPictureUrl;
  // }

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
