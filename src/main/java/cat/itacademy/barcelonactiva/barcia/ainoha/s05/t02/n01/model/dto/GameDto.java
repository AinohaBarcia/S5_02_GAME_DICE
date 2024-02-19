package cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.security.Timestamp;


@Getter
@Setter
public class GameDto {

    private Long idGame;
    private int dice1;
    private int dice2;
    private Timestamp playedTime;
    private boolean win;



    public GameDto() {
        this.dice1 = diceRandomNum();
        this.dice2 = diceRandomNum();
        this.win = isGameWin();
    }

    public int diceRandomNum() {
        return (int) (Math.random() * 6 + 1);
    }

    public boolean isGameWin() {
        return dice1 + dice2 == 7;
    }
}
