package hu.mpb.backendchess64.model;

public class ChessPosition {
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
}
