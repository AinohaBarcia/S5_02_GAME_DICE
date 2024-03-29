package cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.dto;

import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.domain.Game;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDto {

    private Long idPlayer;
    private String name;
    private Date registrationDate;
    private int gamesWin;
    private int gamesLost;
    private double calculateLostRate;
    private double CalculateSuccessRate;
    @JsonIgnore
    private List<Game> gameList;

    public PlayerDto (String name){
        this.name = name;
    }



}
