package br.com.vr.autorizador.domain.cartao.exception;

import org.springframework.http.HttpStatus;

import br.com.vr.autorizador.domain.sk.exception.ApiResponse;
import br.com.vr.autorizador.domain.sk.exception.BusinessException;

@ApiResponse(status = HttpStatus.NOT_FOUND)
public class CartaoNaoExisteException extends BusinessException { // NOSONAR

	/**
	 * 
	 */
	private static final long serialVersionUID = 4632501299814648700L;

	public CartaoNaoExisteException(String numeroCartao) {
		super(CartaoNaoExisteException.class.getSimpleName(), numeroCartao);
	}

}
