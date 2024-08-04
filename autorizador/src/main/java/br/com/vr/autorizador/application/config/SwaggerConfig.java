package br.com.vr.autorizador.application.config;

import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {
	
	private final BuildProperties build;

	@Bean
	OpenAPI openApi() {

		var info = new Info()
				.title("Autorizador")
				.description("Desafio VR")
				.version("V".concat(build.getVersion()));

		return new OpenAPI().info(info);
	}

}