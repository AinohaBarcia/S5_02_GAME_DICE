package cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.service;

import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.exceptions.PlayerNotFoundException;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.domain.Player;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.dto.GameDto;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.dto.PlayerDto;

import java.util.List;
import java.util.Optional;

public interface IPlayerService {
    void createPlayer(PlayerDto playerDto);

    PlayerDto updatePlayer(PlayerDto playerDto, Long idPlayer);

    PlayerDto deltePlayerById(Long idPlayer) throws PlayerNotFoundException;

    PlayerDto getPlayerById(Long idPlayer) throws PlayerNotFoundException;

    List<Player> getAllPlayers();
    public GameDto play (Long idPlayer);
    List<GameDto> getAllGames(Long idPlayer);
    void deleteAllGames(Long idGame);
    double calculateSuccessRate(int totalGames,int wins);
    List<PlayerDto> getAverageSuccesRate();
    public PlayerDto getBestWinnerPlayer();
    PlayerDto getWorstWinnerPlayer();
    void restartAverage(PlayerDto playerDto);
    void updateGameWin(Long idPlayer);
    void updateGameLost (Long idPlayer);

}
