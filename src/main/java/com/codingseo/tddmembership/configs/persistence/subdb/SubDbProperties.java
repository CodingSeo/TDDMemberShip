package com.codingseo.tddmembership.configs.persistence.subdb;

import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.util.Strings;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@ConfigurationProperties(prefix = "hiworks.datasource.subdb")
@Configuration
public class SubDbProperties {
    private String hostname;
    private String username;
    private String password;

    public boolean isValidHostName(){
        if(hostname == null || Strings.isBlank(hostname)){
            return false;
        }

        return true;
    }

    public boolean isValidUserName(){
        if(username == null || Strings.isBlank(username)){
            return false;
        }

        return true;
    }

    public boolean isValidPassword(){
        if(password == null || Strings.isBlank(password)){
            return false;
        }

        return true;
    }
}
