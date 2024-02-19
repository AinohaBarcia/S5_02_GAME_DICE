package cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.repository;

import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.domain.Game;
import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.domain.Player;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class IGameRepositoriTest {

    @Autowired
    private IGameRepositori iGameRepositori;
    /*
    @Test
    void testCreateGame() {
        Player player = new Player();

        player= IPlayerRepositori.<Player>save(player);
        Game saveGame = iGameRepositori.save(new Game(player, 3, 5));
        assertThat(saveGame.getId()).isGreaterThan(0);
    }*/
}