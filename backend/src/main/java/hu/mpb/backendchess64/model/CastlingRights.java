package hu.mpb.backendchess64.model;

public record CastlingRights(boolean whiteKingSide, boolean whiteQueenSide, boolean blackKingSide,
                             boolean blackQueenSide) {
    public boolean none() {
        return !whiteKingSide && !whiteQueenSide && !blackKingSide && !blackQueenSide;
    }
}
