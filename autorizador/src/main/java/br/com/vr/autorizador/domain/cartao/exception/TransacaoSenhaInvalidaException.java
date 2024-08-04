package br.com.vr.autorizador.domain.cartao.exception;

import org.springframework.http.HttpStatus;

import br.com.vr.autorizador.domain.sk.exception.ApiResponse;
import br.com.vr.autorizador.domain.sk.exception.BusinessException;

@ApiResponse(status = HttpStatus.UNPROCESSABLE_ENTITY)
public class TransacaoSenhaInvalidaException extends BusinessException { // NOSONAR

	/**
	 * 
	 */
	private static final long serialVersionUID = -1680840414459565504L;

	public TransacaoSenhaInvalidaException() {
		super(TransacaoSenhaInvalidaException.class.getSimpleName());
	}

}
