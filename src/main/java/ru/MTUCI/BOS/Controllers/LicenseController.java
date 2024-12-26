package ru.MTUCI.BOS.Controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.MTUCI.BOS.Requests.LicenseRequest;
import ru.MTUCI.BOS.Services.LicenseService;

@RestController
@RequestMapping("/licenses")
public class LicenseController {

    private final LicenseService licenseService;

    @Autowired
    public LicenseController(LicenseService licenseService) {
        this.licenseService = licenseService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<String> createLicense(@Valid @RequestBody LicenseRequest licenseRequest) {
        System.out.printf("Creating a new license with description: %s%n", licenseRequest.getDescription());

        try {
            String licenseBody = String.valueOf(licenseService.createLicense(licenseRequest));
            if (licenseBody != null) {
                return ResponseEntity.ok("License created successfully.\nLicense body:\n" + licenseBody);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("An error occurred while creating the license.");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}