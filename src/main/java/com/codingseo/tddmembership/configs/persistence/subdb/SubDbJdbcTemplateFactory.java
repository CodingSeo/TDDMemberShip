package com.codingseo.tddmembership.configs.persistence.subdb;

import com.hiworks.office_api.v4.configs.kms.KmsClientBySystemEnv;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Map;

@Slf4j
@Configuration
public class SubDbJdbcTemplateFactory {
    private final static String KMS_SUB_DB = "mysql/subdb";
    private final static String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";

    private final KmsClientBySystemEnv kmsClient;
    private final SubDbProperties subDbProperties;

    public SubDbJdbcTemplateFactory(KmsClientBySystemEnv kmsClient, SubDbProperties subDbProperties) {
        this.kmsClient = kmsClient;
        this.subDbProperties = subDbProperties;

        subDbConnectionCheck();
    }

    private void subDbConnectionCheck(){
        // kms에서 sub db 정보
        Map<String, String> openDbSecretData = kmsClient.getKmsData(KMS_SUB_DB);
        String hostname = openDbSecretData.get("hostname");
        String user = openDbSecretData.get("user");
        String password = openDbSecretData.get("password");

        // properties에 유효한 db정보가 있다면 properties 정보로 대체
        if(!this.subDbProperties.isValidHostName()){
            this.subDbProperties.setHostname(hostname);
        }
        if(!this.subDbProperties.isValidUserName()){
            this.subDbProperties.setUsername(user);
        }
        if(!this.subDbProperties.isValidPassword()){
            this.subDbProperties.setPassword(password);
        }

        String partition = "01";
        String databaseName = "member";
        JdbcTemplate jdbcTemplate = getJdbcTemplate(
            this.subDbProperties.getHostname(),
            partition,
            databaseName
        );
        jdbcTemplate.query(
            "SELECT NOW() FROM dual", (resultSet, i) -> {
                log.info("sub db connection success hostname: {} / db: {}",
                    this.subDbProperties.getHostname(),
                    databaseName + "_" + partition
                );
                return null;
            }
        );
    }

    private DataSource getDataSource(String hostName, String partitionNo, String databaseName){
        String url = "jdbc:mysql://" + hostName + "/"
            + databaseName + "_" + partitionNo
            + "?serverTimezone=UTC&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull";

        return DataSourceBuilder.create()
            .driverClassName(DRIVER_CLASS_NAME)
            .url(url)
            .username(subDbProperties.getUsername())
            .password(subDbProperties.getPassword())
            .build();
    }

    public JdbcTemplate getJdbcTemplate(String hostName, String partitionNo, String databaseName){
        return new JdbcTemplate(
            getDataSource(hostName, partitionNo, databaseName)
        );
    }
}
