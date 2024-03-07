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
    private boolean win;



    public GameDto() {
        this.dice1 = diceRandomNum();
        this.dice2 = diceRandomNum();

    }
    public GameDto(int dice1, int dice2) {
        this.dice1 = dice1;
        this.dice2 = dice2;
    }

    public int diceRandomNum() {
        return (int) (Math.random() * 6 + 1);
    }

    public boolean getisGameWin() {
        return dice1 + dice2 == 7;
    }
    public boolean getIsWin() {
        win = getisGameWin();
        return win;
    }
}
