package br.com.vr.autorizador.application.adapter.in.rest.cartao;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.vr.autorizador.util.integration.ControllerBaseIT;

@DisplayName("IT: Cartão -> Obter saldo do cartão")
class ObterSaldoCartaoRestApiIT extends ControllerBaseIT {
	
	private static final String PATH = "/cartoes/{numeroCartao}";
	
	@Test
	@DisplayName("Deve obter saldo de cartão com sucesso")
	void deveSaldoComSucesso() throws Exception {
		
		var numeroCartao = "6549873025634001";
		
		mock.perform(get(PATH, numeroCartao))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", is(500.00)));
	}

	@Test
	@DisplayName("Deve valida quando não encontrado para número informado")
	void deveValidarCartaoNaoEncontrado() throws Exception {
		
		var numeroCartao = "6549873025634999";
		
		mock.perform(get(PATH, numeroCartao))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.messages[0].type").value("warning"))
				.andExpect(jsonPath("$.messages[0].text").value("Cartão 6549873025634999 não existe"));
	}
	
}