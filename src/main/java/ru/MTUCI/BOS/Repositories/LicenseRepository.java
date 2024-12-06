package ru.MTUCI.BOS.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.MTUCI.BOS.Requests.*;

import java.util.List;

@Repository
public interface LicenseRepository extends JpaRepository<License, Long> {
    License getLicensesByCode(String code);
    License getLicenseById(Long id);
}
