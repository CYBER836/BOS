package ru.MTUCI.BOS.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.MTUCI.BOS.Requests.Device;
import ru.MTUCI.BOS.Requests.User;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    Device getDeviceByMacAddress(String macAddress);
    Device findDeviceByMacAddressAndUser(String macAddress, User user);
    Device findDeviceByMacAddress(String macAddress);
    Device findDeviceByUser(User user);
}