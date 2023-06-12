package hu.mpb.backendchess64.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "gameRooms")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID roomId;

    @JoinColumn
    @OneToOne
    private Player whitePlayer;

    @JoinColumn
    @OneToOne
    private Player blackPlayer;

    @JoinColumn
    @OneToOne
    private PersistedChessGame game;

    public UUID getWhitePlayerId() {
        return whitePlayer.getPlayerId();
    }

    public UUID getBlackPlayerId() {
        return blackPlayer.getPlayerId();
    }

    public String getLatestFen() {
        return game.getLatestFen();
    }

    public ChessResult getGameResult() {
        return game.getResult();
    }

    public String[] splitFenPositions() {
        return game.splitFenPositions();
    }

    public boolean canPlayerMove(UUID playerUUID) {
        PieceColor colorToMove = game.getColorToMove();
        if (colorToMove == PieceColor.WHITE && playerUUID.equals(whitePlayer.getPlayerId())) {
            return true;
        }
        return colorToMove == PieceColor.BLACK && playerUUID.equals(blackPlayer.getPlayerId());
    }
}
