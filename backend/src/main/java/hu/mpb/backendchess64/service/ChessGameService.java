package hu.mpb.backendchess64.service;

import hu.mpb.backendchess64.exception.InvalidChessMoveException;
import hu.mpb.backendchess64.model.PersistedChessGame;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface ChessGameService {
    PersistedChessGame createPersistedChessGame(String white, String black);

    void makeMove(PersistedChessGame game, String newMoveFen) throws InvalidChessMoveException;

    String[] calculateLegalMoveFens(String latestFen);
}
