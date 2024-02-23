package cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.repository;


import cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
