package com.luxoft.oleksandr_shevchenko.webshop.dao.jdbc;

import com.luxoft.oleksandr_shevchenko.webshop.entity.User;
import com.luxoft.oleksandr_shevchenko.webshop.service.SecurityService;
import org.junit.jupiter.api.Test;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserRowMapperTest {

    @Test
    public void testMapRow() throws SQLException, NoSuchAlgorithmException {
        UserRowMapper userRowMapper = new UserRowMapper();

        ResultSet resultSetMock = mock(ResultSet.class);
        when(resultSetMock.getInt("id")).thenReturn(123);
        when(resultSetMock.getString("email")).thenReturn("darthvader@gmail.com");
        when(resultSetMock.getString("password")).thenReturn("12345");
        when(resultSetMock.getString("gender")).thenReturn("male");
        when(resultSetMock.getString("firstName")).thenReturn("Darth");
        when(resultSetMock.getString("lastName")).thenReturn("Vader");
        when(resultSetMock.getString("about")).thenReturn("DeathStar");
        when(resultSetMock.getInt("age")).thenReturn(100);

        User actual = userRowMapper.mapRow(resultSetMock);

        assertEquals(123, actual.getId());
        assertEquals("darthvader@gmail.com", actual.getEmail());
        assertEquals(SecurityService.md5("12345"), actual.getPassword());
        assertEquals("male", actual.getGender());
        assertEquals("Darth", actual.getFirstName());
        assertEquals("Vader", actual.getLastName());
        assertEquals("DeathStar", actual.getAbout());
        assertEquals(100, actual.getAge());

    }
}
