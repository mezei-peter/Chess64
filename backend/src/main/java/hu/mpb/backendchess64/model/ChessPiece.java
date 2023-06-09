package hu.mpb.backendchess64.model;

public record ChessPiece(PieceType type, PieceColor color) {
    public static ChessPiece none() {
        return new ChessPiece(PieceType.NONE, PieceColor.NONE);
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
