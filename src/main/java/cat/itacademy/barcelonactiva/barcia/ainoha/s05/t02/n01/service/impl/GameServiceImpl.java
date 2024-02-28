package cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.service.impl;

import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.domain.Player;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.domain.Game;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.dto.GameDto;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.repository.IGameRepositori;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.service.GameMapper;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.service.IGameService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;



@Service
@RequiredArgsConstructor
public class GameServiceImpl implements IGameService {
    @Autowired
    private IGameRepositori iGameRepositori;

    @Override
    public GameDto createGame(Player player) {
        GameDto gameDto = new GameDto();
        Game game = GameMapper.mapToGame(gameDto,player);
        game.setPlayer(player);
        game.setWin(gameDto.isGameWin());
        iGameRepositori.save(game);
        return gameDto;
    }

    @Override
    public void deleteGame(Long idGame) {
        iGameRepositori.deleteById(idGame);
    }

    @Override
    public List<GameDto> getAllGames(Player player) {
        List<Game>gameList = player.getGameList();
        return gameList.stream().map(GameMapper::mapToGameDto).collect(Collectors.toList());
    }


}
