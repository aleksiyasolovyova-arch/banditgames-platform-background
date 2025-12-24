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
    @Query("""
    SELECT DISTINCT f FROM FriendshipModel f
    WHERE ( f.recipientId = :playerId and ( f.state = "FRIENDS" or f.state = "REQUESTED" ) ) or
          ( f.requesterId = :playerId and f.state = "FRIENDS" )
    """)
    List<FriendshipModel> findFriendshipsByPlayerIdWhereStateFriendsOrRequested(@Param("playerId") UUID playerId);
}
