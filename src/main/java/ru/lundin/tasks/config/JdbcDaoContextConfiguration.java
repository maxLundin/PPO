package ru.lundin.tasks.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.lundin.tasks.dao.ProductJdbcDao;

import javax.sql.DataSource;

/**
 * @author lundin
 */
public class JdbcDaoContextConfiguration {
    @Bean
    public ProductJdbcDao productJdbcDao(DataSource dataSource) {
        return new ProductJdbcDao(dataSource);
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.sqlite.JDBC");
        dataSource.setUrl("jdbc:sqlite:product.db");
        dataSource.setUsername("");
        dataSource.setPassword("");
        return dataSource;
    }
}
