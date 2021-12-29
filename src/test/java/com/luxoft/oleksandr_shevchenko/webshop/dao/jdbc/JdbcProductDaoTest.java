package com.luxoft.oleksandr_shevchenko.webshop.dao.jdbc;

import com.luxoft.oleksandr_shevchenko.webshop.entity.Product;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

class JdbcProductDaoTest {

    @Test
    public void testFindAllReturnCorrectData() {
        JdbcProductDao jdbcProductDao = new JdbcProductDao();
        List<Product> products = jdbcProductDao.findAll();
        assertFalse(products.isEmpty());
        for (Product product : products) {
            assertNotEquals(0, product.getId());
            assertNotNull(product.getName());
            assertNotEquals(0, product.getPrice());
            assertNotNull(product.getCreationDate());
        }
    }


    @Test
    public void testFindById() {
        JdbcProductDao jdbcProductDaoMock = Mockito.mock(JdbcProductDao.class);

        Product product = Product.builder().build();
        product.setId(23);
        product.setName("IPhone");
        product.setPrice(100);

        when(jdbcProductDaoMock.prFindById(23)).thenReturn(product);

        assertEquals(23, product.getId());
        assertEquals(100, product.getPrice());
        assertEquals("IPhone", product.getName());
    }


}