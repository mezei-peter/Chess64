package hu.mpb.backendchess64.service;

import hu.mpb.backendchess64.model.Player;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public interface PlayerService {
    Player create(String name);

    Player findOpponentFor(Player player);

    Optional<Player> getById(UUID fromString);
}
