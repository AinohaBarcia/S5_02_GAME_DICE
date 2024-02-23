package cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.service.impl;

import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.domain.Player;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.domain.Game;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.dto.GameDto;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.repository.IGameRepositori;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.service.GameMapper;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.service.IGameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;



@Service
@RequiredArgsConstructor
public class GameServiceImpl implements IGameService {

    private IGameRepositori iGameRepositori;

    @Override
    public GameDto createGame(Player player) {
        GameDto gameDto = new GameDto();
        Game game = GameMapper.mapToGame(gameDto,player);
        iGameRepositori.save(game);
        return GameMapper.mapToGameDto(game);
    }
    @Override
    public void deleteAllGames (Player player){
        List<Game>gameList=player.getGameList();
        gameList.forEach(gameList::remove);

    }

    @Override
    public List<GameDto> getAllGames(Player player) {
        List<Game>gameList = player.getGameList();
        return gameList.stream().map(GameMapper::mapToGameDto).collect(Collectors.toList());
    }


}
