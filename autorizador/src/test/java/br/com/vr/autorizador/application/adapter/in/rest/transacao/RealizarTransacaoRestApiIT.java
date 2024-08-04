package br.com.vr.autorizador.application.adapter.in.rest.transacao;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.vr.autorizador.domain.cartao.RealizarTransacaoUseCase.RealizarTransacaoCommand;
import br.com.vr.autorizador.util.integration.ControllerBaseIT;

@DisplayName("IT: Transação -> Realizar transação")
class RealizarTransacaoRestApiIT extends ControllerBaseIT {
	
	private static final String PATH = "/transacoes";
	
	@Test
	@DisplayName("Deve realizar transação com sucesso")
	void deveRealizarComSucesso() throws Exception {
		
		var command = RealizarTransacaoCommand.builder()
				.numeroCartao("6549873025634101")
				.senhaCartao("4321")
				.valor(BigDecimal.valueOf(99.99))
				.build();
		
		mock.perform(post(PATH).contentType("application/json").content(toJson(command)))
			.andExpect(status().isCreated());
	}

	@Test
	@DisplayName("Deve valida quando informado senha inválida")
	void deveValidarSenhaInvalida() throws Exception {
		
		var command = RealizarTransacaoCommand.builder()
				.numeroCartao("6549873025634001")
				.senhaCartao("9999")
				.valor(BigDecimal.valueOf(100.00))
				.build();
		
		mock.perform(post(PATH).contentType("application/json").content(toJson(command)))
				.andExpect(status().isUnprocessableEntity())
				.andExpect(jsonPath("$.messages", hasSize(1)))
				.andExpect(jsonPath("$.messages[0].type").value("warning"))
				.andExpect(jsonPath("$.messages[0].text").value("Senha informada não confere"));
	}
	
	@Test
	@DisplayName("Deve valida quando saldo insuficiente")
	void deveValidarCampos() throws Exception {
		
		var command = RealizarTransacaoCommand.builder()
				.numeroCartao("6549873025634111")
				.senhaCartao("1234")
				.valor(BigDecimal.valueOf(1.01))
				.build();
		
		mock.perform(post(PATH).contentType("application/json").content(toJson(command)))
				.andExpect(status().isUnprocessableEntity())
				.andExpect(jsonPath("$.messages", hasSize(1)))
				.andExpect(jsonPath("$.messages[0].type").value("warning"))
				.andExpect(jsonPath("$.messages[0].text").value("Saldo do cartão 6549873025634111 insuficiente"));
	}
	
}