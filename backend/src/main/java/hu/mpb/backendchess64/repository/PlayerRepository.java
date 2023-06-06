package hu.mpb.backendchess64.repository;

import hu.mpb.backendchess64.model.Player;
import hu.mpb.backendchess64.model.PlayerStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PlayerRepository extends JpaRepository<Player, UUID> {
    Player findFirstByStatus(PlayerStatus status);
}
