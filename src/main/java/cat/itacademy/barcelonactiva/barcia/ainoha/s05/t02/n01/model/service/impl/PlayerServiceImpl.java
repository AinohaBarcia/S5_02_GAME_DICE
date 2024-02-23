package cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.service.impl;

import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.exceptions.GameException;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.exceptions.PlayerException;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.domain.Player;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.dto.GameDto;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.dto.PlayerDto;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.repository.IPlayerRepositori;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.service.IGameService;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.service.IPlayerService;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.service.PlayerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements IPlayerService {
    @Autowired
    private IPlayerRepositori iPlayerRepositori;
    @Autowired
    private IGameService iGameService;

    @Override
    public void createPlayer(PlayerDto playerDto) {
        if (playerDto.getName() == null || playerDto.getName().trim().isEmpty()) {
            playerDto.setName("Anonymous");
        }
        Player player = PlayerMapper.mapToPlayer(playerDto);
        Optional<Player> playerName = iPlayerRepositori.findByName(player.getName());
        if (playerName.isPresent()) {
            throw new PlayerException("This name has been created");
        }
        iPlayerRepositori.save(player);


    }


    public PlayerDto updatePlayer(PlayerDto newplayerDto, Long idPlayer) {
        Player newPlayer = PlayerMapper.mapToPlayer(newplayerDto);
        Optional<Player> player = iPlayerRepositori.findById(idPlayer);
        if (player.isPresent()) {
            Player updatedPlayer = player.get();
            if (newplayerDto.getName().isEmpty() || newplayerDto.getName().isBlank()) {
                updatedPlayer.setName("ANONYMOUS");
            } else {
                Optional<Player> playerName = iPlayerRepositori.findByName(newPlayer.getName());
                if (playerName.isPresent()) {
                    throw new PlayerException("This name is already created");
                } else {
                    updatedPlayer.setName(newPlayer.getName());
                }
            }
            iPlayerRepositori.save(updatedPlayer);
            return PlayerMapper.mapToPlayerDto(updatedPlayer);
        } else {
            throw new PlayerException("The player with id " + idPlayer + " doesn't exist");
        }
    }


    @Override
    public PlayerDto deltePlayerById(Long id) throws PlayerException {
        return iPlayerRepositori.findById(id).map(player -> {
            iPlayerRepositori.deleteById(id);
            System.out.println("Player with id: " + id + " is deleted");
            return PlayerMapper.mapToPlayerDto(player);
        }).orElseThrow(() -> new GameException("Player with id " + id + " not found"));

    }

    @Override
    public PlayerDto getPlayerById(Long id) throws PlayerException {
        return PlayerMapper.mapToPlayerDto(iPlayerRepositori.findById(id).orElseThrow(() -> new GameException("Player with id " + id + " not found")));
    }

    @Override
    public List<PlayerDto> getAllPlayers() {
        List<Player> playerList = iPlayerRepositori.findAll();
        return playerList.stream().map(PlayerMapper::mapToPlayerDto).collect(Collectors.toList());
    }

    @Override
    public void deleteGames(Long idPlayer) {
        PlayerDto playerDto = getPlayerById(idPlayer);
        iGameService.deleteAllGames(PlayerMapper.mapToPlayer(playerDto));
        restartAverage(playerDto);
        iPlayerRepositori.save(PlayerMapper.mapToPlayer(playerDto));
    }

    @Override
    public void restartAverage(PlayerDto playerDto) {
        playerDto.setGamesLost(0);
        playerDto.setCalculateSuccessRate(0);
        playerDto.setCalculateLostRate(0);
        playerDto.setGamesWin(0);
    }

    @Override
    public double calculateSuccessRate(int totalGames, int wins) {
        if (totalGames == 0) {
            return 0;
        }
        return (double) wins / totalGames * 100;
    }

    @Override
    public GameDto play(Long idPlayer) {
        PlayerDto playerDto = getPlayerById(idPlayer);
        Player player = PlayerMapper.mapToPlayer(playerDto);
        GameDto gameDto = iGameService.createGame(player);
        updateResultGame(gameDto, PlayerMapper.mapToPlayerDto(player));
        return gameDto;
    }

    @Override
    public List<GameDto> getAllGames(Long idPlayer) {
        PlayerDto playerDto = getPlayerById(idPlayer);
        Player player = PlayerMapper.mapToPlayer(playerDto);
        return iGameService.getAllGames(player);
        }

    @Override
    public void updateResultGame(GameDto gameDto, PlayerDto playerDto) {
        playerDto.setGamesLost(playerDto.getGamesLost() + 1);
        double newSuccessRate = calculateSuccessRate(playerDto.getGamesWin(), playerDto.getGamesLost());
        playerDto.setCalculateSuccessRate(newSuccessRate);
        iPlayerRepositori.save(PlayerMapper.mapToPlayer(playerDto));
    }
    @Override
    public double getAverageSuccesRate() {
        List<PlayerDto> players = getAllPlayers();
        return players.stream()
                .mapToDouble(PlayerDto::getCalculateSuccessRate)
                .average()
                .orElse(0.0);
    }
    @Override
    public PlayerDto getWorstWinnerPlayer() {
        List<PlayerDto> playerList = getAllPlayers();
        if (playerList.isEmpty()) {
            return null;
        } else {
            playerList.sort(Comparator.comparingDouble(PlayerDto::getCalculateSuccessRate));
            return playerList.get(0);
        }
    }


    @Override
    public PlayerDto getBestWinnerPlayer() {
        List<PlayerDto> playerList = getAllPlayers();
        if (playerList.isEmpty()) {
            return null;
        }
        return playerList.stream()
                .max(Comparator.comparingDouble(PlayerDto::getCalculateSuccessRate))
                .orElse(null);
    }

    @Override
    public void updateGameWin(PlayerDto playerDto) {
        playerDto.setGamesWin(playerDto.getGamesWin() + 1);
        double newSuccessRate = calculateSuccessRate(playerDto.getGamesWin(), playerDto.getGamesLost());
        playerDto.setCalculateSuccessRate(newSuccessRate);
        iPlayerRepositori.save(PlayerMapper.mapToPlayer(playerDto));
    }

    @Override
    public void updateGameLost(PlayerDto playerDto) {
        playerDto.setGamesLost(playerDto.getGamesLost() + 1);
        double newSuccessRate = calculateSuccessRate(playerDto.getGamesWin(), playerDto.getGamesLost());
        playerDto.setCalculateSuccessRate(newSuccessRate);
        iPlayerRepositori.save(PlayerMapper.mapToPlayer(playerDto));
    }

}




