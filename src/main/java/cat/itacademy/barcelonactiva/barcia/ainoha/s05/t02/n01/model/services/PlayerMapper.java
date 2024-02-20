package cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.services;

import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.domain.Player;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.dto.PlayerDto;

public class PlayerMapper {

    public static PlayerDto mapToPlayerDto (Player player){
        PlayerDto playerDto = new PlayerDto();
        playerDto.setIdPlayer(player.getIdPlayer());
        playerDto.setName(player.getName());
        playerDto.setRegistrationDate(player.getRegistrationDate());
        playerDto.setGamesWin(player.getGamesWin());
        playerDto.setCalculateSuccessRate(player.getCalculateSuccessRate());
        playerDto.setGamesLost(player.getGamesLost());
        playerDto.setCalculateLostRate(player.getCalculateLostRate());
        playerDto.setGameList(player.getGameList());

        return playerDto;
    }

    public static Player mapToPlayer (PlayerDto playerDto){
        Player player = new Player();
            player.setIdPlayer(playerDto.getIdPlayer());
            player.setName(playerDto.getName());
            player.setRegistrationDate(playerDto.getRegistrationDate());
            player.setGamesWin(playerDto.getGamesWin());
            player.setCalculateSuccessRate(playerDto.getCalculateSuccessRate());
            player.setGamesLost(playerDto.getGamesLost());
            player.setCalculateLostRate(playerDto.getCalculateLostRate());
            player.setGameList(playerDto.getGameList());

        return player;
    }

}
