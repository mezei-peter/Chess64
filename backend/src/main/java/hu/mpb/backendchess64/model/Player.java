package hu.mpb.backendchess64.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "players")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID playerId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private PlayerStatus status;
}


