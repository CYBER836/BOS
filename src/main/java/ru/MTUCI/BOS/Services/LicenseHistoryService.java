package ru.MTUCI.BOS.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.MTUCI.BOS.Requests.LicenseHistory;
import ru.MTUCI.BOS.Repositories.LicenseHistoryRepository;

import java.util.List;

@Service
public class LicenseHistoryService {

    private final LicenseHistoryRepository licenseHistoryRepository;

    @Autowired
    public LicenseHistoryService(LicenseHistoryRepository licenseHistoryRepository) {
        this.licenseHistoryRepository = licenseHistoryRepository;
    }

    public void saveLicenseHistory(LicenseHistory licenseHistory) {
        licenseHistoryRepository.save(licenseHistory);
    }

    public List<LicenseHistory> getAllLicenseHistories() {
        return licenseHistoryRepository.findAll();
    }

    public LicenseHistory getLicenseHistoryById(Long id) {
        return licenseHistoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Запись с id " + id + " не найдена"));
    }

    public void deleteLicenseHistoryById(Long id) {
        LicenseHistory licenseHistory = getLicenseHistoryById(id);
        licenseHistoryRepository.delete(licenseHistory);
    }
}