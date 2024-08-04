package br.com.vr.autorizador.application.config;

import java.util.Arrays;
import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Bean
	LocaleResolver localeResolver() {

		var language = Arrays.asList(new Locale("pt", "BR"), Locale.ENGLISH);
		var resolver = new AcceptHeaderLocaleResolver();
		resolver.setDefaultLocale(language.get(0));
		resolver.setSupportedLocales(language);

		return resolver;
	}

}