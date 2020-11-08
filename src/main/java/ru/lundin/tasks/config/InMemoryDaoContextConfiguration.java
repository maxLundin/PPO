package ru.lundin.tasks.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.lundin.tasks.dao.ProductDao;
import ru.lundin.tasks.dao.ProductInMemoryDao;

/**
 * @author lundin
 */
@Configuration
public class InMemoryDaoContextConfiguration {
    @Bean
    public ProductDao productDao() {
        return new ProductInMemoryDao();
    }
}
