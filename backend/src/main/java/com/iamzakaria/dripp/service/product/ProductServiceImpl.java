package com.iamzakaria.dripp.service.product;

import com.iamzakaria.dripp.dao.ProductRepository;
import com.iamzakaria.dripp.entity.Product;
import com.iamzakaria.dripp.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product addProduct(Product product, MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            product.setImageUrl(file.getBytes());
        }
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }
    @Override
    public Product getProductById(Long productId){
        Optional<Product> product = productRepository.findById(productId);
        return product.orElseThrow(() -> new NotFoundException("Product with ID " + productId + " not found"));
    }
    @Override
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }
    @Override
    public void deleteProduct(Long productId){
        productRepository.deleteById(productId);
    }
    @Override
    public byte[] getProductImage(Long productId) throws IOException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IOException("Product not found"));
        return product.getImageUrl();
    }
}
