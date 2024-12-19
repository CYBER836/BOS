package ru.MTUCI.BOS.Requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {

    @NotBlank(message = "Название не может быть пустым")
    private String name;


    private boolean isBlocked;

}
