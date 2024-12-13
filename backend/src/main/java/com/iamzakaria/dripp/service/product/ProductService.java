package com.iamzakaria.dripp.service.product;

import com.iamzakaria.dripp.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(Long productId);
    Product addProduct(Product product, MultipartFile file) throws IOException;
    Product updateProduct(Product product);
    void deleteProduct(Long productId);
    byte[] getProductImage(Long productId) throws IOException;
}
