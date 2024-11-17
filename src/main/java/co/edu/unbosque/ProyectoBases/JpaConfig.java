package co.edu.unbosque.ProyectoBases;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;

@Configuration
public class JpaConfig {
    @Bean
    public EntityManagerFactoryBuilder entityManagerFactoryBuilder() {
        return new EntityManagerFactoryBuilder(
            new org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter(), 
            new java.util.HashMap<>(), 
            null
        );
    }
}
