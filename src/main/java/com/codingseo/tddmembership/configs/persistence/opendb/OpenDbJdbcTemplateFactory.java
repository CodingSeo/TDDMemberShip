package com.codingseo.tddmembership.configs.persistence.opendb;

import com.hiworks.office_api.v4.configs.kms.KmsClientBySystemEnv;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Map;

@Slf4j
@Configuration
public class OpenDbJdbcTemplateFactory {
    private final static String KMS_OPEN_DB = "mysql/opendb";
    private final static String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";

    private final KmsClientBySystemEnv kmsClient;
    private final OpenDbProperties openDbProperties;

    public OpenDbJdbcTemplateFactory(KmsClientBySystemEnv kmsClient, OpenDbProperties openDbProperties) {
        this.kmsClient = kmsClient;
        this.openDbProperties = openDbProperties;

        openDbConnectionCheck();
    }

    private void openDbConnectionCheck(){
        // kms에서 open db 정보 GET
        Map<String, String> openDbSecretData = kmsClient.getKmsData(KMS_OPEN_DB);
        String hostname = openDbSecretData.get("hostname");
        String user = openDbSecretData.get("user");
        String password = openDbSecretData.get("password");

        // properties에 유효한 db정보가 있다면 properties 정보로 대체
        if(!this.openDbProperties.isValidHostName()){
            this.openDbProperties.setHostname(hostname);
        }
        if(!this.openDbProperties.isValidUserName()){
            this.openDbProperties.setUsername(user);
        }
        if(!this.openDbProperties.isValidPassword()){
            this.openDbProperties.setPassword(password);
        }

        String databaseName = "schedule";
        JdbcTemplate jdbcTemplate = getJdbcTemplate(
            databaseName
        );
        jdbcTemplate.query(
            "SELECT NOW() FROM dual", (resultSet, i) -> {
                log.info("open db connection success hostname: {} / db: {}",
                    this.openDbProperties.getHostname(),
                    databaseName
                );
                return null;
            }
        );
    }

    private DataSource getDataSource(String databaseName){
        String url = "jdbc:mysql://" + this.openDbProperties.getHostname() + "/"
            + databaseName
            + "?serverTimezone=UTC&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull";

        return DataSourceBuilder.create()
            .driverClassName(DRIVER_CLASS_NAME)
            .url(url)
            .username(openDbProperties.getUsername())
            .password(openDbProperties.getPassword())
            .build();
    }

    public JdbcTemplate getJdbcTemplate(String databaseName){
        return new JdbcTemplate(
            getDataSource(databaseName)
        );
    }
}
