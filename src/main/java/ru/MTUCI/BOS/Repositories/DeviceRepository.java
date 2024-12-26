package ru.MTUCI.BOS.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.MTUCI.BOS.Requests.Device;

import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    Optional<Device> findDeviceByMacAddress(String macAddress);
    Optional<Device> findDeviceByMacAddressAndUser_Id(String macAddress, Long userId);
}