package ru.MTUCI.BOS.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.MTUCI.BOS.Requests.ProductRequest;
import ru.MTUCI.BOS.Requests.Product;
import ru.MTUCI.BOS.Repositories.ProductRepository;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProductById(Long productId) {
        return productRepository.getProductsById(productId);
    }

    /// CRUD operations

    public Product createProduct(ProductRequest productRequest) {
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setBlocked(productRequest.isBlocked());
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, ProductRequest productRequest) {
        Product product = productRepository.findProductById(id);
        if (product != null) {
            product.setName(productRequest.getName());
            product.setBlocked(productRequest.isBlocked());
            return productRepository.save(product);
        }
        return null;
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }


}
