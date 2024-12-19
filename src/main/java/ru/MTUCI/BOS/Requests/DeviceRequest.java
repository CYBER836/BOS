package ru.MTUCI.BOS.Requests;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceRequest {

    @NotBlank(message = "Название устройства не может быть пустым")
    private String deviceName;

    @NotBlank(message = "MАС адрес не может быть пустым")
    private String macAddress;

    private Long userId;
}