package study.board.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "API test",
        description = "Swagger API",
        version = "1.0.0")
)
@Configuration
public class SwaggerConfig {

//    @Bean
//    public OpenAPI customOpenAPI() {
//        return new OpenAPI()
//                .components(new Components())
//                .info(apiInfo());
//    }
//
//    private Info apiInfo() {
//        return new Info()
//                .title("API test") // API 제목
//                .description("Swagger API") // API 설명
//                .version("1.0.0"); // API 버전
//    }

    @Bean
    public GroupedOpenApi getItemApi() {

        return GroupedOpenApi
                .builder()
                .group("item")
                .pathsToMatch("/verify-email")
                .build();
    }

    @Bean
    public GroupedOpenApi getMemberApi() {

        return GroupedOpenApi
                .builder()
                .group("member")
                .pathsToMatch("/verification-code")
                .build();
    }
}
