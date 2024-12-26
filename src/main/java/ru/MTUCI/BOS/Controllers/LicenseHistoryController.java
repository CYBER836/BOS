package ru.MTUCI.BOS.Controllers;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.MTUCI.BOS.Requests.LicenseHistory;
import ru.MTUCI.BOS.Services.LicenseHistoryService;

import java.util.List;

@PreAuthorize("hasRole('ROLE_ADMIN')")
@RestController
@RequestMapping("/license-histories")
@AllArgsConstructor
public class LicenseHistoryController {

    private final LicenseHistoryService licenseHistoryService;

    @GetMapping
    public ResponseEntity<List<LicenseHistory>> getAllLicenseHistories() {
        List<LicenseHistory> histories = licenseHistoryService.getAllLicenseHistories();
        return ResponseEntity.ok(histories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LicenseHistory> getLicenseHistoryById(@PathVariable @NotNull Long id) {
        LicenseHistory history = licenseHistoryService.getLicenseHistoryById(id);
        if (history == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(history);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLicenseHistoryById(@PathVariable @NotNull Long id) {
        licenseHistoryService.deleteLicenseHistoryById(id);
        return ResponseEntity.noContent().build();
    }
}