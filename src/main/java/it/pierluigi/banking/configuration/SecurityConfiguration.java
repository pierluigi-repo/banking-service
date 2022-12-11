package it.pierluigi.banking.configuration;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This class contains additional security configuration
 */
@Configuration
public class SecurityConfiguration {

    @Value("${jasypt.key}")
    private String jasyptKey;

    /**
     * Defines a custom Encryptor to obfuscate sensitive properties with Jasypt
     *
     * @return A custom StringEncryptor
     */
    @Bean(name = "encryptorBean")
    public StringEncryptor stringEncryptor() {

        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        // in production environment should pass this value as argument, and retrieve it through System.getProperty
        config.setPassword(this.jasyptKey);
        // in production environment should use a stronger algorithm (e.g. PBEWithHMACSHA512AndAES_256)
        // this weak algorithm is only used to avoid EncryptionOperationNotPossibleException on test jdk
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);

        return encryptor;

    }
}
