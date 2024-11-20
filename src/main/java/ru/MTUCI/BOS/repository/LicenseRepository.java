package ru.MTUCI.BOS.repository;
import ru.MTUCI.BOS.User.License;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LicenseRepository extends JpaRepository<License, Long> {
}
