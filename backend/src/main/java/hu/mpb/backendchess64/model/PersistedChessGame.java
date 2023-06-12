package hu.mpb.backendchess64.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "chessGames")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersistedChessGame {
    private static final int fenColorFieldIndex = 1;
    public static final char fenSplitter = ';';

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID gameId;

    @Column
    private String event;

    @Column
    private String site;

    @Column
    private String date;

    @Column
    private String round;

    @Column
    private String white;

    @Column
    private String black;

    @Column
    private ChessResult result;

    @Column
    private String fenPositions;

    public String getLatestFen() {
        String[] positions = splitFenPositions();
        return positions[positions.length - 1];
    }

    public String[] splitFenPositions() {
        return fenPositions.split(Character.toString(fenSplitter));
    }

    public PieceColor getColorToMove() {
        char colorField = getLatestFen().split(" ")[fenColorFieldIndex].toCharArray()[0];
        if (colorField == 'w') {
            return PieceColor.WHITE;
        }
        if (colorField == 'b') {
            return PieceColor.BLACK;
        }
        return PieceColor.NONE;
    }
}
