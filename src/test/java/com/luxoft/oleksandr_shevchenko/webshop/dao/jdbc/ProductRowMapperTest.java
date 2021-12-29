package com.luxoft.oleksandr_shevchenko.webshop.dao.jdbc;

import com.luxoft.oleksandr_shevchenko.webshop.entity.Product;
import org.junit.jupiter.api.Test;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductRowMapperTest {

    @Test
    public void testMapRow() throws SQLException {
        ProductRowMapper productRowMapper = new ProductRowMapper();
        LocalDateTime localDateTime = LocalDateTime.of(2021,10,11,19,22,30);
        Timestamp timestamp = Timestamp.valueOf(localDateTime);

        ResultSet resultSetMock = mock(ResultSet.class);
        when(resultSetMock.getInt("id")).thenReturn(101);
        when(resultSetMock.getString("name")).thenReturn("phone");
        when(resultSetMock.getDouble("price")).thenReturn(100.0);
        when(resultSetMock.getTimestamp("creation_date")).thenReturn(timestamp);

        Product actual = productRowMapper.mapRow(resultSetMock);

        assertEquals(101, actual.getId());
        assertEquals("phone", actual.getName());
        assertEquals(100.0, actual.getPrice());
        assertEquals(timestamp, actual.getCreationDate());
    }
}
