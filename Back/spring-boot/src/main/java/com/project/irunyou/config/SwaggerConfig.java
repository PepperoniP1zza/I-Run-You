/* 작성자 : 홍지혜
 * 파일의 역할 : 모든 API 요청 조회용
 * 작성날짜 : 2023-01-12
 * 
 * 업데이트 작성자 : -
 * 업데이트 날짜 : -
 * */

/* 서버를 켜고 브라우저에서
 * http://localhost:4040/v2/api-docs
 * 접속하면 현재 사용 가능한 Request들 JSON 조회 가능 -> raw 데이터 저장
 * https://editor.swagger.io/
 * 접속 후 json 파일 import 하기
 * 
 * */
package com.project.irunyou.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig{
	
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // apis : 대상 패키지 설정
                .apis(RequestHandlerSelectors.basePackage("com.project.irunyou")) // swagger에서 스캔할 패키지 범위
                // paths : 어떤 식으로 시작하는 api를 먼저 보여줄 것인지 결정
                // any - 그냥 전부다.
                .paths(PathSelectors.any())
                .build();
    }
    

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("I RUN YOU Spring Boot REST API")
                .version("1.0.0")
                .description("I RUN YOU")
                .build();
    }
}
