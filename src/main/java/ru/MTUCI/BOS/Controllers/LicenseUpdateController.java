package ru.MTUCI.BOS.Controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.MTUCI.BOS.Requests.LicenseUpdateRequest;
import ru.MTUCI.BOS.Requests.Ticket;
import ru.MTUCI.BOS.Services.LicenseService;

import java.util.Objects;

@RestController
@RequestMapping("/license")
@RequiredArgsConstructor
public class LicenseUpdateController {

    private final LicenseService licenseService;

    @PostMapping("/update")
    public ResponseEntity<String> handleLicenseUpdate(@Valid @RequestBody LicenseUpdateRequest request, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String errorMessage = Objects.requireNonNull(validationResult.getFieldError()).getDefaultMessage();
            return ResponseEntity.ok("Validation error: " + errorMessage);
        }

        try {
            Ticket updatedTicket = licenseService.updateExistentLicense(
                    request.getLicenseCode(),
                    request.getLogin(),
                    request.getMacAddress()
            );

            return ResponseEntity.ok("License updated successfully. Ticket: " + updatedTicket.toString());
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.status(400).body(exception.getMessage());
        }
    }
}