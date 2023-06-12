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
    private static final char fenSplitter = ';';

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
}
