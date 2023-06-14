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
        PieceColor color = position.getActiveColor();
        if (color == PieceColor.NONE || color == null) {
            return false;
        }
        ChessPiece enemyQueen;
        if (color == PieceColor.WHITE) {
            enemyQueen = new ChessPiece(PieceType.QUEEN, PieceColor.BLACK);
        } else if (color == PieceColor.BLACK) {
            enemyQueen = new ChessPiece(PieceType.QUEEN, PieceColor.WHITE);
        } else {
            enemyQueen = new ChessPiece(PieceType.NONE, PieceColor.NONE);
        }
        return findCheckOnLine(position, kingX, kingY, +1, 0, enemyQueen)
               || findCheckOnLine(position, kingX, kingY, -1, 0, enemyQueen)
               || findCheckOnLine(position, kingX, kingY, 0, +1, enemyQueen)
               || findCheckOnLine(position, kingX, kingY, 0, -1, enemyQueen)
               || findCheckOnLine(position, kingX, kingY, +1, +1, enemyQueen)
               || findCheckOnLine(position, kingX, kingY, -1, +1, enemyQueen)
               || findCheckOnLine(position, kingX, kingY, +1, -1, enemyQueen)
               || findCheckOnLine(position, kingX, kingY, -1, -1, enemyQueen);
    }

    private boolean findRookChecks(ChessPosition position, int kingX, int kingY) {
        PieceColor color = position.getActiveColor();
        if (color == PieceColor.NONE || color == null) {
            return false;
        }
        ChessPiece enemyRook;
        if (color == PieceColor.WHITE) {
            enemyRook = new ChessPiece(PieceType.ROOK, PieceColor.BLACK);
        } else if (color == PieceColor.BLACK) {
            enemyRook = new ChessPiece(PieceType.ROOK, PieceColor.WHITE);
        } else {
            enemyRook = new ChessPiece(PieceType.NONE, PieceColor.NONE);
        }
        return findCheckOnLine(position, kingX, kingY, +1, 0, enemyRook)
               || findCheckOnLine(position, kingX, kingY, -1, 0, enemyRook)
               || findCheckOnLine(position, kingX, kingY, 0, +1, enemyRook)
               || findCheckOnLine(position, kingX, kingY, 0, -1, enemyRook);
    }

    private boolean findBishopChecks(ChessPosition position, int kingX, int kingY) {
        PieceColor color = position.getActiveColor();
        if (color == PieceColor.NONE || color == null) {
            return false;
        }
        ChessPiece enemyBishop;
        if (color == PieceColor.WHITE) {
            enemyBishop = new ChessPiece(PieceType.BISHOP, PieceColor.BLACK);
        } else if (color == PieceColor.BLACK) {
            enemyBishop = new ChessPiece(PieceType.BISHOP, PieceColor.WHITE);
        } else {
            enemyBishop = new ChessPiece(PieceType.NONE, PieceColor.NONE);
        }
        return findCheckOnLine(position, kingX, kingY, +1, +1, enemyBishop)
               || findCheckOnLine(position, kingX, kingY, -1, +1, enemyBishop)
               || findCheckOnLine(position, kingX, kingY, +1, -1, enemyBishop)
               || findCheckOnLine(position, kingX, kingY, -1, -1, enemyBishop);
    }

    private boolean findCheckOnLine(ChessPosition position, int kingX, int kingY, int offsetX, int offsetY,
                                    ChessPiece enemyPiece) {
        if (offsetX == 0 && offsetY == 0) {
            return false;
        }
        int len = position.getPiecePositionsLength();
        boolean isPresentBlockingPiece = false;
        for (int i = kingX + offsetX, j = kingY + offsetY;
             (offsetX > 0 ? i < len : i >= 0) || (offsetY > 0 ? j < len : j >= 0);
             i += offsetX, j += offsetY) {
            ChessPiece piece = position.getPieceAt(i, j);
            if (piece.equals(enemyPiece) && !isPresentBlockingPiece) {
                return true;
            }
            if (!piece.equals(ChessPiece.none())) {
                isPresentBlockingPiece = true;
            }
        }
        return false;
    }

    private boolean findKnightChecks(ChessPosition position, int kingX, int kingY) {
        PieceColor color = position.getActiveColor();
        if (color == PieceColor.NONE || color == null) {
            return false;
        }
        ChessPiece enemyKnight;
        if (color == PieceColor.WHITE) {
            enemyKnight = new ChessPiece(PieceType.KNIGHT, PieceColor.BLACK);
        } else if (color == PieceColor.BLACK) {
            enemyKnight = new ChessPiece(PieceType.KNIGHT, PieceColor.WHITE);
        } else {
            enemyKnight = new ChessPiece(PieceType.NONE, PieceColor.NONE);
        }
        return enemyKnight.equals(position.getPieceAt(kingX - 2, kingY - 1))
               || enemyKnight.equals(position.getPieceAt(kingX - 2, kingY + 1))
               || enemyKnight.equals(position.getPieceAt(kingX + 2, kingY - 1))
               || enemyKnight.equals(position.getPieceAt(kingX + 2, kingY + 1))
               || enemyKnight.equals(position.getPieceAt(kingX - 1, kingY - 1))
               || enemyKnight.equals(position.getPieceAt(kingX - 1, kingY + 1))
               || enemyKnight.equals(position.getPieceAt(kingX + 1, kingY - 1))
               || enemyKnight.equals(position.getPieceAt(kingX + 1, kingY + 1));
    }

    private boolean findPawnChecks(ChessPosition position, int kingX, int kingY) {
        PieceColor color = position.getActiveColor();
        if (color == PieceColor.WHITE) {
            return position.getPieceTypeAt(kingX - 1, kingY - 1) == PieceType.PAWN
                   || position.getPieceTypeAt(kingX + 1, kingY - 1) == PieceType.PAWN;
        } else if (color == PieceColor.BLACK) {
            return position.getPieceTypeAt(kingX - 1, kingY + 1) == PieceType.PAWN
                   || position.getPieceTypeAt(kingX + 1, kingY + 1) == PieceType.PAWN;
        } else {
            return false;
        }
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
