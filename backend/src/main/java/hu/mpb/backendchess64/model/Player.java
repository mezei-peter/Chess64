package hu.mpb.backendchess64.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "players")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID playerId;

    @Column
    private String name;
}
