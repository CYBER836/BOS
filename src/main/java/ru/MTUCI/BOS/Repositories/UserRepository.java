package ru.MTUCI.BOS.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.MTUCI.BOS.Requests.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByLogin(String login);

    boolean existsByLogin(String login);
    boolean existsByEmail(String email);
}