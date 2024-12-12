package ru.MTUCI.BOS.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.MTUCI.BOS.Requests.DeviceLicense;
import ru.MTUCI.BOS.Requests.License;

import java.util.List;

@Repository
public interface DeviceLicenseRepository extends JpaRepository<DeviceLicense, Long> {
    DeviceLicense getDeviceLicenseByDeviceIdAndLicenseId(Long deviceId, Long licenseId);
    List<DeviceLicense> getDeviceLicensesByLicense(License license);

}