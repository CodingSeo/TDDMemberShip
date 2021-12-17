package com.codingseo.tddmembership.configs.datasources;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.codingseo.tddmembership.repositories.sub",
        entityManagerFactoryRef = "masterEntityManager",
        transactionManagerRef = "masterTransactionManager"
)
public class MasterDataSourceConfig {

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
    @Bean
    public JdbcTemplate masterJDBCTemplate(DataSource masterDataSource){
        return new JdbcTemplate(masterDataSource);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean masterEntityManager(DataSource masterDataSource){
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(masterDataSource);
        return em;
    }

    @Bean
    public PlatformTransactionManager masterTransactionManager(DataSource masterDataSource){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setDataSource(masterDataSource);
        return transactionManager;
    }
}
