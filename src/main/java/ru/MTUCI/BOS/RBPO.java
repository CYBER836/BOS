package ru.MTUCI.BOS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RBPO {

	public static void main(String[] args) {
		SpringApplication.run(RBPO.class, args);
	}
}

//Сущности - классы с методами и полями
//Связь один ко многим - у одного объекта множество связей
//Репозитории - интерфейс для работы с БД
//Контроллеры - для работы с HTTP запросами
//Сервис прослойка между контроллером и репозиторием
//product - на что лицензия(телефон,пк и т.д)
//device - устройство того,кто активирует