package br.com.vr.autorizador.application.adapter.in.rest.cartao;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import br.com.vr.autorizador.domain.cartao.CriarCartaoUseCase.CriarCartaoCommand;
import br.com.vr.autorizador.util.integration.ControllerBaseIT;

@DisplayName("IT: Cartão -> Criar cartão")
class CriarCartaoEndpointIT extends ControllerBaseIT {
	
	private static final String PATH = "/cartoes";
	
	@Test
	@DisplayName("Deve criar cartão com sucesso")
	void deveCriarComSucesso() throws Exception {
		
		var command = CriarCartaoCommand.builder()
				.numeroCartao("6549873025634501")
				.senha("1234")
				.build();
		
		mock.perform(post(PATH).contentType("application/json").content(toJson(command)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.numeroCartao", is("6549873025634501")))
			.andExpect(jsonPath("$.senha", is("1234")));
	}

	@Test
	@DisplayName("Deve valida quando número do cartão já existir")
	void deveValidarCartaoDuplicado() throws Exception {
		
		var command = CriarCartaoCommand.builder()
				.numeroCartao("6549873025634001")
				.senha("1234")
				.build();
		
		mock.perform(post(PATH).contentType("application/json").content(toJson(command)))
				.andExpect(status().isUnprocessableEntity())
				.andExpect(jsonPath("$.messages", hasSize(1)))
				.andExpect(jsonPath("$.messages[0].type").value("warning"))
				.andExpect(jsonPath("$.messages[0].text").value("Cartão 6549873025634001 já existe"));
	}
	
	@ParameterizedTest
	@MethodSource("getCommandInvalido")
	@DisplayName("Deve valida quando dados informados inválidos")
	void deveValidarCampos(CriarCartaoCommand command, String [] validacoes) throws Exception {
		
		mock.perform(post(PATH).contentType("application/json").content(toJson(command)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.messages", hasSize(2)))
				.andExpect(jsonPath("$.messages.[*].type", hasItem("warning")))
				.andExpect(jsonPath("$.messages.[*].field", containsInAnyOrder("numeroCartao", "senha")))
				.andExpect(jsonPath("$.messages.[*].text", containsInAnyOrder(validacoes)));
	}
	
	private static Stream<Arguments> getCommandInvalido() {
		return Stream.of(
				Arguments.of(
						CriarCartaoCommand.builder().build(),
						new String[] {"Número do cartão é obrigatório", "Senha do cartão é obrigatória"}),
				Arguments.of(
						CriarCartaoCommand.builder().numeroCartao("123456789012345").senha("123").build(), 
						new String[] {"Número do cartão deve possuir 16 números", "Senha do cartão deve possuir 4 números"}),
				Arguments.of(
						CriarCartaoCommand.builder().numeroCartao("abcdefghijklmnop").senha("abcd").build(), 
						new String[] {"Número do cartão deve possuir 16 números", "Senha do cartão deve possuir 4 números"}));
	}
	
}