package hu.mpb.backendchess64.service;

import hu.mpb.backendchess64.exception.InvalidChessMoveException;
import hu.mpb.backendchess64.model.PersistedChessGame;
import org.springframework.stereotype.Service;

@Service
public interface ChessGameService {
    PersistedChessGame createPersistedChessGame(String white, String black);

    void makeMove(PersistedChessGame game, String newMoveFen) throws InvalidChessMoveException;

    String[] calculateMoveFens(String latestFen);
}
