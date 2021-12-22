package com.codingseo.tddmembership.configs.datasources;

import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@RequiredArgsConstructor
@EnableJpaRepositories(
        basePackages = "com.codingseo.tddmembership.repositories.sub",
        entityManagerFactoryRef = "subEntityManager",
        transactionManagerRef = "subTransactionManager"
)
public class SubDataSourceConfig {

    @Bean
    public DataSource subDataSource(
            @Value("${datasource.sub.url}") String url,
            @Value("${datasource.sub.driver-class-name}") String driverClassName,
            @Value("${datasource.sub.username}") String userName,
            @Value("${datasource.sub.password}") String password
    ) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(url);
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean subEntityManager(DataSource subDataSource, Environment env) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(subDataSource);
        em.setPackagesToScan("com.codingseo.tddmembership.entities.sub");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
        properties.put("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"));
        em.setJpaPropertyMap(properties);
        return em;
    }

    @Bean
    public PlatformTransactionManager subTransactionManager(DataSource subDataSource, LocalContainerEntityManagerFactoryBean subEntityManager) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(subEntityManager.getObject());
        transactionManager.setDataSource(subDataSource);
        return transactionManager;
    }
}
