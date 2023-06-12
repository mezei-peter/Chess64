package hu.mpb.backendchess64.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PersistedChessGameTest {

    @Test
    void testGetColorToMove_White() {
        PersistedChessGame game = PersistedChessGame.builder()
                .fenPositions("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1")
                .build();

        PieceColor expected = PieceColor.WHITE;
        PieceColor result = game.getColorToMove();

        assertEquals(expected, result);
    }

    @Test
    void testGetColorToMove_Black() {
        PersistedChessGame game = PersistedChessGame.builder()
                .fenPositions("rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1")
                .build();

        PieceColor expected = PieceColor.BLACK;
        PieceColor result = game.getColorToMove();

        assertEquals(expected, result);
    }

    @Test
    void testGetColorToMove_None() {
        PersistedChessGame game = PersistedChessGame.builder()
                .fenPositions("rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR - KQkq e3 0 1")
                .build();

        PieceColor expected = PieceColor.NONE;
        PieceColor result = game.getColorToMove();

        assertEquals(expected, result);
    }
}