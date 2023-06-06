package hu.mpb.backendchess64.service;

import hu.mpb.backendchess64.model.Player;
import hu.mpb.backendchess64.model.PlayerStatus;
import hu.mpb.backendchess64.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.UUID;

public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Player create(String name) {
        Player player = Player.builder()
                .name(name)
                .status(PlayerStatus.WAITING)
                .build();
        return playerRepository.save(player);
    }

    @Override
    public Player findOpponentFor(Player player) {
        return playerRepository.findFirstByStatus(PlayerStatus.WAITING);
    }

    @Override
    public Optional<Player> getById(UUID id) {
        return playerRepository.findById(id);
    }
}
