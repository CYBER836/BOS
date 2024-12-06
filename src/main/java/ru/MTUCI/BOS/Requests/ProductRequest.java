package ru.MTUCI.BOS.Requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {

    @NotBlank(message = "Name cannot be empty")
    private String name;


    private boolean isBlocked;

}
