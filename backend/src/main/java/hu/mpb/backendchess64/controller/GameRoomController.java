package hu.mpb.backendchess64.controller;

import hu.mpb.backendchess64.model.GameRoom;
import hu.mpb.backendchess64.model.Player;
import hu.mpb.backendchess64.service.GameRoomService;
import hu.mpb.backendchess64.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

@Controller
public class GameRoomController {
    private final PlayerService playerService;
    private final GameRoomService gameRoomService;

    @Autowired
    public GameRoomController(PlayerService playerService, GameRoomService gameRoomService) {
        this.playerService = playerService;
        this.gameRoomService = gameRoomService;
    }

    @GetMapping("/room/{id}")
    public @ResponseBody ResponseEntity<GameRoom> getRoomById(@PathVariable String id) {
        try {
            UUID uuid = UUID.fromString(id);
            GameRoom room = gameRoomService.get(uuid).orElse(null);
            if (room == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(room, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Throwable e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @MessageMapping("/pair/{userId}")
    @SendTo("/topic/pairings")
    public GameRoom pairIntoGameRoom(@DestinationVariable String userId) {
        try {
            Player player = playerService.getById(UUID.fromString(userId)).orElse(null);
            if (player == null) {
                return null;
            }
            Player opponent = playerService.findOpponentFor(player);
            if (opponent == null) {
                return null;
            }
            return gameRoomService.create(player, opponent);
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }
}
