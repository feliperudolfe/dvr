package br.com.vr.autorizador.application.adapter.in.rest.cartao;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.vr.autorizador.domain.cartao.ObterSaldoCartaoUseCase;
import br.com.vr.autorizador.domain.cartao.ObterSaldoCartaoUseCase.ObterSaldoCartaoCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Cartão enpoint")

@RestController
@RequiredArgsConstructor
@RequestMapping("cartoes/{numeroCartao:[0-9]{16}}")
public class ObterSaldoCartaoRestAPI {
	
	private final ObterSaldoCartaoUseCase useCase; 

	@Operation(summary = "Obter saldo de cartão")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200", 
			description  = "Success"),
		@ApiResponse(
			responseCode = "404",
			description = "Not found")
	})
	@GetMapping
	public BigDecimal handle(@PathVariable String numeroCartao) {
		return useCase.execute(ObterSaldoCartaoCommand.withNumeroCartao(numeroCartao));
	}

}