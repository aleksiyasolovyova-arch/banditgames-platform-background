package be.kdg.team11.player.adapter.out.jpa;

import be.kdg.team11.player.domain.friendship.Friendship;
import be.kdg.team11.player.domain.friendship.FriendshipId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendshipJpaRepository extends JpaRepository<Friendship, FriendshipId> {
}
