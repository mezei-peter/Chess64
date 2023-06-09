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
        return sb.toString();
    }
}
