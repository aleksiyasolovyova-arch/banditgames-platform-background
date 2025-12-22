package be.kdg.team11.readmodel.repository;

import be.kdg.team11.readmodel.models.FriendshipModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FriendshipModelRepository extends JpaRepository<FriendshipModel, UUID> {
    @Query("SELECT f FROM FriendshipModel f WHERE " +
            "(f.requesterId = :playerId OR f.recipientId = :playerId) AND " +
            "f.state = :state")
    List<FriendshipModel> findFriendshipsByPlayerAndState(@Param("playerId") UUID playerId,
                                                          @Param("state") String state);

    List<FriendshipModel> findByRequesterIdAndState(UUID requesterId, String state);
    List<FriendshipModel> findByRecipientIdAndState(UUID recipientId, String state);
}
