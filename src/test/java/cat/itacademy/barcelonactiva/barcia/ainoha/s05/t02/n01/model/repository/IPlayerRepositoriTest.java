package cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.repository;

import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.domain.Player;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DataJpaTest
class IPlayerRepositoriTest {
    @Autowired
    private IPlayerRepositori iPlayerRepositori;
    @Test
    void testFindByName() {

        Player player = new Player();
        iPlayerRepositori.save(player);


        Optional<Player> foundPlayer = iPlayerRepositori.findByName("TestPlayer");


        assertTrue(foundPlayer.isPresent());
        assertEquals("TestPlayer", foundPlayer.get().getName());
    }
}