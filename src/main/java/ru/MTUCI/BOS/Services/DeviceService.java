package ru.MTUCI.BOS.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.MTUCI.BOS.Requests.ActivationRequest;
import ru.MTUCI.BOS.Requests.DeviceRequest;
import ru.MTUCI.BOS.Requests.Device;
import ru.MTUCI.BOS.Requests.User;
import ru.MTUCI.BOS.Repositories.DeviceRepository;
import ru.MTUCI.BOS.Repositories.UserRepository;

import java.util.List;



@Service
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final UserRepository userRepository;

    public Device registerOrUpdateDevice(ActivationRequest activationRequest, User user) {

        Device device = deviceRepository.getDeviceByMacAddress(activationRequest.getMacAddress());
        if (device == null) {
            device = new Device();
            device.setMacAddress(activationRequest.getMacAddress());
            device.setUser(user);
        } else if (!device.getUser().equals(user)) {
            throw new IllegalArgumentException("Устройство зарегистрировано другим пользователем");
        }

        device.setName(activationRequest.getDeviceName());

        return deviceRepository.save(device);
    }

    public Device getDeviceByInfo(String macAddress, User user) {
        return deviceRepository.findDeviceByMacAddressAndUser(macAddress, user);
    }

    public Device createDevice(DeviceRequest deviceRequest) {
        User user = userRepository.findById(deviceRequest.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));
        Device device = new Device();
        device.setName(deviceRequest.getDeviceName());
        device.setMacAddress(deviceRequest.getMacAddress());
        device.setUser(user);
        return deviceRepository.save(device);
    }

    public Device getDeviceById(Long id) {
        return deviceRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Устройство не найдено"));
    }

    public Device updateDevice(Long id, DeviceRequest deviceRequest) {
        Device device = getDeviceById(id);
        User user = userRepository.findById(deviceRequest.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));
        device.setName(deviceRequest.getDeviceName());
        device.setMacAddress(deviceRequest.getMacAddress());
        device.setUser(user);
        return deviceRepository.save(device);
    }

    public void deleteDevice(Long id) {
        deviceRepository.deleteById(id);
    }

    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    public Device getDeviceByMacAddress(String macAddress) {
        return deviceRepository.findDeviceByMacAddress(macAddress);
    }
}