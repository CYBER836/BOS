package ru.MTUCI.BOS.repository;
import ru.MTUCI.BOS.User.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
