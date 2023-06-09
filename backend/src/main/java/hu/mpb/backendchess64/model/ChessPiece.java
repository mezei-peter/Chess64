package hu.mpb.backendchess64.model;

public record ChessPiece(PieceType type, PieceColor color) {
    public static ChessPiece none() {
        return new ChessPiece(PieceType.NONE, PieceColor.NONE);
    }
}
