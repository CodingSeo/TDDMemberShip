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
    @Value("${datasource.master.url}")
    String url;
    @Value("${datasource.master.driver-class-name}")
    String driverClassName;
    @Value("${datasource.master.username}")
    String userName;
    @Value("${datasource.master.password}")
    String password;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    String ddlAuto;
    @Value("${spring.jpa.properties.hibernate.dialect}")
    String dialect;

    @Primary
    @Bean
    public DataSource masterDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(url);
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Primary
    @Bean
    public JdbcTemplate masterJDBCTemplate(DataSource masterDataSource) {
        return new JdbcTemplate(masterDataSource);
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean masterEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setPackagesToScan("com.codingseo.tddmembership.entities.master");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", ddlAuto);
        properties.put("hibernate.dialect", dialect);
        em.setJpaPropertyMap(properties);
        em.setDataSource(masterDataSource());
        return em;
    }

    @Primary
    @Bean
    public PlatformTransactionManager masterTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(masterEntityManager().getObject());
        transactionManager.setDataSource(masterDataSource());
        return transactionManager;
    }
}
