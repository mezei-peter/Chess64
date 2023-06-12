package hu.mpb.backendchess64.controller;

import hu.mpb.backendchess64.controller.dto.ChessGameDto;
import hu.mpb.backendchess64.exception.InvalidChessMoveException;
import hu.mpb.backendchess64.model.GameRoom;
import hu.mpb.backendchess64.model.Player;
import hu.mpb.backendchess64.service.ChessGameService;
import hu.mpb.backendchess64.service.GameRoomService;
import hu.mpb.backendchess64.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
public class GameRoomController {
    private final SimpMessagingTemplate template;
    private final PlayerService playerService;
    private final GameRoomService gameRoomService;
    private final ChessGameService chessGameService;

    @Autowired
    public GameRoomController(SimpMessagingTemplate template, PlayerService playerService,
                              GameRoomService gameRoomService, ChessGameService chessGameService) {
        this.template = template;
        this.playerService = playerService;
        this.gameRoomService = gameRoomService;
        this.chessGameService = chessGameService;
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

    @PostMapping(path = "/room/pair/{playerId}")
    public ResponseEntity<HttpStatus> pairIntoGameRoom(@PathVariable String playerId) {
        try {
            Player player = playerService.getById(UUID.fromString(playerId)).orElse(null);
            if (player == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            Player opponent = playerService.findOpponentFor(player);
            if (opponent == null) {
                return new ResponseEntity<>(HttpStatus.ACCEPTED);
            }
            GameRoom room = gameRoomService.create(player, opponent);
            template.convertAndSend("/topic/pairings/" + player.getPlayerId(), room);
            template.convertAndSend("/topic/pairings/" + opponent.getPlayerId(), room);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Throwable e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/room/ping/{roomId}")
    public ResponseEntity<HttpStatus> pingRoom(@PathVariable String roomId) {
        UUID uuid = UUID.fromString(roomId);
        GameRoom room = gameRoomService.get(uuid).orElse(null);
        if (room == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        String msg = "Room pinged";
        template.convertAndSend("/topic/roomPings/" + room.getWhitePlayerId().toString(), msg);
        template.convertAndSend("/topic/roomPings/" + room.getBlackPlayerId().toString(), msg);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/room/{roomId}/latestPosition")
    public ResponseEntity<String> getLatestPosition(@PathVariable String roomId) {
        try {
            UUID uuid = UUID.fromString(roomId);
            GameRoom room = gameRoomService.get(uuid).orElse(null);
            if (room == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            String latestFen = room.getLatestFen();
            return new ResponseEntity<>(latestFen, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Throwable e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/room/{roomId}/move/{playerId}")
    public ResponseEntity<HttpStatus> makeMove(@PathVariable String roomId, @PathVariable String playerId,
                                               @RequestBody String moveFen) {
        try {
            UUID roomUUID = UUID.fromString(roomId);
            UUID playerUUID = UUID.fromString(playerId);
            GameRoom room = gameRoomService.get(roomUUID).orElse(null);
            if (room == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            if (!room.canPlayerMove(playerUUID)) {
                throw new IllegalArgumentException("Player with UUID '" + playerId + "' is not in turn to move.");
            }
            chessGameService.makeMove(room.getGame(), moveFen);
            String latestFen = room.getLatestFen();
            ChessGameDto chessGameUpdate = new ChessGameDto(room.getGameResult(), room.splitFenPositions(),
                    chessGameService.calculateLegalMoveFens(latestFen));
            template.convertAndSend("/topic/chessGameUpdate/" + roomId, chessGameUpdate);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException | InvalidChessMoveException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Throwable e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
