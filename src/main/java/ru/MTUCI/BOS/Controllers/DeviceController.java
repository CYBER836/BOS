package ru.MTUCI.BOS.Controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.MTUCI.BOS.Requests.DeviceRequest;
import ru.MTUCI.BOS.Requests.Device;
import ru.MTUCI.BOS.Services.DeviceService;

import java.util.List;
import java.util.Objects;

@PreAuthorize("hasRole('ROLE_ADMIN')")
@RestController
@RequestMapping("/devices")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class DeviceController {

    private final DeviceService deviceService;

    @PostMapping
    public ResponseEntity<String> createDevice(@Valid @RequestBody DeviceRequest request, BindingResult result) {
        if (result.hasErrors()) {
            String errorMessage = Objects.requireNonNull(result.getFieldError()).getDefaultMessage();
            return ResponseEntity.badRequest().body(errorMessage);
        }

        Device createdDevice = deviceService.createDevice(request);
        return ResponseEntity.ok(createdDevice.toString());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Device> getDeviceById(@PathVariable Long id) {
        Device device = deviceService.getDeviceById(id);
        return ResponseEntity.ok(device);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateDevice(@PathVariable Long id, @Valid @RequestBody DeviceRequest request, BindingResult result) {
        if (result.hasErrors()) {
            String errorMessage = Objects.requireNonNull(result.getFieldError()).getDefaultMessage();
            return ResponseEntity.badRequest().body(errorMessage);
        }

        Device updatedDevice = deviceService.updateDevice(id, request);
        return ResponseEntity.ok(updatedDevice.toString());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDevice(@PathVariable Long id) {
        deviceService.deleteDevice(id);
        return ResponseEntity.ok("Устройство с id: " + id + " успешно удалено");
    }

    @GetMapping
    public ResponseEntity<List<Device>> getAllDevices() {
        List<Device> devices = deviceService.getAllDevices();
        return ResponseEntity.ok(devices);
    }
}