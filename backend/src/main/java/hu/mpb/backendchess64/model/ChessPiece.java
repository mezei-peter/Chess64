package hu.mpb.backendchess64.model;

public record ChessPiece(PieceType type, PieceColor color) {
    public static ChessPiece none() {
        return new ChessPiece(PieceType.NONE, PieceColor.NONE);
    }

    public static ChessPiece fromChar(char c) {
        return switch (c) {
            case 'K' -> new ChessPiece(PieceType.KING, PieceColor.WHITE);
            case 'Q' -> new ChessPiece(PieceType.QUEEN, PieceColor.WHITE);
            case 'R' -> new ChessPiece(PieceType.ROOK, PieceColor.WHITE);
            case 'B' -> new ChessPiece(PieceType.BISHOP, PieceColor.WHITE);
            case 'N' -> new ChessPiece(PieceType.KNIGHT, PieceColor.WHITE);
            case 'P' -> new ChessPiece(PieceType.PAWN, PieceColor.WHITE);
            case 'k' -> new ChessPiece(PieceType.KING, PieceColor.BLACK);
            case 'q' -> new ChessPiece(PieceType.QUEEN, PieceColor.BLACK);
            case 'r' -> new ChessPiece(PieceType.ROOK, PieceColor.BLACK);
            case 'b' -> new ChessPiece(PieceType.BISHOP, PieceColor.BLACK);
            case 'n' -> new ChessPiece(PieceType.KNIGHT, PieceColor.BLACK);
            case 'p' -> new ChessPiece(PieceType.PAWN, PieceColor.BLACK);
            default -> ChessPiece.none();
        };
    }

    public boolean isNone() {
        return this.type == PieceType.NONE;
    }

    public char getFenCode() {
        char pieceCode = switch (this.type) {
            case KING -> 'k';
            case QUEEN -> 'q';
            case ROOK -> 'r';
            case BISHOP -> 'b';
            case KNIGHT -> 'n';
            case PAWN -> 'p';
            default -> Character.MAX_VALUE;
        };
        if (this.color == PieceColor.WHITE) {
            pieceCode = Character.toUpperCase(pieceCode);
        }
        return pieceCode;
    }
}
