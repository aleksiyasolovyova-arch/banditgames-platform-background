package be.kdg.team11.readmodel.repository;

import be.kdg.team11.readmodel.models.PlayerFavouriteGameRM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PlayerFavouriteGameRMRepository extends JpaRepository<PlayerFavouriteGameRM, UUID> {
}
