package hu.mpb.backendchess64.service;

import hu.mpb.backendchess64.model.GameRoom;
import hu.mpb.backendchess64.model.Player;
import hu.mpb.backendchess64.repository.GameRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class GameRoomServiceImpl implements GameRoomService {
    private final GameRoomRepository gameRoomRepository;

    @Autowired
    public GameRoomServiceImpl(GameRoomRepository gameRoomRepository) {
        this.gameRoomRepository = gameRoomRepository;
    }

    @Override
    public GameRoom create(Player player, Player opponent) {
        return null;
    }
}
