package ru.MTUCI.BOS.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.MTUCI.BOS.Requests.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByLogin(String login);
    User getUserById(Long id);

    boolean existsByLogin(String login);
    boolean existsByEmail(String email);
}