package ru.MTUCI.BOS.Controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.MTUCI.BOS.Requests.ActivationRequest;
import ru.MTUCI.BOS.Requests.*;
import ru.MTUCI.BOS.Services.*;

import java.util.Objects;

@RestController
@RequestMapping("/license")
@RequiredArgsConstructor
public class ActivationController {

    private final UserService userService;
    private final DeviceService deviceService;
    private final LicenseService licenseService;

    @PostMapping("/activate")
    public ResponseEntity<?> activateLicense(@Valid @RequestBody ActivationRequest activationRequest, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            String errMsg = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            return ResponseEntity.status(400).body("Ошибка валидации: " + errMsg);
        }

        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            User user = userService.fetchUserByLogin(authentication.getName());

            Device device = deviceService.registerOrUpdateDevice(activationRequest, user);

            String activationCode = activationRequest.getActivationCode();
            String userLogin = user.getLogin();
            Ticket ticket = licenseService.activateLicense(activationCode, device, userLogin);

            return ResponseEntity.status(200).body("Лицензия успешно активирована: " + ticket.toString());
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(400).body("Ошибка: " + e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(500).body("Ошибка сервера:" + e.getMessage());
        }
    }
}