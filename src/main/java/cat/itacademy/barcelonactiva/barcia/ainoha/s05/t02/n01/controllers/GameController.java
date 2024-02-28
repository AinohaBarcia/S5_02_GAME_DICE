package cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.controllers;

import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.domain.Player;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.dto.GameDto;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.dto.PlayerDto;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.service.IPlayerService;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.service.PlayerMapper;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class GameController {
    @Autowired
    private IPlayerService iPlayerService;


    @PostMapping("/createPlayer")
    public ResponseEntity<String> createPlayer(@RequestBody PlayerDto playerDto) {
        if(playerDto.getName()==null|| playerDto.getName().isEmpty()){
            playerDto.setName(null);
        }
        iPlayerService.createPlayer(playerDto);
        return new ResponseEntity<>("Player is created",HttpStatus.CREATED);
    }
    @GetMapping("/getAllPlayers")
    public ResponseEntity<List<Player>> getAllPlayers() {
       List<Player> players=iPlayerService.getAllPlayers();
       if (players.isEmpty()){
           return new ResponseEntity<>(HttpStatus.NO_CONTENT);
       }
           return new ResponseEntity<>(players,HttpStatus.OK);
       }


    @PutMapping("/updatePlayerById/{idPlayer}") //TODO cazar exceptcion si nno pones body
    public ResponseEntity<String> updatePlayerById(@PathVariable(value = "idPlayer") Long idPlayer,
                                                   @RequestBody PlayerDto newPlayerDto) {
        PlayerDto playerDto = iPlayerService.updatePlayer(newPlayerDto, idPlayer);
        if (playerDto != null) {
            return new ResponseEntity<>("Player was updated", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Player not fourn", HttpStatus.NOT_FOUND);
        }
    }
   @PostMapping("/{idGame}/games")
   public ResponseEntity<GameDto> play(@PathVariable("idGame") Long idGame) {
       GameDto newGame = iPlayerService.play(idGame);
       return new ResponseEntity<>(newGame, HttpStatus.OK);
   }
    @DeleteMapping("/deleteAllGames/{idPlayer}")
    public ResponseEntity<String> deleteAllGames(@PathVariable("idPlayer") Long idPlayer) {
       iPlayerService.deleteAllGames(idPlayer);
        return new ResponseEntity<>("Games deleted successfully.", HttpStatus.OK);
    }

    @GetMapping("/getGames/{idGame}")
    public ResponseEntity<List<GameDto>> getAllGames(@PathVariable("idGame") Long idGame) {
        List<GameDto> gamesDTO = iPlayerService.getAllGames(idGame);
        if (gamesDTO.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(gamesDTO, HttpStatus.OK);
        }
    }

    @DeleteMapping("/deletePlayerById/{idPlayer}")
    public ResponseEntity<String> deletePlayerById(@PathVariable ("idPlayer")Long idPlayer) {
        iPlayerService.deltePlayerById(idPlayer);
        return new ResponseEntity<>("Player was deleted", HttpStatus.OK);
    }

    @GetMapping("/getRanking")
    public ResponseEntity<List<PlayerDto>>getAverageSuccesRate() {
        List<PlayerDto> playersRanking = iPlayerService.getAverageSuccesRate();
        if(playersRanking.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(playersRanking,HttpStatus.OK) ;
    }
    }

    @GetMapping("/getBestWinnerPlayer")
    public ResponseEntity<PlayerDto> getBestWinnerPlayer() {
        PlayerDto bestPlayer = iPlayerService.getBestWinnerPlayer();
        if (bestPlayer == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(bestPlayer, HttpStatus.OK);
        }
    }
    @GetMapping("/getWorstWinnerPlayer")
    public ResponseEntity<PlayerDto> getWorstWinnerPlayer() {
        PlayerDto worstPlayer = iPlayerService.getWorstWinnerPlayer();
        if (worstPlayer == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(worstPlayer, HttpStatus.OK);
        }
    }

}
