package hu.mpb.backendchess64.service;

import hu.mpb.backendchess64.model.GameRoom;
import hu.mpb.backendchess64.model.Player;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public interface GameRoomService {
    GameRoom create(Player player, Player opponent);

    Optional<GameRoom> get(UUID id);
}
