package cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.repository;

import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.domain.Player;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class IPlayerRepositoriTest {
    @Autowired
    private IPlayerRepositori iPlayerRepositori;

    @Test
    void testCreatePlayer(){
        Player savePlayer = iPlayerRepositori.save(new Player("TestPlayer"));
        assertThat(savePlayer.getId()).isGreaterThan(0);

    }

}