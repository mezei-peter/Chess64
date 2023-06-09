package hu.mpb.backendchess64.model;

public record CastlingRights(boolean whiteKingSide, boolean whiteQueenSide, boolean blackKingSide,
                             boolean BlackQueenSide) {
}
