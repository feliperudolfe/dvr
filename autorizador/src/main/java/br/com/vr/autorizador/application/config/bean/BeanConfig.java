package br.com.vr.autorizador.application.config.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.vr.autorizador.domain.cartao.CriarCartaoUseCase;
import br.com.vr.autorizador.domain.cartao.repository.CartaoRepository;
import br.com.vr.autorizador.domain.cartao.service.CriarCartaoService;

@Configuration
public class BeanConfig {

	@Bean
	CriarCartaoUseCase getCriarCartaoUseCase(CartaoRepository repository) {
		return new CriarCartaoService(repository);
	}

}