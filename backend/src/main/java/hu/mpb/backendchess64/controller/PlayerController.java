package hu.mpb.backendchess64.controller;

import hu.mpb.backendchess64.model.Player;
import hu.mpb.backendchess64.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
