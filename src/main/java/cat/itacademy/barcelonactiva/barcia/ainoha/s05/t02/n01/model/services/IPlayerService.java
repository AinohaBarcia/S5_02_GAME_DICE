package cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.services;

import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.PlayerException;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.dto.GameDto;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.dto.PlayerDto;

import java.util.List;

public interface IPlayerService {
    PlayerDto createPlayer(PlayerDto playerDto);

    PlayerDto updatePlayer(PlayerDto playerDto, Long idPlayer);

    PlayerDto deltePlayerById(Long idPlayer) throws PlayerException;

    PlayerDto getPlayerById(Long idPlayer) throws PlayerException;

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
