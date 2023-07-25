package com.example.demo.application.usecases.product;

import com.example.demo.application.output.ProductOutput;
import com.example.demo.infrastructure.persistence.adapter.ProductAdapter;
import com.example.demo.infrastructure.persistence.entities.ProductEntity;
import com.example.demo.infrastructure.persistence.jpa.ProductRepositoryJpa;
import com.example.demo.infrastructure.persistence.mappers.ProductMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class GetAllProductsUseCaseTest {

    ProductRepositoryJpa productRepositoryJpa = Mockito.mock(ProductRepositoryJpa.class);

    ProductAdapter productAdapter = new ProductAdapter(productRepositoryJpa, new ProductMapper());

    GetAllProductsUseCase getAllProductsUseCase = new GetAllProductsUseCase(productAdapter, new com.example.demo.application.mapper.ProductMapper());

    @Test
    void should_return_all_products() {
        //GIVEN
        when(productRepositoryJpa.findAll())
                .thenReturn(List.of(
                        buildProduct(9136275, "Frigorifico", "LG", 1),
                        buildProduct(6216547, "Frigorifico", "LG", 1),
                        buildProduct(3837464, "Frigorifico", "Haier", 2),
                        buildProduct(1833244, "Frigorifico", "Haier", 2),
                        buildProduct(4300886, "Lavadora", "LG", 1),
                        buildProduct(6108643, "Congelador", "LG", 1)));

        //WHEN
        List<ProductOutput> products = getAllProductsUseCase.execute();

        //THEN
        assertEquals(6, products.size());
    }

    @Test
    void should_return_empty_list() {
        //GIVEN
        when(productRepositoryJpa.findAll())
                .thenReturn(List.of());

        //WHEN
        List<ProductOutput> allProducts = getAllProductsUseCase.execute();

        //THEN
        assertThat(allProducts.isEmpty());
    }

    private ProductEntity buildProduct(long productId, String productType, String brandName, long brandId) {
        ProductEntity product = new ProductEntity();

        product.setProductId(productId);
        product.setProductType(productType);
        product.setBrandName(brandName);
        product.setBrandId(brandId);
        product.setDescription("Description");
        product.setStock(5);

        return product;
    }
}
