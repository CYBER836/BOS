package ru.MTUCI.BOS.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.MTUCI.BOS.Requests.LicenseType;

@Repository
public interface LicenseTypeRepository extends JpaRepository<LicenseType, Long> {
    public LicenseType getLicenseTypeById(Long licenseTypeId);
    public LicenseType findLicenseTypeById(Long id);
}