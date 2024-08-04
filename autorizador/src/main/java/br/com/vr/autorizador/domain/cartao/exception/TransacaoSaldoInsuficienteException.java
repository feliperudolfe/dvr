package br.com.vr.autorizador.domain.cartao.exception;

import org.springframework.http.HttpStatus;

import br.com.vr.autorizador.domain.sk.exception.ApiResponse;
import br.com.vr.autorizador.domain.sk.exception.BusinessException;

@ApiResponse(status = HttpStatus.UNPROCESSABLE_ENTITY)
public class TransacaoSaldoInsuficienteException extends BusinessException { // NOSONAR

	/**
	 * 
	 */
	private static final long serialVersionUID = -6774222622452102315L;

	public TransacaoSaldoInsuficienteException(String numeroCartao) {
		super(TransacaoSaldoInsuficienteException.class.getSimpleName(), numeroCartao);
	}

}
