package cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.services.impl;

import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.GameException;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.domain.Player;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.domain.Game;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.dto.GameDto;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.dto.PlayerDto;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.repository.IGameRepositori;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.services.GameMapper;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.services.IGameService;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.services.PlayerMapper;
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
