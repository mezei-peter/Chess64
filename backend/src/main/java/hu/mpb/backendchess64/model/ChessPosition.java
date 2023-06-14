package hu.mpb.backendchess64.model;

import lombok.Getter;

import java.util.Arrays;

@Getter
public class ChessPosition {
    private static final int piecePosIndex = 0;
    private static final int colorIndex = 1;
    private static final int castlingIndex = 2;
    private static final int enPassantIndex = 3;
    private static final int halfMoveIndex = 4;
    private static final int fullMoveIndex = 5;


    private final ChessPiece[][] piecePositions;
    private final PieceColor activeColor;
    private final CastlingRights castlingRights;
    private final ChessSquare enPassantTarget;
    private final short halfMoveClock;
    private final short fullMoveClock;

    public ChessPosition(ChessPiece[][] piecePositions, PieceColor activeColor, CastlingRights castlingRights,
                         ChessSquare enPassantTarget, short halfMoveClock, short fullMoveClock) {
        this.piecePositions = piecePositions;
        this.activeColor = activeColor;
        this.castlingRights = castlingRights;
        this.enPassantTarget = enPassantTarget;
        this.halfMoveClock = halfMoveClock;
        this.fullMoveClock = fullMoveClock;
    }

    public static ChessPosition fromFen(String fen) {
        String[] splitFen = fen.split(" ");
        ChessPiece[][] piecePositions = convertToPiecePositions(splitFen[piecePosIndex]);
        PieceColor color = convertToColor(splitFen[colorIndex]);
        CastlingRights castlingRights = convertToCastlingRights(splitFen[castlingIndex]);
        ChessSquare enPassantSquare = convertToEnPassantSquare(splitFen[enPassantIndex]);
        short halfMoves = Short.parseShort(splitFen[halfMoveIndex]);
        short fullMoves = Short.parseShort(splitFen[fullMoveIndex]);
        return new ChessPosition(piecePositions, color, castlingRights, enPassantSquare, halfMoves, fullMoves);
    }

    private static ChessSquare convertToEnPassantSquare(String s) {
        assert s.length() == 2 : "en passant square notation is not 2 characters long";
        char file = s.charAt(0);
        char rank = s.charAt(1);
        byte fileIdx = switch (file) {
            case 'a' -> 0;
            case 'b' -> 1;
            case 'c' -> 2;
            case 'd' -> 3;
            case 'e' -> 4;
            case 'f' -> 5;
            case 'g' -> 6;
            case 'h' -> 7;
            default -> throw new IllegalStateException("Unexpected value: " + file);
        };
        byte rankIdx = switch (rank) {
            case '8' -> 0;
            case '7' -> 1;
            case '6' -> 2;
            case '5' -> 3;
            case '4' -> 4;
            case '3' -> 5;
            case '2' -> 6;
            case '1' -> 7;
            default -> throw new IllegalStateException("Unexpected value: " + file);
        };
        return new ChessSquare(fileIdx, rankIdx, ChessPiece.none());
    }

    private static CastlingRights convertToCastlingRights(String s) {
        return new CastlingRights(
                s.contains("K"),
                s.contains("Q"),
                s.contains("k"),
                s.contains("q")
        );
    }

    private static PieceColor convertToColor(String s) {
        if (s.equals("w")) {
            return PieceColor.WHITE;
        }
        if (s.equals("b")) {
            return PieceColor.BLACK;
        }
        return PieceColor.NONE;
    }

    private static ChessPiece[][] convertToPiecePositions(String s) {
        String[] ranks = s.split("/");
        assert ranks.length == 8 : "Invalid number of ranks in FEN: should be 8.";
        ChessPiece[][] pieces = new ChessPiece[ChessGame.BOARD_SIZE][ChessGame.BOARD_SIZE];
        for (int i = 0; i < ranks.length; i++) {
            String rank = ranks[i];
            int emptyOffset = 0;
            for (int j = 0; j < rank.length(); j++) {
                char c = rank.charAt(j);
                if (Character.isDigit(c)) {
                    int digit = Character.getNumericValue(c);
                    for (int x = 0; x < digit; x++) {
                        pieces[i][j + x + emptyOffset] = ChessPiece.none();
                    }
                    emptyOffset += digit - 1;
                } else {
                    pieces[i][j + emptyOffset] = ChessPiece.fromChar(c);
                }
            }
        }
        return pieces;
    }

    public String getFEN() {
        StringBuilder sb = new StringBuilder();
        appendFenPiecePositions(sb);
        appendFenActiveColor(sb);
        appendFenCastlingRights(sb);
        appendFenEnPassantTarget(sb);
        sb.append(" ").append(halfMoveClock).append(" ").append(fullMoveClock);
        return sb.toString();
    }

    private void appendFenEnPassantTarget(StringBuilder sb) {
        if (enPassantTarget == null) {
            sb.append(" -");
            return;
        }
        sb.append(" ");
        char fileVal = enPassantTarget.getFileName();
        byte rankVal = enPassantTarget.getRankName();
        sb.append(fileVal);
        sb.append(rankVal);
    }

    private void appendFenCastlingRights(StringBuilder sb) {
        if (castlingRights == null) {
            return;
        }
        sb.append(" ");
        if (castlingRights.none()) {
            sb.append('-');
        } else {
            if (castlingRights.whiteKingSide()) {
                sb.append('K');
            }
            if (castlingRights.whiteQueenSide()) {
                sb.append('Q');
            }
            if (castlingRights.blackKingSide()) {
                sb.append('k');
            }
            if (castlingRights.blackQueenSide()) {
                sb.append('q');
            }
        }
    }

    private void appendFenActiveColor(StringBuilder sb) {
        if (activeColor == null) {
            return;
        }
        sb.append(" ");
        char c;
        if (activeColor == PieceColor.WHITE) {
            c = 'w';
        } else if (activeColor == PieceColor.BLACK) {
            c = 'b';
        } else {
            c = '-';
        }
        sb.append(c);
    }

    private void appendFenPiecePositions(StringBuilder sb) {
        if (piecePositions == null) {
            return;
        }
        for (int i = 0; i < piecePositions.length; i++) {
            byte emptySquares = 0;
            for (int j = 0; j < piecePositions[i].length; j++) {
                ChessPiece chessPiece = piecePositions[i][j];
                if (chessPiece.isNone()) {
                    emptySquares++;
                    if (j == piecePositions[i].length - 1) {
                        sb.append(emptySquares);
                        emptySquares = 0;
                    }
                } else {
                    if (emptySquares > 0) {
                        sb.append(emptySquares);
                        emptySquares = 0;
                    }
                    sb.append(chessPiece.getFenCode());
                }
            }
            if (i < piecePositions.length - 1) {
                sb.append('/');
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ChessPosition other = (ChessPosition) obj;
        return Arrays.deepEquals(this.piecePositions, other.piecePositions) &&
               activeColor == other.activeColor &&
               castlingRights.equals(other.castlingRights) &&
               enPassantTarget.equals(other.enPassantTarget) &&
               halfMoveClock == other.halfMoveClock &&
               fullMoveClock == other.fullMoveClock;
    }

    public boolean sameActiveColor(PieceColor color) {
        return activeColor.equals(color);
    }

    public PieceType getPieceTypeAt(int x, int y) {
        return getPieceAt(x, y).type();
    }

    public ChessPiece getPieceAt(int x, int y) {
        if (x >= piecePositions.length || x < 0) {
            return ChessPiece.none();
        }
        if (y >= piecePositions[x].length || y < 0) {
            return ChessPiece.none();
        }
        return piecePositions[x][y];
    }

    public int getPiecePositionsLength() {
        return this.piecePositions.length;
    }
}
