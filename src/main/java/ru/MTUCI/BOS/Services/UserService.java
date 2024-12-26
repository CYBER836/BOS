package ru.MTUCI.BOS.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.MTUCI.BOS.Requests.User;
import ru.MTUCI.BOS.Repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    // Используем аннотацию для внедрения зависимости
    private final UserRepository userRepo;

    @Autowired
    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepo.findUserByLogin(username);
        if (!optionalUser.isPresent()) {
            throw new UsernameNotFoundException("Пользователь с логином '" + username + "' не найден");
        }
        return optionalUser.get(); // Предполагаем, что ваш класс User реализует интерфейс UserDetails
    }

    public void addNewUser(User user) {
        userRepo.save(user);
    }

    public User fetchUserByLogin(String login) {
        return userRepo.findUserByLogin(login).orElse(null);
    }

    public User fetchUserById(Long id) {
        return userRepo.findById(id).orElse(null);
    }

    public List<User> fetchAllUsers() {
        return userRepo.findAll();
    }

    public void removeUser(Long id) {
        userRepo.deleteById(id);
    }

    public boolean checkIfLoginExists(String login) {
        return userRepo.existsByLogin(login);
    }

    public boolean checkIfEmailExists(String email) {
        return userRepo.existsByEmail(email);
    }
}