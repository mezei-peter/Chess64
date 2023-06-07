package hu.mpb.backendchess64.service;

import hu.mpb.backendchess64.model.GameRoom;
import hu.mpb.backendchess64.model.Player;
import hu.mpb.backendchess64.model.PlayerStatus;
import hu.mpb.backendchess64.repository.GameRoomRepository;
import hu.mpb.backendchess64.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

public class GameRoomServiceImpl implements GameRoomService {
    private final GameRoomRepository gameRoomRepository;
    private final PlayerRepository playerRepository;

    @Autowired
    public GameRoomServiceImpl(GameRoomRepository gameRoomRepository, PlayerRepository playerRepository) {
        this.gameRoomRepository = gameRoomRepository;
        this.playerRepository = playerRepository;
    }

    @Override
    public GameRoom create(Player p1, Player p2) {
        p1.setStatus(PlayerStatus.PLAYING);
        p2.setStatus(PlayerStatus.PLAYING);
        playerRepository.save(p1);
        playerRepository.save(p2);

        int randomVal = new Random().nextInt(2);
        GameRoom room;
        if (randomVal == 0) {
            room = GameRoom.builder()
                    .whitePlayer(p1)
                    .blackPlayer(p2)
                    .build();
        } else {
            room = GameRoom.builder()
                    .whitePlayer(p2)
                    .blackPlayer(p1)
                    .build();
        }
        return gameRoomRepository.save(room);
    }

    @Override
    public Optional<GameRoom> get(UUID id) {
        return gameRoomRepository.findById(id);
    }
}
