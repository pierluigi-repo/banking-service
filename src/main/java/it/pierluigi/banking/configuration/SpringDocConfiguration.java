package it.pierluigi.banking.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * This class configures springdoc
 */
@Configuration
@PropertySource(value = "classpath:springdoc.yaml", factory = YamlPropertySourceFactory.class)
public class SpringDocConfiguration {

    @Value("${api.info.version}")       String apiVersion;
    @Value("${api.info.title}")         String apiTitle;
    @Value("${api.info.description}")   String apiDescription;

    @Bean
    public GroupedOpenApi publicBankingApi() {
        return GroupedOpenApi.builder()
                .group("Banking")
                .pathsToMatch("/banking/**")
                .build();
    }

    @Bean
    public OpenAPI infoOpenAPI() {
        return new OpenAPI()
                .info(new Info().title(this.apiTitle)
                        .description(this.apiDescription)
                        .version(this.apiVersion));
    }
}
