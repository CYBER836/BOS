package ru.MTUCI.BOS.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.MTUCI.BOS.User.License;
import ru.MTUCI.BOS.repository.LicenseRepository;

import java.util.List;

@RestController
@RequestMapping("/licenses")
public class LicenseController {

    @Autowired
    private LicenseRepository licenseRepository;

    @PostMapping
    public License createLicense(@RequestBody License license) {
        return licenseRepository.save(license);
    }

    @GetMapping
    public List<License> getAllLicenses() {
        return licenseRepository.findAll();
    }

    @GetMapping("/{id}")
    public License getLicenseById(@PathVariable Long id) {
        return licenseRepository.findById(id).orElse(null);
    }
}
