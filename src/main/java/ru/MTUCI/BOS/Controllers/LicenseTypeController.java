package ru.MTUCI.BOS.Controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.MTUCI.BOS.Requests.LicenseTypeRequest;
import ru.MTUCI.BOS.Requests.LicenseType;
import ru.MTUCI.BOS.Services.LicenseTypeService;

import java.util.List;
import java.util.Objects;

@PreAuthorize("hasRole('ROLE_ADMIN')")
@RestController
@RequestMapping("/license-types")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class LicenseTypeController {

    private final LicenseTypeService licenseTypeService;

    @PostMapping
    public ResponseEntity<LicenseType> createLicenseType(@Valid @RequestBody LicenseTypeRequest request, BindingResult result) {
        if (result.hasErrors()) {
            String message = Objects.requireNonNull(result.getFieldError()).getDefaultMessage();
            LicenseType licenseType = new LicenseType(); // Используем пустой конструктор
            licenseType.setName(message);                // Устанавливаем сообщение в качестве имени
            return ResponseEntity.badRequest().body(licenseType);
        }

        LicenseType licenseType = licenseTypeService.createLicenseType(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(licenseType);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LicenseType> getLicenseTypeById(@PathVariable Long id) {
        LicenseType licenseType = licenseTypeService.getLicenseTypeById(id);
        if (licenseType == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(licenseType);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LicenseType> updateLicenseType(@PathVariable Long id, @Valid @RequestBody LicenseTypeRequest request, BindingResult result) {
        if (result.hasErrors()) {
            String message = Objects.requireNonNull(result.getFieldError()).getDefaultMessage();
            return ResponseEntity.badRequest().body(new LicenseType(null, message));
        }

        LicenseType licenseType = licenseTypeService.updateLicenseType(id, request);
        return ResponseEntity.ok(licenseType);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLicenseTypeById(@PathVariable Long id) {
        licenseTypeService.deleteLicenseType(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<LicenseType>> getAllLicenseTypes() {
        List<LicenseType> licenseTypes = licenseTypeService.getAllLicenseTypes();
        return ResponseEntity.ok(licenseTypes);
    }
}