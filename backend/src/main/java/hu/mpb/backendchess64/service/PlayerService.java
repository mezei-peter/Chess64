package hu.mpb.backendchess64.service;

import hu.mpb.backendchess64.model.Player;
import org.springframework.stereotype.Service;

@Service
public interface PlayerService {
    Player create(String name);
}
