package ru.MTUCI.BOS.Controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerUser(@Valid @RequestBody RegistrationUserTransferObject userDTO,
                                                            BindingResult bindingResult) {
        Map<String, Object> response = new HashMap<>();

        if (bindingResult.hasErrors()) {
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            response.put("error", "Ошибка валидации: " + errorMessage);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (userService.checkIfLoginExists(userDTO.getLogin())) {
            response.put("error", "Ошибка валидации: пользователь с таким логином уже существует");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (userService.checkIfEmailExists(userDTO.getEmail())) {
            response.put("error", "Ошибка валидации: пользователь с такой электронной почтой уже существует");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setLogin(userDTO.getLogin());
        user.setPasswordHash(passwordEncoder.encode(userDTO.getPassword()));
        user.setEmail(userDTO.getEmail());
        user.setRole(ROLE.ROLE_USER);

        userService.addNewUser(user);

        String token = jwtUtil.generateToken(userDetailsService.loadUserByUsername(user.getLogin()));
        response.put("token", token);
        response.put("message", "Регистрация прошла успешно!");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> authenticateUser(@Valid @RequestBody LoginUserTransferObject userDTO,
                                                                BindingResult bindingResult) {
        Map<String, Object> response = new HashMap<>();

        if (bindingResult.hasErrors()) {
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            response.put("error", "Ошибка валидации: " + errorMessage);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userDTO.getLogin(),
                    userDTO.getPassword()
            ));
        } catch (DisabledException e) {
            response.put("error", "Аккаунт заблокирован");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        } catch (BadCredentialsException e) {
            response.put("error", "Неверный логин или пароль");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(userDTO.getLogin());
        final String token = jwtUtil.generateToken(userDetails);

        response.put("token", token);
        response.put("message", "Вы вошли в систему");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}