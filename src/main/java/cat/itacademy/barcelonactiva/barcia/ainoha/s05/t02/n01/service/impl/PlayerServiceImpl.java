package cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.service.impl;

import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.exceptions.PlayerAlreadyExistsException;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.exceptions.PlayerNotFoundException;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.domain.Game;import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.domain.Player;
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
    private final IPlayerRepositori iPlayerRepositori;
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
            throw new PlayerAlreadyExistsException("This player has been created");
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
            playerName.ifPresent(existingPlayer -> {
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

    @Override
    public List<Player> getAllPlayers() {
        return iPlayerRepositori.findAll();
    }

    @Override
    public void deleteAllGames(Long idPlayer) {
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
        return ((double) wins / totalGames) * 100;
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

        if (playerSerch.isPresent()) {
            Player player = playerSerch.get();
            GameDto gameDto = iGameService.createGame(player);

            if (gameDto.isWin()) {
                updateGameWin(player.getIdPlayer());
            } else {
                updateGameLost(player.getIdPlayer());
            }
            iPlayerRepositori.save(player);

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
    public double getAverageSuccessRate() {
        List<Player> players = iPlayerRepositori.findAll();

        if (players.isEmpty()) {
            throw new PlayerNotFoundException("No games played.");
        }
        double totalSuccessRate = players.stream()
                .mapToDouble(Player::getCalculateSuccessRate)
                .average()
                .orElse(0.0);

        return totalSuccessRate;
    }

    @Override
    public PlayerDto getWorstWinnerPlayer() {
        List<PlayerDto> playerList = iPlayerRepositori.findAll().stream()
                .map(PlayerMapper::mapToPlayerDto)
                .sorted(Comparator.comparingDouble(PlayerDto::getCalculateSuccessRate))
                .collect(Collectors.toList());

        if (playerList.isEmpty()) {
            throw new PlayerNotFoundException("No players found.");
        }

        return playerList.get(playerList.size() - 1);
    }

    @Override
    public PlayerDto getBestWinnerPlayer() {
            List<PlayerDto> playerList = iPlayerRepositori.findAll().stream()
                    .map(PlayerMapper::mapToPlayerDto)
                    .sorted(Comparator.comparingDouble(PlayerDto::getCalculateSuccessRate).reversed())
                    .collect(Collectors.toList());

            if (playerList.isEmpty()) {
                throw new PlayerNotFoundException("No players found.");
            }

            return playerList.get(0);
        }
        @Override
        public void updateGameWin (Long idPlayer){
            Optional<Player> playerSearch = iPlayerRepositori.findById(idPlayer);
            if (playerSearch.isPresent()) {
                Player player = playerSearch.get();
                player.setGamesWin(player.getGamesWin() + 1);
                double newSuccessRate = ((double) player.getGamesWin() / player.getGameList().size()) * 100;
                player.setCalculateSuccessRate(newSuccessRate);
                iPlayerRepositori.save(player);
            } else {
                throw new PlayerNotFoundException("Player doesn't exist");
            }
        }

        @Override
        public void updateGameLost (Long idPlayer){
            Optional<Player> playerSearch = iPlayerRepositori.findById(idPlayer);
            if (playerSearch.isPresent()) {
                Player player = playerSearch.get();
                player.setGamesLost(player.getGamesLost() + 1);

                // Calcular la tasa de fracaso solo si la lista de juegos no es nula
                if (player.getGameList() != null) {
                    double newLostRate = 100 - (((double) player.getGamesLost() / player.getGameList().size()) * 100.0);
                    player.setCalculateLostRate(newLostRate);
                }

                iPlayerRepositori.save(player);
            } else {
                throw new PlayerNotFoundException("Player doesn't exist");
            }
        }
    }






