package com.codingseo.tddmembership.configs.persistence.masterdb;

import com.hiworks.office_api.v4.configs.kms.KmsClientBySystemEnv;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class MasterAdminDatasourceConfiguration {
    private final static String KMS_MASTER_DB = "mysql/masterdb";
    private final static String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private final static String PACKAGE = "com.hiworks.office_api.v4";

    private final KmsClientBySystemEnv kmsClient;

    @Bean(name="entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean masterDbEntityManager(
            MasterDbProperties masterDbProperties,
            @Qualifier("masterDataSource") DataSource dataSource
    ) {
        EntityManagerFactoryBuilder builder = new EntityManagerFactoryBuilder(
                new HibernateJpaVendorAdapter(),
                masterDbProperties.getJpaProperties().getProperties(),
                null
        );

        return builder
                .dataSource(dataSource)
                .properties(masterDbProperties.getJpaProperties().getProperties())
                .persistenceUnit("masterEntityManager")
                .packages(PACKAGE)
                .build();
    }

    @Bean
    public DataSource masterDataSource(
            MasterDbProperties masterDbProperties,
            JpaProperties jpaProperties
    ) throws InvalidPropertiesFormatException {
        masterDbProperties = getValidMasterDbProperties(
                masterDbProperties, jpaProperties
        );

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(DRIVER_CLASS_NAME);
        dataSource.setJdbcUrl(masterDbProperties.getConnectionString());
        dataSource.setUsername(masterDbProperties.getUsername());
        dataSource.setPassword(masterDbProperties.getPassword());
        dataSource.setMaximumPoolSize(
            masterDbProperties.getMaximumPoolSize()
        );

        return dataSource;
    }

    public MasterDbProperties getValidMasterDbProperties(
            MasterDbProperties masterDbProperties,
            JpaProperties jpaProperties
    ){
        masterDbProperties.setJpaProperties(jpaProperties);

        if(masterDbProperties.isValid()){
            return masterDbProperties;
        }

        Map<String, String> masterDbSecretData = kmsClient.getKmsData(KMS_MASTER_DB);
        String hostname = masterDbSecretData.get("hostname");
        String user = masterDbSecretData.get("user");
        String password = masterDbSecretData.get("password");

        if(!masterDbProperties.isValidHostName()){
            masterDbProperties.setHostname(hostname);
        }
        if(!masterDbProperties.isValidUserName()){
            masterDbProperties.setUsername(user);
        }
        if(!masterDbProperties.isValidPassword()){
            masterDbProperties.setPassword(password);
        }

        return masterDbProperties;
    };
}
