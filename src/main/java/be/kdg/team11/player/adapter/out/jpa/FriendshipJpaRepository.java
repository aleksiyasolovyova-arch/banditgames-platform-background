package be.kdg.team11.player.adapter.out.jpa;

import be.kdg.team11.player.adapter.out.jpa.entity.FriendshipJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FriendshipJpaRepository extends JpaRepository<FriendshipJpaEntity, UUID> {
    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END " +
            "FROM FriendshipJpaEntity f " +
            "WHERE (f.requesterId = :playerId1 AND f.recipientId = :playerId2) " +
            "OR (f.requesterId = :playerId2 AND f.recipientId = :playerId1)")
    boolean existsBetweenPlayers(@Param("playerId1") UUID playerId1,
                                 @Param("playerId2") UUID playerId2);
}
