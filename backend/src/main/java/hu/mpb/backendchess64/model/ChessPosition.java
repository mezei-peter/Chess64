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
}
