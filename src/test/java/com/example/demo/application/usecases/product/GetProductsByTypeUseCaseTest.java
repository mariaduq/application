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

public class GetProductsByTypeUseCaseTest {

    ProductRepositoryJpa productRepositoryJpa = Mockito.mock(ProductRepositoryJpa.class);

    ProductAdapter productAdapter = new ProductAdapter(productRepositoryJpa, new ProductMapper());

    GetProductsByTypeUseCase getProductsByTypeUseCase = new GetProductsByTypeUseCase(productAdapter, new com.example.demo.application.mapper.ProductMapper());

    @Test
    void products_when_type_is_Lavadora() throws Exception {
        //GIVEN
        when(productRepositoryJpa.findAll())
                .thenReturn(List.of(
                        buildProduct(9136275, "Frigorifico", "LG", 1),
                        buildProduct(6216547, "Frigorifico", "LG", 1),
                        buildProduct(3837464, "Frigorifico", "Haier", 2),
                        buildProduct(1833244, "Frigorifico", "Haier", 2),
                        buildProduct(4300886, "Lavadora", "LG", 1),
                        buildProduct(6108643, "Congelador", "LG", 1)));

        String type = "Lavadora";

        //WHEN
        List<ProductOutput> products = getProductsByTypeUseCase.execute(type);

        //THEN
        assertEquals(1, products.size());

        assertEquals("LG", products.get(0).getBrandName());
    }

    @Test
    void products_when_type_is_Frigorifico() throws Exception {
        //GIVEN
        when(productRepositoryJpa.findAll())
                .thenReturn(List.of(
                        buildProduct(9136275, "Frigorifico", "LG", 1),
                        buildProduct(6216547, "Frigorifico", "LG", 1),
                        buildProduct(3837464, "Frigorifico", "Haier", 2),
                        buildProduct(1833244, "Frigorifico", "Haier", 2),
                        buildProduct(4300886, "Lavadora", "LG", 1),
                        buildProduct(6108643, "Congelador", "LG", 1)));

        String type = "Frigorifico";

        //WHEN
        List<ProductOutput> products = getProductsByTypeUseCase.execute(type);

        //THEN
        assertEquals(4, products.size());

        assertEquals("LG", products.get(0).getBrandName());
        assertEquals("LG", products.get(1).getBrandName());
        assertEquals("Haier", products.get(2).getBrandName());
        assertEquals("Haier", products.get(3).getBrandName());
    }

    @Test
    void products_when_type_is_Congelador() throws Exception {
        //GIVEN
        when(productRepositoryJpa.findAll())
                .thenReturn(List.of(
                        buildProduct(9136275, "Frigorifico", "LG", 1),
                        buildProduct(6216547, "Frigorifico", "LG", 1),
                        buildProduct(3837464, "Frigorifico", "Haier", 2),
                        buildProduct(1833244, "Frigorifico", "Haier", 2),
                        buildProduct(4300886, "Lavadora", "LG", 1),
                        buildProduct(6108643, "Congelador", "LG", 1)));

        String type = "Congelador";

        //WHEN
        List<ProductOutput> products = getProductsByTypeUseCase.execute(type);

        //THEN
        assertEquals(1, products.size());

        assertEquals("LG", products.get(0).getBrandName());
    }


    @Test
    void products_when_type_not_exists() throws Exception {
        //GIVEN
        when(productRepositoryJpa.findAll())
                .thenReturn(List.of(
                        buildProduct(9136275, "Frigorifico", "LG", 1),
                        buildProduct(6216547, "Frigorifico", "LG", 1),
                        buildProduct(3837464, "Frigorifico", "Haier", 2),
                        buildProduct(1833244, "Frigorifico", "Haier", 2),
                        buildProduct(4300886, "Lavadora", "LG", 1),
                        buildProduct(6108643, "Congelador", "LG", 1)));

        String type = "Licuadora";

        //WHEN; THEN
        assertThrows(Exception.class, ()->getProductsByTypeUseCase.execute(type));
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
