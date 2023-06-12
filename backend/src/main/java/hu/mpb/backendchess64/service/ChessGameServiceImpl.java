package hu.mpb.backendchess64.service;

import hu.mpb.backendchess64.exception.InvalidChessMoveException;
import hu.mpb.backendchess64.model.ChessResult;
import hu.mpb.backendchess64.model.PersistedChessGame;
import hu.mpb.backendchess64.repository.ChessGameRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.UUID;

public class ChessGameServiceImpl implements ChessGameService {
    private static final String initialFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";

    private final ChessGameRepository chessGameRepository;

    @Autowired
    public ChessGameServiceImpl(ChessGameRepository chessGameRepository) {
        this.chessGameRepository = chessGameRepository;
    }

    @Override
    public PersistedChessGame createPersistedChessGame(String white, String black) {
        PersistedChessGame game = PersistedChessGame.builder()
                .event("Online Chess")
                .site("Chess64")
                .date(LocalDate.now().toString().replace("-", "."))
                .round("")
                .white(white)
                .black(black)
                .result(ChessResult.ONGOING)
                .fenPositions(initialFen)
                .build();
        return chessGameRepository.save(game);
    }

    @Override
    public void makeMove(PersistedChessGame game, UUID playerId, String newMoveFen) throws InvalidChessMoveException {
        //TODO
    }

    @Override
    public String[] calculateLegalMoveFens(String fen) {
        //TODO
        return null;
    }

}
