package ru.MTUCI.BOS.Controllers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.MTUCI.BOS.Requests.*;
import ru.MTUCI.BOS.Requests.LicenseRequest;
import ru.MTUCI.BOS.Services.LicenseService;

@RestController
@RequestMapping("/license")
public class LicenseController {

    private final LicenseService licenseService;

    @Autowired
    public LicenseController(LicenseService licenseService) {
        this.licenseService = licenseService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> createLicense(@Valid @RequestBody LicenseRequest licenseRequest) {
        System.out.println("LicenseController: createLicense: Started creating license, data: " + licenseRequest.getDescription());

        try {
            License license = licenseService.createLicense(licenseRequest);

            if (license == null) {
                return ResponseEntity.badRequest().body("Failed to create license");
            }
            return ResponseEntity.ok("License created successfully, License:\n" + license.getBody());

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}