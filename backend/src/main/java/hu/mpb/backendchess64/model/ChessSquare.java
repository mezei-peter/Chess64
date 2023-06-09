package hu.mpb.backendchess64.model;

public class ChessSquare {
    private final byte coordinates[] = new byte[2];
    private ChessPiece chessPiece;

    public ChessSquare(byte file, byte rank, ChessPiece chessPiece) {
        this.coordinates[0] = file;
        this.coordinates[1] = rank;
        this.chessPiece = chessPiece;
    }
}
