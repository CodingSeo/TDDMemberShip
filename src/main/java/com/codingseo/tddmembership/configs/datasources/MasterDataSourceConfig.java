package com.codingseo.tddmembership.configs.datasources;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.codingseo.tddmembership.repositories.master",
        entityManagerFactoryRef = "masterEntityManager",
        transactionManagerRef = "masterTransactionManager"
)
public class MasterDataSourceConfig {

    @Primary
    @Bean
    public DataSource masterDataSource(
            @Value("${datasource.master.url}") String url,
            @Value("${datasource.master.driver-class-name}") String driverClassName,
            @Value("${datasource.master.username}") String userName,
            @Value("${datasource.master.password}") String password
    ) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(url);
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Primary
    @Bean
    public JdbcTemplate masterJDBCTemplate(DataSource masterDataSource){
        return new JdbcTemplate(masterDataSource);
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean masterEntityManager(DataSource masterDataSource, Environment env){
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setPackagesToScan("com.codingseo.tddmembership.entities.master");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
        properties.put("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"));
        em.setJpaPropertyMap(properties);
        em.setDataSource(masterDataSource);
        return em;
    }

    @Primary
    @Bean
    public PlatformTransactionManager masterTransactionManager(DataSource masterDataSource, LocalContainerEntityManagerFactoryBean masterEntityManager){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(masterEntityManager.getObject());
        transactionManager.setDataSource(masterDataSource);
        return transactionManager;
    }
}
