package hu.mpb.backendchess64.service;

import hu.mpb.backendchess64.model.Player;
import hu.mpb.backendchess64.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Player create(String name) {
        Player player = Player.builder().name(name).build();
        return playerRepository.save(player);
    }
}
