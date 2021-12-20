package com.codingseo.tddmembership.configs.persistence;

import com.hiworks.office_api.v4.configs.kms.KmsClientBySystemEnv;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;

import java.util.Map;

@EnableCouchbaseRepositories(basePackages={"com.hiworks.office_api.v4.cache"})
@RequiredArgsConstructor
@Configuration
public class CouchbaseConfig extends AbstractCouchbaseConfiguration {
    private static final String dbName = "couchbase/menu";
    private static final String bucketName = "hiworks-office-info";
    private final KmsClientBySystemEnv kmsClientBySystemEnv;
    private Map<String, String> kmsData;

    /**
     * The connection string which allows the SDK to connect to the cluster.
     */
    @Override
    public String getConnectionString() {
        return getCouchbaseInfo("hostname");
    }

    /**
     * The username of the user accessing Couchbase, configured on the cluster.
     */
    @Override
    public String getUserName() {
        return getCouchbaseInfo("user");
    }

    /**
     * The password used or the username to authenticate against the cluster.
     */
    @Override
    public String getPassword() {
        return getCouchbaseInfo("password");
    }

    /**
     * The name of the bucket that should be used (for example "travel-sample").
     */
    @Override
    public String getBucketName() {
        return bucketName;
    }


    private String getCouchbaseInfo(String key){
        if(kmsData==null) {
            kmsData = kmsClientBySystemEnv.getKmsData(dbName);
        }

        return kmsData.get(key);
    }
}
