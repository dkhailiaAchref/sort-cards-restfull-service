package com.example.sortCartes.config;


import com.example.sortCartes.utils.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * tester les Apis Rest sur
 * https://localhost:8443/swagger-ui.html#
 * (aprés avoir  demarré le serveur embarqué de spring-web , par spring-boot:run )
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.sortCartes.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaInfo())
                        .tags(new Tag(Constants.TAG_CARDS_GAME, "API permettant la récuperation et le tri des cartes.")
);
    }

    private ApiInfo metaInfo() {

        ApiInfo apiInfo = new ApiInfo(
                " SORT CARTES API",
                "API project to expose  sort cards functionality to all apps"
                ,
                "1.0",
        null,
                new Contact("Achref dkhailia", "","dkhailia.achref@gmail.com"),
                null,
                null);
        return apiInfo;
    }
}
