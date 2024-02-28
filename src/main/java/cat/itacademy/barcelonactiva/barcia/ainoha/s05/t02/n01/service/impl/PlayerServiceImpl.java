package cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.service.impl;

import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.exceptions.PlayerAlreadyExistsException;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.exceptions.PlayerNotFoundException;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.domain.Game;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.domain.Player;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.dto.GameDto;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.dto.PlayerDto;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.repository.IPlayerRepositori;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.service.IGameService;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.service.IPlayerService;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.service.PlayerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
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

        if (playerDto.getName() == null || playerDto.getName().isEmpty()) {
            playerDto.setName("Anonymous");
        }
        playerDto.setRegistrationDate(new Date());
        Player player = PlayerMapper.mapToPlayer(playerDto);
        Optional<Player> playerName = iPlayerRepositori.findByName(player.getName());
        if (playerName.isPresent()) {
            throw new PlayerAlreadyExistsException("This name has been created");
        }
        iPlayerRepositori.save(player);
    }
    @Override
    public PlayerDto updatePlayer(PlayerDto newplayerDto, Long idPlayer) {
        Player newPlayer = PlayerMapper.mapToPlayer(newplayerDto);
        Optional<Player> playerOptional = iPlayerRepositori.findById(idPlayer);
        Player player = playerOptional.orElseGet(() -> {
            throw new PlayerNotFoundException("The player with id " + idPlayer + " doesn't exist");
        });

            if (!newplayerDto.getName().isEmpty() || !newplayerDto.getName().isBlank()) {
                Optional<Player> playerName = iPlayerRepositori.findByName(newPlayer.getName());
                playerName.ifPresent(existingPlayer ->{
                    throw new PlayerAlreadyExistsException("This name is already created");
                });
                player.setName(newPlayer.getName());
            } else {
                player.setName("ANONYMOUS");
            }
            iPlayerRepositori.save(player);

            return PlayerMapper.mapToPlayerDto(player);
    }


    @Override
    public PlayerDto deltePlayerById(Long id) throws PlayerNotFoundException {
        return iPlayerRepositori.findById(id).map(player -> {
            iPlayerRepositori.deleteById(id);
            System.out.println("Player with id: " + id + " is deleted");
            return PlayerMapper.mapToPlayerDto(player);
        }).orElseThrow(() -> new PlayerNotFoundException("Player with id " + id + " not found"));

    }

    @Override
    public PlayerDto getPlayerById(Long id) throws PlayerNotFoundException {
        return PlayerMapper.mapToPlayerDto(iPlayerRepositori.findById(id).orElseThrow(() -> new PlayerNotFoundException("Player with id " + id + " not found")));
    }

  /*  @Override
    public List<PlayerDto> getAllPlayers() {
        List<PlayerDto> playerDtoList = getAllPlayersWithTotals();
        playerDtoList.forEach(playerDto -> {
            playerDto.setTotalGames(0);
            playerDto.setTotalWins(0);
        });
        return playerDtoList;
    }*/

   /* @Override
    public List<PlayerDto> getAllPlayersWithTotals() {
        List<PlayerDto> playerDtoList = getAllPlayers();
        playerDtoList.forEach(playerDto -> {
            int totalGames = playerDto.getGameList().size();
            int totalWins = playerDto.getGamesWin();
            playerDto.setTotalGames(totalGames);
            playerDto.setTotalWins(totalWins);
        });
        return playerDtoList;
    }*/
   @Override
   public List<Player> getAllPlayers() {
       return iPlayerRepositori.findAll();
   }
    @Override
    public void deleteAllGames(Long idPlayer) {
       /* Optional<Player> playerSearch =iPlayerRepositori.findById(idPlayer);
        if(playerSearch.isPresent()){
            Player player = playerSearch.get();
            iGameService.deleteGame(player.getIdPlayer());
            PlayerDto playerDto = restartAverage(PlayerMapper.mapToPlayerDto(player));
            iPlayerRepositori.save(PlayerMapper.mapToPlayer(playerDto));
        } else {
            throw new PlayerNotFoundException("The id: " + idPlayer + ", doesn't correspond to any player.");
        }*/
        PlayerDto playerDto = getPlayerById(idPlayer);
        restartAverage(playerDto);
        iGameService.deleteGame(idPlayer);


    }

    @Override
    public void restartAverage(PlayerDto playerDto) {
        if (playerDto != null) {
            playerDto.setGamesLost(0);
            playerDto.setCalculateSuccessRate(0);
            playerDto.setCalculateLostRate(0);
            playerDto.setGamesWin(0);
        }
    }
   @Override
   public double calculateSuccessRate(int totalGames, int wins) {
       if (totalGames == 0) {
           return 0;
       }
       return (double) (wins / totalGames) * 100;
   }
    public double calculatePlayerSuccessRate(Long playerId) {
        PlayerDto playerDto = getPlayerById(playerId);
        List<GameDto> playerGames = getAllGames(playerId);

        int totalGames = playerGames.size();
        int wins = 0;
        for (GameDto game : playerGames) {
            if (game.isWin()) {
                wins++;
            }
        }

        return calculateSuccessRate(totalGames, wins);
    }
    @Override
    public GameDto play(Long idPlayer) {
        Optional<Player> playerSerch = iPlayerRepositori.findById(idPlayer);
        if(playerSerch.isPresent()){
            Player player = playerSerch.get();
            GameDto gameDto = iGameService.createGame(player);
            if (gameDto.isWin()) {
                updateGameWin(player.getIdPlayer());
            } else {
                updateGameLost(player.getIdPlayer());
            }
            return gameDto;
        } else {
            throw new PlayerNotFoundException("This player doesn't exist");
        }
    }
    @Override
    public List<GameDto> getAllGames(Long idPlayer) {
        PlayerDto playerDto = getPlayerById(idPlayer);
        Player player = PlayerMapper.mapToPlayer(playerDto);
        return iGameService.getAllGames(player);
    }

    @Override
    public List<PlayerDto> getAverageSuccesRate() {
        List<Player> players = iPlayerRepositori.findAll();
        List<PlayerDto> playersRanking = new ArrayList<>();
        players.stream().toList().forEach(l -> playersRanking.add(PlayerMapper.mapToPlayerDto(l)));
        playersRanking.sort(Comparator.comparing(PlayerDto::getGamesWin));
        if(playersRanking.isEmpty()){
            throw new PlayerNotFoundException("No games played.");
        }
        return playersRanking;
    }


    @Override
    public PlayerDto getWorstWinnerPlayer() {
       /* List<Player> playerList = getAllPlayers();
        if (playerList.isEmpty()) {
            return null;
        } else {
            playerList.sort(Comparator.comparingDouble(PlayerDto::getCalculateSuccessRate));
            return playerList.get(0);
        }*/
        return getAverageSuccesRate().stream().toList().get(getAverageSuccesRate().size()-1);
    }


    @Override
    public PlayerDto getBestWinnerPlayer() {
        return getAverageSuccesRate().stream().toList().get(0);
    }

    @Override
    public void updateGameWin(Long idPlayer) {
        PlayerDto playerDto = getPlayerById(idPlayer);
        playerDto.setGamesWin(playerDto.getGamesWin() + 1);
        double newSuccessRate = calculateSuccessRate(playerDto.getGamesWin(), playerDto.getGamesLost());
        playerDto.setCalculateSuccessRate(newSuccessRate);
        iPlayerRepositori.save(PlayerMapper.mapToPlayer(playerDto));
    }

    @Override
    public void updateGameLost(Long idPlayer) {
        PlayerDto playerDto = getPlayerById(idPlayer);
        playerDto.setGamesLost(playerDto.getGamesLost() + 1);
        double newSuccessRate = calculateSuccessRate(playerDto.getGamesWin(), playerDto.getGamesLost());
        playerDto.setCalculateSuccessRate(newSuccessRate);
        iPlayerRepositori.save(PlayerMapper.mapToPlayer(playerDto));
    }

}




