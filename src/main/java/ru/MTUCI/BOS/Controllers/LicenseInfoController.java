package ru.MTUCI.BOS.Controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.MTUCI.BOS.Requests.LicenseInfoRequest;
import ru.MTUCI.BOS.Requests.Device;
import ru.MTUCI.BOS.Requests.License;
import ru.MTUCI.BOS.Requests.Ticket;
import ru.MTUCI.BOS.Requests.User;
import ru.MTUCI.BOS.Services.DeviceService;
import ru.MTUCI.BOS.Services.LicenseService;
import ru.MTUCI.BOS.Services.UserService;

import java.util.Objects;

@RestController
@RequestMapping("/license")
@RequiredArgsConstructor
public class LicenseInfoController {

    private final DeviceService deviceService;
    private final LicenseService licenseService;
    private final UserService userService;

    @GetMapping("/info")
    public ResponseEntity<?> getLicenseInfo(@Valid @RequestBody LicenseInfoRequest licenseInfoRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            String errMsg = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            return ResponseEntity.status(200).body("Ошибка валидации: " + errMsg);
        }

        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findUserByLogin(authentication.getName());

            //Device device = deviceService.getDeviceByInfo(licenseInfoRequest.getMacAddress(), user);
            Device device = deviceService.getDeviceByMacAddress(licenseInfoRequest.getMacAddress());

            if(!Objects.equals(device.getUser().getId(), user.getId())){
                throw new IllegalArgumentException("Ошибка аутентификации: неверный пользователь");
            }

            if(device == null){
                return ResponseEntity.status(404).body("Ошибка: устройство не найдено");
            }

            License activeLicense = licenseService.getActiveLicenseForDevice(device, user, licenseInfoRequest.getLicenseCode());

            Ticket ticket = licenseService.generateTicket(activeLicense, device);

            return ResponseEntity.status(200).body("Лицензия найдена, Тикет: " + ticket.toString());

        } catch (Exception e){
            return ResponseEntity.status(500).body("Внутренняя ошибка сервера: " + e.getMessage());
        }
    }

}
