package com.iamzakaria.dripp.product;

import com.iamzakaria.dripp.dao.ProductRepository;
import com.iamzakaria.dripp.entity.Product;
import com.iamzakaria.dripp.enums.Category;
import com.iamzakaria.dripp.service.product.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTests {
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductServiceImpl productService;
    private Product product;
    @BeforeEach
    public void setup(){
        product = Product.builder()
                .id(1L)
                .productName("Hoodie XJ")
                .description("Black Over Sized Hoodie, Very Confy and coton Made.")
                .price(BigDecimal.valueOf(29.99))
                .category(Category.HOODIE)
                .unitsInStock(5)
                .imageUrl("classpath:images/hoodie.jpg".getBytes())
                .build();
    }

    @DisplayName("JUnit test for getProductById method")
    @Test
    public void givenProductId_whenGetProductById_thenReturnProductObject(){
        // given
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        // when
        Product savedProduct = productService.getProductById(product.getId());
        // then
        assertThat(savedProduct).isNotNull();
    }
}
