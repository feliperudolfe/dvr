package br.com.vr.autorizador.application.adapter.in.rest.cartao;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.vr.autorizador.domain.cartao.CriarCartaoUseCase;
import br.com.vr.autorizador.domain.cartao.CriarCartaoUseCase.CriarCartaoCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Cartão")

@RestController
@RequiredArgsConstructor
@RequestMapping("cartoes")
public class CriarCartaoRestAPI {

	private final CriarCartaoUseCase useCase; 

	@Operation(summary = "Criar novo cartão")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "201", 
			description  = "Created"),
		@ApiResponse(
			responseCode = "422",
			description = "Unprocessable Content")
	})
	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<CriarCartaoCommand> handle(@RequestBody CriarCartaoCommand command) {
		var resposta = useCase.execute(command);
		return new ResponseEntity<>(resposta, HttpStatus.CREATED);
	}

}