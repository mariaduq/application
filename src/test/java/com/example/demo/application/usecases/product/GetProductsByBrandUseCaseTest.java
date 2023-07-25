package com.example.demo.application.usecases.product;

import com.example.demo.application.output.ProductOutput;
import com.example.demo.infrastructure.persistence.adapter.ProductAdapter;
import com.example.demo.infrastructure.persistence.entities.ProductEntity;
import com.example.demo.infrastructure.persistence.jpa.ProductRepositoryJpa;
import com.example.demo.infrastructure.persistence.mappers.ProductMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class GetProductsByBrandUseCaseTest {

    ProductRepositoryJpa productRepositoryJpa = Mockito.mock(ProductRepositoryJpa.class);

    ProductAdapter productAdapter = new ProductAdapter(productRepositoryJpa, new ProductMapper());

    GetProductsByBrandUseCase getProductsByBrandUseCase = new GetProductsByBrandUseCase(productAdapter, new com.example.demo.application.mapper.ProductMapper());

    @Test
    void products_when_brandName_is_LG() throws Exception {
        //GIVEN
        when(productRepositoryJpa.findAll())
                .thenReturn(List.of(
                        buildProduct(9136275, "Frigorifico", "LG", 1),
                        buildProduct(6216547, "Frigorifico", "LG", 1),
                        buildProduct(3837464, "Frigorifico", "Haier", 2),
                        buildProduct(1833244, "Frigorifico", "Haier", 2),
                        buildProduct(4300886, "Lavadora", "LG", 1),
                        buildProduct(6108643, "Congelador", "LG", 1)));

        String brandName = "LG";

        //WHEN
        List<ProductOutput> brandProducts = getProductsByBrandUseCase.execute(brandName);

        //THEN
        assertEquals(4, brandProducts.size());

        assertEquals("Frigorifico", brandProducts.get(0).getProductType());
        assertEquals("Frigorifico", brandProducts.get(1).getProductType());
        assertEquals("Lavadora", brandProducts.get(2).getProductType());
        assertEquals("Congelador", brandProducts.get(3).getProductType());
    }

    @Test
    void products_when_brandName_is_Haier() throws Exception {
        //GIVEN
        when(productRepositoryJpa.findAll())
                .thenReturn(List.of(
                        buildProduct(9136275, "Frigorifico", "LG", 1),
                        buildProduct(6216547, "Frigorifico", "LG", 1),
                        buildProduct(3837464, "Frigorifico", "Haier", 2),
                        buildProduct(1833244, "Frigorifico", "Haier", 2),
                        buildProduct(4300886, "Lavadora", "LG", 1),
                        buildProduct(6108643, "Congelador", "LG", 1)));

        String brandName = "Haier";

        //WHEN
        List<ProductOutput> brandProducts = getProductsByBrandUseCase.execute(brandName);

        //THEN
        assertEquals(2, brandProducts.size());

        assertEquals("Frigorifico", brandProducts.get(0).getProductType());
        assertEquals("Frigorifico", brandProducts.get(1).getProductType());
    }

    @Test
    void products_when_brandName_not_exists() throws Exception {
        //GIVEN
        when(productRepositoryJpa.findAll())
                .thenReturn(List.of(
                        buildProduct(9136275, "Frigorifico", "LG", 1),
                        buildProduct(6216547, "Frigorifico", "LG", 1),
                        buildProduct(3837464, "Frigorifico", "Haier", 2),
                        buildProduct(1833244, "Frigorifico", "Haier", 2),
                        buildProduct(4300886, "Lavadora", "LG", 1),
                        buildProduct(6108643, "Congelador", "LG", 1)));

        String brandName = "Siemens";

        //WHEN; THEN
        assertThrows(Exception.class, ()->getProductsByBrandUseCase.execute(brandName));
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
