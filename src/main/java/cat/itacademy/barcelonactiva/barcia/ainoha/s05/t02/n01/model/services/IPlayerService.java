package cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.services;

import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.PlayerException;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.dto.GameDto;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.dto.PlayerDto;

import java.util.List;

public interface IPlayerService {
    PlayerDto createPlayer(PlayerDto playerDto);

    PlayerDto updatePlayer(PlayerDto playerDto, Long id);

    PlayerDto deltePlayerById(Long id) throws PlayerException;

    PlayerDto getPlayerById(Long id) throws PlayerException;

    List<PlayerDto> getAllPlayers();

    void updateResultGame (GameDto gameDto,PlayerDto playerDto);
    public GameDto play (Long id);
    List<GameDto> getAllGames(Long id);

    void deleteGames(Long id);

    double calculateSuccessRate();

    double calculateSuccessRate(List<PlayerDto> playerDtoList);

    double calculateSuccessRate(int totalGames, int wins);
    List<PlayerDto>getRanking();
    public double calculateAverageSuccesRate();
    public PlayerDto getLoser();
    public PlayerDto getWiner();
    double calculateLostRate(PlayerDto playerDto);
    void restartAverage(PlayerDto playerDto);


}
