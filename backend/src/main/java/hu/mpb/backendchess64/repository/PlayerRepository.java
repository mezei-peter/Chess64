package hu.mpb.backendchess64.repository;

import hu.mpb.backendchess64.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PlayerRepository extends JpaRepository<Player, UUID> {
}
