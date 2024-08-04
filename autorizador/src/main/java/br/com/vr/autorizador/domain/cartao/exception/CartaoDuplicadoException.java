package br.com.vr.autorizador.domain.cartao.exception;

import org.springframework.http.HttpStatus;

import br.com.vr.autorizador.domain.sk.exception.ApiResponse;
import br.com.vr.autorizador.domain.sk.exception.BusinessException;

@ApiResponse(status = HttpStatus.UNPROCESSABLE_ENTITY)
public class CartaoDuplicadoException extends BusinessException { // NOSONAR

	/**
	 * 
	 */
	private static final long serialVersionUID = -9036712974106674108L;

	public CartaoDuplicadoException(String numeroCartao) {
		super(CartaoDuplicadoException.class.getSimpleName(), numeroCartao);
	}

}
