package com.codingseo.tddmembership.configs.datasources;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.codingseo.tddmembership.repositories.sub",
        entityManagerFactoryRef = "subEntityManager",
        transactionManagerRef = "subTransactionManager"
)
public class SubDataSourceConfig {

    @Primary
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
    public LocalContainerEntityManagerFactoryBean subEntityManager(DataSource subDataSource){
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setPackagesToScan("com.codingseo.tddmembership.entities.sub");
        em.setPersistenceUnitName("membership");
        em.setDataSource(subDataSource);
        return em;
    }

    @Bean
    public PlatformTransactionManager subTransactionManager(DataSource subDataSource){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setDataSource(subDataSource);
        return transactionManager;
    }
}
