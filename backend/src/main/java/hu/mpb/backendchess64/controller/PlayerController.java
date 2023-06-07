package hu.mpb.backendchess64.controller;

import hu.mpb.backendchess64.model.Player;
import hu.mpb.backendchess64.service.PlayerService;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/player")
public class PlayerController {
    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping("/")
    public ResponseEntity<String> createUnnamedPlayer() {
        return createPlayer("");
    }


    @PostMapping("/{name}")
    public ResponseEntity<String> createPlayer(@PathVariable String name) {
        try {
            Player player = playerService.create(name);
            return new ResponseEntity<>(player.getPlayerId().toString(), HttpStatus.CREATED);
        } catch (Throwable e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deletePlayer(@PathVariable String id) {
        try {
            UUID uuid = UUID.fromString(id);
            playerService.delete(uuid);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Throwable e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
