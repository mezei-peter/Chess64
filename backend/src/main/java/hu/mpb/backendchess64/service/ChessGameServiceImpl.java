package hu.mpb.backendchess64.service;

import hu.mpb.backendchess64.exception.InvalidChessMoveException;
import hu.mpb.backendchess64.model.*;
import hu.mpb.backendchess64.repository.ChessGameRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

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
    public void makeMove(PersistedChessGame game, String newMoveFen) throws InvalidChessMoveException {
        //TODO
    }

    @Override
    public String[] calculateMoveFens(String fen) {
        List<String> legalList = new ArrayList<>();
        ChessPosition position = ChessPosition.fromFen(fen);
        ChessPiece[][] pieceMatrix = position.getPiecePositions();
        boolean isInCheck = determineCheck(position);
        for (int i = 0; i < pieceMatrix.length; i++) {
            ChessPiece[] rank = pieceMatrix[i];
            for (int j = 0; j < rank.length; j++) {
                ChessPiece piece = pieceMatrix[i][j];
                if (position.sameActiveColor(piece.color())) {
                    switch (piece.type()) {
                        case KING -> legalList.addAll(calcKingMoveFens(piece, position, i, j, isInCheck));
                        case QUEEN -> legalList.addAll(calcQueenMoveFens(piece, position, i, j, isInCheck));
                        case ROOK -> legalList.addAll(calcRookMoveFens(piece, position, i, j, isInCheck));
                        case BISHOP -> legalList.addAll(calcBishopMoveFens(piece, position, i, j, isInCheck));
                        case KNIGHT -> legalList.addAll(calcKnightMoveFens(piece, position, i, j, isInCheck));
                        case PAWN -> legalList.addAll(calcPawnMoveFens(piece, position, i, j, isInCheck));
                    }
                }
            }
        }
        String[] legalFens = new String[legalList.size()];
        for (int i = 0; i < legalFens.length; i++) {
            legalFens[i] = legalList.get(i);
        }
        return legalFens;
    }

    private boolean determineCheck(ChessPosition position) {
        PieceColor color = position.getActiveColor();
        ChessPiece[][] matrix = position.getPiecePositions();
        int kingX = -1, kingY = -1;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                ChessPiece piece = matrix[i][j];
                if (PieceType.KING.equals(piece.type()) && color.equals(piece.color())) {
                    kingX = i;
                    kingY = j;
                    break;
                }
            }
            if (i == matrix.length - 1) {
                return false;
            }
        }
        return findPawnChecks(position, kingX, kingY) || findKnightChecks(position, kingX, kingY)
               || findBishopChecks(position, kingX, kingY) || findRookChecks(position, kingX, kingY)
               || findQueenChecks(position, kingX, kingY);
    }

    private boolean findQueenChecks(ChessPosition position, int kingX, int kingY) {
        // TODO
        return false;
    }

    private boolean findRookChecks(ChessPosition position, int kingX, int kingY) {
        // TODO
        return false;
    }

    private boolean findBishopChecks(ChessPosition position, int kingX, int kingY) {
        // TODO
        return false;
    }

    private boolean findKnightChecks(ChessPosition position, int kingX, int kingY) {
        // TODO
        return false;
    }

    private boolean findPawnChecks(ChessPosition position, int kingX, int kingY) {
        // TODO
        return false;
    }

    private Collection<String> calcPawnMoveFens(ChessPiece piece, ChessPosition position, int file, int rank,
                                                boolean inCheck) {
        //TODO
        return new HashSet<>();
    }

    private Collection<String> calcKnightMoveFens(ChessPiece piece, ChessPosition position, int file, int rank,
                                                  boolean inCheck) {
        //TODO
        return new HashSet<>();
    }

    private Collection<String> calcBishopMoveFens(ChessPiece piece, ChessPosition position, int file, int rank,
                                                  boolean inCheck) {
        //TODO
        return new HashSet<>();
    }

    private Collection<String> calcRookMoveFens(ChessPiece piece, ChessPosition position, int file, int rank,
                                                boolean inCheck) {
        //TODO
        return new HashSet<>();
    }

    private Collection<String> calcQueenMoveFens(ChessPiece piece, ChessPosition position, int file, int rank,
                                                 boolean inCheck) {
        //TODO
        return new HashSet<>();
    }

    private Collection<String> calcKingMoveFens(ChessPiece piece, ChessPosition position, int file, int rank,
                                                boolean inCheck) {
        //TODO
        return new HashSet<>();
    }

}
