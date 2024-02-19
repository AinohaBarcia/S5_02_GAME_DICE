package cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.controllers;

import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.GameException;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.dto.PlayerDto;
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
@RequestMapping("/api/index")
@RequiredArgsConstructor
public class PlayerController {

    @Autowired
    private IPlayerService iPlayerService;



    @GetMapping("/createPlayer")
    public String createPlayerForm(Model model) {
        PlayerDto playerObj = new PlayerDto();
        model.addAttribute("playerDto", playerObj);
        return "createPlayer";
    }




    @PostMapping("/addPlayer")
    public String savePlayer(@ModelAttribute("playerDto") PlayerDto playerDto) {
        iPlayerService.createPlayer(playerDto);
        return "redirect:/api/index/";
    }
    @GetMapping("/updateForm/{id}")
    public String updateFormPlayer(@PathVariable (value = "id") Long id, Model model){
        model.addAttribute("playerDTO",iPlayerService.getPlayerById(id));
        model.addAttribute("newPassword", iPlayerService.getPlayerById(id).getPassword());
        return "updatePlayer";
    }



    @GetMapping("/getOnePlayer/{id}")
    public String getOnePlayerById(@PathVariable(value = "id") Long id, Model model){
        PlayerDto playerDto=iPlayerService.getPlayerById(id);
        List<PlayerDto>playerDtoList = new ArrayList<>();
        playerDtoList.add(playerDto);
        model.addAttribute("playerDto", playerDtoList);
        return "players";
    }
    @GetMapping("/getOnePlayer/{name}")
    public String getOnePlayerByName(@PathVariable(value = "name") String name, Model model){
        PlayerDto playerDto=iPlayerService.getPlayerByName(name);
        List<PlayerDto>playerDtoList = new ArrayList<>();
        playerDtoList.add(playerDto);
        model.addAttribute("playerDto", playerDtoList);
        return "players";
    }
    @GetMapping("/player/bestRate")
    public String showPlayerSuccessRate(Model model) {
        List<PlayerDto> playerDto =iPlayerService.getAllPlayers();
        double playerSuccessRate = iPlayerService.calculateSuccessRate();
        model.addAttribute("playersSuccessRate", playerSuccessRate);
        return "hallOfFame";
    }


}
