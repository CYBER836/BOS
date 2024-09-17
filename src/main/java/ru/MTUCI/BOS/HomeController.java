package ru.MTUCI.BOS;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // Измените на @Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "MainPage"; // Возвращаемое имя шаблона без расширения
    }
}
