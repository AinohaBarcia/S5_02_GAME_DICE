package cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.services.impl;

import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.GameException;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.PlayerException;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.domain.Player;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.dto.GameDto;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.dto.PlayerDto;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.repository.IPlayerRepositori;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.services.IGameService;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.services.IPlayerService;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.services.PlayerMapper;
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
    public PlayerDto createPlayer(PlayerDto playerDto) {
        if (playerDto.getName().isBlank() || playerDto.getName().isBlank()) {
            playerDto.setName("Anonymous");
            Player player = PlayerMapper.mapToPlayer(playerDto);
            iPlayerRepositori.save(player);
        } else {
            Player player = PlayerMapper.mapToPlayer(playerDto);
            Optional<Player> playerName = iPlayerRepositori.findByName(player.getName());
            if (playerName.isPresent()) {
                throw new PlayerException("This name has been created");
            } else {
                iPlayerRepositori.save(player);
            }
        }
        return playerDto;
    }

    public PlayerDto updatePlayer(PlayerDto newplayerDto, Long id) {
        Player newPlayer = PlayerMapper.mapToPlayer(newplayerDto);
        Optional<Player> player = iPlayerRepositori.findById(id);
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
            throw new PlayerException("Te player with id " + id + " doesn't exist");
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
    public void deleteGames(Long id) {
        PlayerDto playerDto = getPlayerById(id);
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
    public double calculateSuccessRate() {
        return getAllPlayers().stream().mapToDouble(PlayerDto::getCalculateSuccessRate).sum();
    }

    @Override
    public double calculateAverageSuccesRate() {
        return 0;
    }

    @Override
    public double calculateSuccessRate(List<PlayerDto> playerDtoList) {
        int totalGames = 0;
        int totalWins = 0;
        List<Player> playerList = iPlayerRepositori.findAll();
        for (PlayerDto playerDto : playerDtoList) {
            totalGames += playerDto.getGameList().size();
            totalWins += playerDto.getGamesWin();
        }
        return calculateSuccessRate(totalGames, totalWins);
    }
    @Override
    public double calculateSuccessRate(int totalGames, int wins) {
        if (totalGames == 0) {
            return 0;
        }
        return (double) wins / totalGames * 100;
    }

    @Override
    public GameDto play(Long id) {
        PlayerDto playerDto = getPlayerById(id);
        Player player = PlayerMapper.mapToPlayer(playerDto);
        GameDto gameDto = iGameService.createGame(player);
        updateResultGame(gameDto, PlayerMapper.mapToPlayerDto(player));
        return gameDto;
    }

    @Override
    public List<GameDto> getAllGames(Long id) {
        PlayerDto playerDto = getPlayerById(id);
        Player player = PlayerMapper.mapToPlayer(playerDto);
        return iGameService.getAllGames(player);
        }

    @Override
    public void updateResultGame(GameDto gameDto, PlayerDto playerDto) {
        if (gameDto.isGameWin()) {
            playerDto.setGamesWin(playerDto.getGamesWin() + 1);
            double newSuccessRate = calculateSuccessRate();
            playerDto.setCalculateSuccessRate(newSuccessRate);
        } else {
            playerDto.setGamesLost(playerDto.getGamesLost() + 1);
            double newLostRate = calculateLostRate(playerDto);
            playerDto.setCalculateLostRate(newLostRate);
            iPlayerRepositori.save(PlayerMapper.mapToPlayer(playerDto));
        }
        iPlayerRepositori.save(PlayerMapper.mapToPlayer(playerDto));
    }
    @Override
    public List<PlayerDto> getRanking() {
        return getAllPlayers().stream().sorted(Comparator.comparingDouble(PlayerDto::getCalculateSuccessRate)).toList();
    }
    @Override
    public double calculateLostRate(PlayerDto playerDto) {
        return 100 - playerDto.getCalculateSuccessRate();
    }
    @Override
    public PlayerDto getLoser() {
        return getRanking().stream().toList().get(getRanking().size());
    }

    @Override
    public PlayerDto getWiner() {
        return getRanking().stream().toList().get(0);
    }


}




