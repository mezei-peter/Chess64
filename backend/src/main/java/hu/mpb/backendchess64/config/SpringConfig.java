package hu.mpb.backendchess64.config;

import hu.mpb.backendchess64.repository.GameRoomRepository;
import hu.mpb.backendchess64.repository.PlayerRepository;
import hu.mpb.backendchess64.service.GameRoomService;
import hu.mpb.backendchess64.service.GameRoomServiceImpl;
import hu.mpb.backendchess64.service.PlayerService;
import hu.mpb.backendchess64.service.PlayerServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    @Bean
    public PlayerService playerService(PlayerRepository playerRepository) {
        return new PlayerServiceImpl(playerRepository);
    }

    @Bean
    public GameRoomService gameRoomService(GameRoomRepository gameRoomRepository, PlayerRepository playerRepository) {
        return new GameRoomServiceImpl(gameRoomRepository, playerRepository);
    }
}
