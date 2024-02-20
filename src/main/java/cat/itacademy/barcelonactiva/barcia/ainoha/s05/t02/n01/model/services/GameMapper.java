package cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.services;

import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.domain.Game;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.domain.Player;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.dto.GameDto;

public class GameMapper {

    public static GameDto mapToGameDto(Game game) {
        GameDto gameDto = new GameDto();
        gameDto.setIdGame(game.getIdGame());
        gameDto.setDice1(game.getDice1());
        gameDto.setDice2(game.getDice2());
        gameDto.setWin(game.isWin());
            return gameDto;
        }


    public static Game mapToGame(GameDto gameDto, Player player){
        Game game = new Game(player);
        game.setIdGame(gameDto.getIdGame());
        game.setDice1(gameDto.getDice1());
        game.setDice2(gameDto.getDice2());
        return game;
    }

}
