package hu.mpb.backendchess64.model;

import java.util.List;

public class ChessGame {
    private final List<ChessPosition> positions;
    private boolean whiteToMove;
    private ChessResult result;

    public ChessGame(List<ChessPosition> positions, boolean whiteToMove, ChessResult result) {
        this.positions = positions;
        this.whiteToMove = whiteToMove;
        this.result = result;
    }
}
