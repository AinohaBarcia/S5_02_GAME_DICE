package cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.controllers;

import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.dto.GameDto;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.dto.PlayerDto;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.service.IPlayerService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/game")
@RequiredArgsConstructor
public class GameController {
    @Autowired
    private IPlayerService iPlayerService;

    @Operation(summary = "Create new Player")
    @PostMapping("/createPlayer")
    public ResponseEntity<String> createPlayer(@RequestBody PlayerDto playerDto) {
         iPlayerService.createPlayer(playerDto);
        return new ResponseEntity<>("Player is created",HttpStatus.CREATED);
    }
    @Operation(summary = "Show All Players with their Success Rate")
    @GetMapping("/getAllPlayers")
    public ResponseEntity<List<PlayerDto>> getAllPlayers() {
        List<PlayerDto> playerDtoList = iPlayerService.getAllPlayers();
        if (playerDtoList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            playerDtoList.forEach(playerDto -> {
                int totalGames = playerDto.getGameList().size();
                int totalWins = playerDto.getGamesWin();
                double successRate = iPlayerService.calculateSuccessRate(totalGames, totalWins);
                playerDto.setCalculateSuccessRate(successRate);
            });
            return new ResponseEntity<>(playerDtoList, HttpStatus.OK);
        }
    }
    @Operation(summary = "Update Player")
    @PutMapping("/updatePlayerById/{idPlayer}")
    public ResponseEntity<String> updatePlayerById(@PathVariable(value = "idPlayer") Long idPlayer,
                                                   @RequestBody PlayerDto newPlayerDto) {
        PlayerDto playerDto = iPlayerService.updatePlayer(newPlayerDto, idPlayer);
        if (playerDto != null) {
            return new ResponseEntity<>("Player was updated", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Player not fourn", HttpStatus.NOT_FOUND);
        }
    }
    @Operation(summary = "Player plays Game")
    @PostMapping("/{id}/games")
    public ResponseEntity<GameDto> play(@PathVariable("idGame") Long idGame) {
        GameDto newGame = iPlayerService.play(idGame);
        return new ResponseEntity<>(newGame, HttpStatus.OK);
    }
    @Operation(summary = "Delete All Games of Player")
    @DeleteMapping("/deleteAllGames/{id}")
    public ResponseEntity<String> deleteAllGames(@PathVariable("id") Long id) {
        iPlayerService.deleteGames(id);
        return new ResponseEntity<>("Delete games succefully", HttpStatus.OK);
    }
    @Operation(summary = "Get All Games of Player")
    @GetMapping("/getGames/{id}")
    public ResponseEntity<List<GameDto>> getAllGames(@PathVariable("id") Long id) {
        List<GameDto> gamesDTO = iPlayerService.getAllGames(id);
        if (gamesDTO.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(gamesDTO, HttpStatus.OK);
        }
    }
    @Operation(summary="Delete player by id")
    @DeleteMapping("/deletePlayerById/{id}")
    public ResponseEntity<String> deletePlayerById(@PathVariable ("id")Long id) {
        iPlayerService.deltePlayerById(id);
        return new ResponseEntity<>("Player was deleted", HttpStatus.OK);
    }
    @Operation(summary = "Get Average Success Rate of All Players")
    @GetMapping("/getRanking")
    public ResponseEntity<Double>getAverageSuccesRate() {
        List<PlayerDto> players = iPlayerService.getAllPlayers();
        if (players.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            double averageSuccessRate = iPlayerService.getAverageSuccesRate();
            return ResponseEntity.ok(averageSuccessRate);
        }
    }
    @Operation(summary = "Get Winner - Player/s with highest score")
    @GetMapping("/getBestWinnerPlayer")
    public ResponseEntity<PlayerDto> getBestWinnerPlayer() {
        PlayerDto bestPlayer = iPlayerService.getBestWinnerPlayer();
        if (bestPlayer == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(bestPlayer, HttpStatus.OK);
        }
    }

    @Operation(summary = "Get Loser - Worst winner Player with score")
    @GetMapping("/getWorstWinnerPlayer/")
    public ResponseEntity<PlayerDto> getWorstWinnerPlayer() {
        PlayerDto worstPlayer = iPlayerService.getWorstWinnerPlayer();
        if (worstPlayer == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(worstPlayer, HttpStatus.OK);
        }
    }

}
