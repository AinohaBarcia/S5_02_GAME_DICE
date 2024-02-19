package cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.controllers;

import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.domain.Game;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.dto.GameDto;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.dto.PlayerDto;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.repository.IGameRepositori;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.services.IGameService;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.services.IPlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/game")
@RequiredArgsConstructor
public class GameController {
    @Autowired
    private IPlayerService iPlayerService;

    @PostMapping("/createPlayer") =>ok =>ok
    public ResponseEntity<PlayerDto> createPlayer (@RequestBody PlayerDto playerDto){
        PlayerDto playerDto1= iPlayerService.createPlayer(playerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(playerDto1);
    }
    @GetMapping("/getAllPlayers")=>ok
    public ResponseEntity<List<PlayerDto>> getAllPlayers() {
        List<PlayerDto> playerDtoList = iPlayerService.getAllPlayers();
        if(playerDtoList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
        return new ResponseEntity<>(playerDtoList,HttpStatus.OK);
    }
    @PutMapping("/updatePlayerById/{id}")=>ok=>ok
    public ResponseEntity <String> updatePlayerById(@PathVariable(value = "id") Long id,
                                                    @RequestBody PlayerDto newPlayerDto) {
      PlayerDto playerDto =iPlayerService.updatePlayer(newPlayerDto,id);
        if (playerDto != null) {
            return new ResponseEntity<>("Player was updated", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Player not fourn", HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/{id}/games")  =>ok =>ok
    public ResponseEntity<GameDto>play(@PathVariable("id") Long id){
        GameDto  newGame=iPlayerService.play(id);
        return new ResponseEntity<>(newGame,HttpStatus.OK);
    }
    @GetMapping("/deleteAllGames/{id}")=>ok
    public ResponseEntity<String> deleteAllGames(@PathVariable("id") Long id) {
      iPlayerService.deleteGames(id);
      return  new ResponseEntity<>("Delete games succefully",HttpStatus.OK);
    }
    @GetMapping("/getGames/{id}")
    public ResponseEntity<List<GameDto>> getAllGames(@PathVariable("id") Long id){
        List<GameDto> gamesDTO =iPlayerService.getAllGames(id);
        if(gamesDTO.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(gamesDTO, HttpStatus.OK);
        }
    }
    @GetMapping("/deletePlayerById/{id}")     =>ok
    public String deletePlayerById(@PathVariable Long id) {
        return null;
    }
    @GetMapping("/ranking")    =ok
    public ResponseEntity <PlayerDto> getRanking (){
       return null;
     }
     @GetMapping("/ranking/loser")   =>ok
     public ResponseEntity<PlayerDto>getRankingLoser(){
        return null;
     }
     @GetMapping("/ranking/winner")  =>ok
     public ResponseEntity<PlayerDto>getRankingWinner(){
        return null;
     }
   /* @PostMapping("/updateGameById/{id}")
    public String updateGameById(@PathVariable(value = "id") Long id,
                                     @ModelAttribute("gameDto") GameDto gameDto, Model model) {
       iGameService.updatateGame(gameDto);

        return "/updateSucursal";
    }*/








   @GetMapping("/getOne/{id}")
    public String getOneGame(@PathVariable (value = "id") Long id, Model model){
       GameDto gameDTO= iGameService.getGamebyID(id);
        List<GameDto> gameDtoList = new ArrayList<>();
        gameDtoList.add(gameDTO);
        model.addAttribute("gameDtoListDTO", gameDtoList);
        return "games";

    }

    @GetMapping("/")
    public String getAllGames(Model model) {
        List<GameDto>gameDtoList = iGameService.getAllGames();
        model.addAttribute("gameDtoList",gameDtoList);
        return "games";
    }



}
