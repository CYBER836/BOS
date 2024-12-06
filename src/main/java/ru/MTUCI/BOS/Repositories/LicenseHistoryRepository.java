package ru.MTUCI.BOS.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.MTUCI.BOS.Requests.LicenseHistory;

@Repository
public interface LicenseHistoryRepository extends JpaRepository<LicenseHistory, Long> {
    LicenseHistory findLicenseHistoryById(Long id);
}