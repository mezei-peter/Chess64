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

}
