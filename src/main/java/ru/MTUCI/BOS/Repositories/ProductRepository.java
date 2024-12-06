package ru.MTUCI.BOS.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.MTUCI.BOS.Requests.Product;
import ru.MTUCI.BOS.Requests.License;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product getProductsById(Long id);
    Product findProductById(Long id);
}
