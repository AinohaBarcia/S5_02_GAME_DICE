package cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.service;

import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.exceptions.PlayerNotFoundException;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.dto.GameDto;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.dto.PlayerDto;

import java.util.List;

public interface IPlayerService {
    void createPlayer(PlayerDto playerDto);

    PlayerDto updatePlayer(PlayerDto playerDto, Long idPlayer);

    PlayerDto deltePlayerById(Long idPlayer) throws PlayerNotFoundException;

    PlayerDto getPlayerById(Long idPlayer) throws PlayerNotFoundException;

    List<PlayerDto> getAllPlayers();

    void updateResultGame (GameDto gameDto,PlayerDto playerDto);
    public GameDto play (Long idPlayer);
    List<GameDto> getAllGames(Long idPlayer);

    void deleteGames(Long idPlayer);

    double calculateSuccessRate(int totalGames, int wins);
    double getAverageSuccesRate();

    public PlayerDto getBestWinnerPlayer();
    PlayerDto getWorstWinnerPlayer();
    void restartAverage(PlayerDto playerDto);
    void updateGameWin(PlayerDto playerDto);
    void updateGameLost (PlayerDto playerDto);

}
