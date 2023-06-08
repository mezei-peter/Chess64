package hu.mpb.backendchess64.config;

import hu.mpb.backendchess64.repository.ChessGameRepository;
import hu.mpb.backendchess64.repository.GameRoomRepository;
import hu.mpb.backendchess64.repository.PlayerRepository;
import hu.mpb.backendchess64.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    @Bean
    public PlayerService playerService(PlayerRepository playerRepository) {
        return new PlayerServiceImpl(playerRepository);
    }

    @Bean public ChessGameService chessGameService(ChessGameRepository chessGameRepository) {
        return new ChessGameServiceImpl(chessGameRepository);
    }

    @Bean
    public GameRoomService gameRoomService(GameRoomRepository gameRoomRepository, PlayerRepository playerRepository,
                                           ChessGameService chessGameService) {
        return new GameRoomServiceImpl(gameRoomRepository, playerRepository, chessGameService);
    }
}
