package br.com.vr.autorizador.application.adapter.in.rest.transacao;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import br.com.vr.autorizador.domain.cartao.CriarCartaoUseCase.CriarCartaoCommand;
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
	
	
	@ParameterizedTest
	@MethodSource("getCommandInvalido")
	@DisplayName("Deve valida quando dados informados inválidos")
	void deveValidarCampos(RealizarTransacaoCommand command, String [] validacoes) throws Exception {
		
		mock.perform(post(PATH).contentType("application/json").content(toJson(command)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.messages", hasSize(3)))
				.andExpect(jsonPath("$.messages.[*].type", hasItem("warning")))
				.andExpect(jsonPath("$.messages.[*].field", containsInAnyOrder("numeroCartao", "senhaCartao", "valor")))
				.andExpect(jsonPath("$.messages.[*].text", containsInAnyOrder(validacoes)));
	}
	
	private static Stream<Arguments> getCommandInvalido() {
		return Stream.of(
				Arguments.of(
						RealizarTransacaoCommand.builder().build(),
						new String[] {"Número do cartão é obrigatório", "Senha do cartão é obrigatória", "Valor da transação é obrigatório"}),
				Arguments.of(
						RealizarTransacaoCommand.builder().numeroCartao("123456789012345").senhaCartao("123").valor(BigDecimal.valueOf(10.9999)).build(), 
						new String[] {"Número do cartão inválido", "Senha do cartão inválida", "Valor no formato inválido"}),
				Arguments.of(
						RealizarTransacaoCommand.builder().numeroCartao("abcdefghijklmnop").senhaCartao("abcd").valor(BigDecimal.valueOf(9999999999999999999.99)).build(), 
						new String[] {"Número do cartão inválido", "Senha do cartão inválida", "Valor no formato inválido"}));
	}
	
}