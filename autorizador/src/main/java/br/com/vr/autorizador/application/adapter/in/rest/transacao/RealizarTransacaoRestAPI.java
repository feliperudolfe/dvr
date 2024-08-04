package br.com.vr.autorizador.application.adapter.in.rest.transacao;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.vr.autorizador.domain.cartao.RealizarTransacaoUseCase;
import br.com.vr.autorizador.domain.cartao.RealizarTransacaoUseCase.RealizarTransacaoCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Transação")

@RestController
@RequiredArgsConstructor
@RequestMapping("transacoes")
public class RealizarTransacaoRestAPI {

	private final RealizarTransacaoUseCase useCase; 

	@Operation(summary = "Realizar transação")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "201", 
			description  = "Created"),
		@ApiResponse(
			responseCode = "422",
			description = "Unprocessable Content")
	})
	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Void> handle(@RequestBody RealizarTransacaoCommand command) {
		useCase.execute(command);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

}