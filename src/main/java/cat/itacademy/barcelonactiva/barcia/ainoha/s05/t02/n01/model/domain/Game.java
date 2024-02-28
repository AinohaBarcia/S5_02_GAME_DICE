package cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Date;
@Getter
@Setter
@Entity
@Table(name = "Game")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGame;

    @ManyToOne
    @JoinColumn(name = "id_Player")
    private Player player;
    @Column(name = "Dice 1", length = 15, nullable = false)
    private int dice1;
    @Column(name = "Dice 2", length = 15, nullable = false)
    private int dice2;
    @Column(name = "Game won", nullable = false, length = 25)
    private boolean win;


    public Game(Long idGame, int dice1, int dice2) {
    }

    public Game(Long idGame, Player player, int dice1, int dice2) {
        this.idGame = idGame;
        this.player = player;
        this.dice1 = dice1;
        this.dice2 = dice2;
    }
    public Game(){}
    public Game(Player playere) {
        this.player = player;
    }

}