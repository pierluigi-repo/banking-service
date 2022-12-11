package it.pierluigi.banking.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * This class contains additional spring web configuration
 */
@Configuration
@PropertySource("classpath:configuration.properties")
@EnableEncryptableProperties
public class WebConfiguration {

    @Value("${api.base.url}")
    private String apiBaseUrl;

    @Value("${api.key}")
    private String apiKey;

    /**
     * Configures an HTTP client ready for setting up a connection to Sandbox APIs
     *
     * @param baseConfig The default object mapper
     * @return A WebClient implementation
     */
    @Bean
    public WebClient webClient(ObjectMapper baseConfig) {
        ObjectMapper newMapper = baseConfig.copy();
        newMapper.setPropertyNamingStrategy(PropertyNamingStrategies.LOWER_CAMEL_CASE);

        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(configurer ->
                        configurer.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(newMapper)))
                .build();
        return WebClient.builder()
                .baseUrl(this.apiBaseUrl)
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.set("Auth-Schema", "S2S");
                    httpHeaders.set("Api-Key", apiKey);
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                })
                .exchangeStrategies(exchangeStrategies)
                .build();
    }
}
