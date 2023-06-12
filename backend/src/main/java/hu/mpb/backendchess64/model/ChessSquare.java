package hu.mpb.backendchess64.model;

import java.util.Arrays;

public class ChessSquare {
    private final byte coordinates[] = new byte[2];
    private ChessPiece chessPiece;

    public ChessSquare(byte file, byte rank, ChessPiece chessPiece) {
        this.coordinates[0] = file;
        this.coordinates[1] = rank;
        this.chessPiece = chessPiece;
    }

    public char getFileName() {
        return switch (coordinates[0]) {
            case 0 -> 'a';
            case 1 -> 'b';
            case 2 -> 'c';
            case 3 -> 'd';
            case 4 -> 'e';
            case 5 -> 'f';
            case 6 -> 'g';
            case 7 -> 'h';
            default -> throw new IllegalStateException("Unexpected value: " + coordinates[0]);
        };
    }

    public byte getRankName() {
        return (byte) (coordinates[1] + 1);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ChessSquare other = (ChessSquare) obj;
        return Arrays.equals(this.coordinates, other.coordinates) &&
               this.chessPiece.equals(other.chessPiece);
    }
}
