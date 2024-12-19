package ru.MTUCI.BOS.Controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.MTUCI.BOS.Requests.TransferObject.LoginUserTransferObject;
import ru.MTUCI.BOS.Requests.TransferObject.RegistrationUserTransferObject;
import ru.MTUCI.BOS.Requests.ENUMS.ROLE;
import ru.MTUCI.BOS.Requests.User;
import ru.MTUCI.BOS.Services.UserService;
import ru.MTUCI.BOS.Utils.JwtUtil;

import java.util.Objects;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;;

    @PostMapping("/register")
    public ResponseEntity<?> userRegistration(@Valid @RequestBody RegistrationUserTransferObject userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errMsg = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            return ResponseEntity.badRequest().body("Ошибка валидации: " + errMsg);
        }

        if(userService.existsByLogin(userDTO.getLogin())){
            return ResponseEntity.status(400).body("Ошибка валидации: пользователь с этим логином уже существует");
        }

        if(userService.existsByEmail(userDTO.getEmail())){
            return ResponseEntity.status(400).body("Ошибка валидации: пользователь с такой почтой уже существует");
        }

        User user = new User(userDTO.getLogin(), passwordEncoder.encode(userDTO.getPassword()), userDTO.getEmail(), ROLE.ROLE_USER, null);
        userService.saveUser(user);

        String token = jwtUtil.generateToken(userService.loadUserByUsername(user.getUsername()));

        return ResponseEntity.status(200).body("Регистрация пройдена, Токен: " + token);
    }

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@Valid @RequestBody LoginUserTransferObject userDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            String errMsg = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            return ResponseEntity.status(200).body("Ошибка валидации: " + errMsg);
        }

        User user = userService.findUserByLogin(userDTO.getLogin());

        if(user == null){
            return ResponseEntity.status(400).body("Ошибка валидации: пользователь не найден");
        }

        if(!passwordEncoder.matches(userDTO.getPassword(), user.getPassword())){
            return ResponseEntity.status(400).body("Ошибка валидации: пароль неверный");
        }

        String token = jwtUtil.generateToken(userService.loadUserByUsername(user.getUsername()));
        return ResponseEntity.status(200).body("Логин пройден, Токен: " + token);
    }
}
