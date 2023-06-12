package hu.mpb.backendchess64.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChessPositionTest {
    private final ChessPiece whiteKing = new ChessPiece(PieceType.KING, PieceColor.WHITE);
    private final ChessPiece whiteQueen = new ChessPiece(PieceType.QUEEN, PieceColor.WHITE);
    private final ChessPiece whiteRook = new ChessPiece(PieceType.ROOK, PieceColor.WHITE);
    private final ChessPiece whiteBishop = new ChessPiece(PieceType.BISHOP, PieceColor.WHITE);
    private final ChessPiece whiteKnight = new ChessPiece(PieceType.KNIGHT, PieceColor.WHITE);
    private final ChessPiece whitePawn = new ChessPiece(PieceType.PAWN, PieceColor.WHITE);
    private final ChessPiece blackKing = new ChessPiece(PieceType.KING, PieceColor.BLACK);
    private final ChessPiece blackQueen = new ChessPiece(PieceType.QUEEN, PieceColor.BLACK);
    private final ChessPiece blackRook = new ChessPiece(PieceType.ROOK, PieceColor.BLACK);
    private final ChessPiece blackBishop = new ChessPiece(PieceType.BISHOP, PieceColor.BLACK);
    private final ChessPiece blackKnight = new ChessPiece(PieceType.KNIGHT, PieceColor.BLACK);
    private final ChessPiece blackPawn = new ChessPiece(PieceType.PAWN, PieceColor.BLACK);
    private final ChessPiece none = ChessPiece.none();

    @Test
    void testGetFEN_DutchLeningrad() {
        ChessPosition chessPosition = new ChessPosition(
                new ChessPiece[][]{
                        {blackRook, blackKnight, blackBishop, blackQueen, none, blackRook, blackKing, none},
                        {blackPawn, blackPawn, blackPawn, none, blackPawn, none, blackBishop, blackPawn},
                        {none, none, none, blackPawn, none, blackKnight, blackPawn, none},
                        {none, none, none, none, none, blackPawn, none, none},
                        {none, none, whitePawn, whitePawn, none, none, none, none},
                        {none, none, whiteKnight, none, none, whiteKnight, whitePawn, none},
                        {whitePawn, whitePawn, none, none, whitePawn, whitePawn, whiteBishop, whitePawn},
                        {whiteRook, none, whiteBishop, whiteQueen, none, whiteRook, whiteKing, none},
                },
                PieceColor.BLACK,
                new CastlingRights(false, false, false, false),
                null,
                (short) 1,
                (short) 7
        );

        String expected = "rnbq1rk1/ppp1p1bp/3p1np1/5p2/2PP4/2N2NP1/PP2PPBP/R1BQ1RK1 b - - 1 7";
        String result = chessPosition.getFEN();
        assertEquals(expected, result);
    }

    @Test
    void testGetFEN_Berlin() {
        ChessPosition chessPosition = new ChessPosition(
                new ChessPiece[][]{
                        {blackRook, none, blackBishop, blackQueen, blackKing, blackBishop, none, blackRook},
                        {blackPawn, blackPawn, blackPawn, blackPawn, none, blackPawn, blackPawn, blackPawn},
                        {none, none, blackKnight, none, none, blackKnight, none, none},
                        {none, whiteBishop, none, none, blackPawn, none, none, none},
                        {none, none, none, none, whitePawn, none, none, none},
                        {none, none, none, none, none, whiteKnight, none, none},
                        {whitePawn, whitePawn, whitePawn, whitePawn, none, whitePawn, whitePawn, whitePawn},
                        {whiteRook, whiteKnight, whiteBishop, whiteQueen, whiteKing, none, none, whiteRook},
                },
                PieceColor.WHITE,
                new CastlingRights(true, true, true, true),
                null,
                (short) 4,
                (short) 4
        );

        String expected = "r1bqkb1r/pppp1ppp/2n2n2/1B2p3/4P3/5N2/PPPP1PPP/RNBQK2R w KQkq - 4 4";
        String result = chessPosition.getFEN();
        assertEquals(expected, result);
    }

    @Test
    void testGetFEN_1_e4() {
        ChessPosition chessPosition = new ChessPosition(
                new ChessPiece[][]{
                        {blackRook, blackKnight, blackBishop, blackQueen, blackKing, blackBishop, blackKnight, blackRook},
                        {blackPawn, blackPawn, blackPawn, blackPawn, blackPawn, blackPawn, blackPawn, blackPawn},
                        {none, none, none, none, none, none, none, none},
                        {none, none, none, none, none, none, none, none},
                        {none, none, none, none, whitePawn, none, none, none},
                        {none, none, none, none, none, none, none, none},
                        {whitePawn, whitePawn, whitePawn, whitePawn, none, whitePawn, whitePawn, whitePawn},
                        {whiteRook, whiteKnight, whiteBishop, whiteQueen, whiteKing, whiteBishop, whiteKnight, whiteRook},
                },
                PieceColor.BLACK,
                new CastlingRights(true, true, true, true),
                new ChessSquare((byte) 4, (byte) 2, ChessPiece.none()),
                (short) 0,
                (short) 1
        );

        String expected = "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1";
        String result = chessPosition.getFEN();
        assertEquals(expected, result);
    }

    @Test
    void testFromFen_1_e4() {
        String fen = "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1";
        ChessPosition expected = new ChessPosition(
                new ChessPiece[][]{
                        {blackRook, blackKnight, blackBishop, blackQueen, blackKing, blackBishop, blackKnight, blackRook},
                        {blackPawn, blackPawn, blackPawn, blackPawn, blackPawn, blackPawn, blackPawn, blackPawn},
                        {none, none, none, none, none, none, none, none},
                        {none, none, none, none, none, none, none, none},
                        {none, none, none, none, whitePawn, none, none, none},
                        {none, none, none, none, none, none, none, none},
                        {whitePawn, whitePawn, whitePawn, whitePawn, none, whitePawn, whitePawn, whitePawn},
                        {whiteRook, whiteKnight, whiteBishop, whiteQueen, whiteKing, whiteBishop, whiteKnight, whiteRook},
                },
                PieceColor.BLACK,
                new CastlingRights(true, true, true, true),
                new ChessSquare((byte) 4, (byte) 5, ChessPiece.none()),
                (short) 0,
                (short) 1
        );
        ChessPosition result = ChessPosition.fromFen(fen);
        assertEquals(expected, result);
    }
}