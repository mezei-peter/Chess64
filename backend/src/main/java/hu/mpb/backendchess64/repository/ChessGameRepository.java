package hu.mpb.backendchess64.repository;

import hu.mpb.backendchess64.model.PersistedChessGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ChessGameRepository extends JpaRepository<PersistedChessGame, UUID> {
}
