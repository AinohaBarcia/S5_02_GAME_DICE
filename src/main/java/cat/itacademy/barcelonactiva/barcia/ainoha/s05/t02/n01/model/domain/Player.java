package cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name="Player",uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Player implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPlayer;
    @Column(name = "Name", length = 45, nullable = false, unique = true)
    private String name;
    @Temporal(TemporalType.DATE)
    @Column(name = "Registration date", nullable = false, length = 25)
    private Date registrationDate;
    @Column(name = "Wins")
    private int gamesWin;
    @Column(name = "Average wins")
    private double calculateSuccessRate;
    @Column(name = "Lost")
    private int gamesLost;
    @Column(name = "Average lost")
    private double calculateLostRate;

    @OneToMany(mappedBy = "player", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Game> gameList;
    public Player(){this.registrationDate=new Date();}
}
