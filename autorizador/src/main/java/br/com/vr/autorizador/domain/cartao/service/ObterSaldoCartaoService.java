package br.com.vr.autorizador.domain.cartao.service;

import java.math.BigDecimal;

import org.springframework.transaction.annotation.Transactional;

import br.com.vr.autorizador.domain.cartao.ObterSaldoCartaoUseCase;
import br.com.vr.autorizador.domain.cartao.exception.CartaoNaoExisteException;
import br.com.vr.autorizador.domain.cartao.model.Cartao;
import br.com.vr.autorizador.domain.cartao.repository.CartaoRepository;
import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ObterSaldoCartaoService implements ObterSaldoCartaoUseCase {
	
	private final CartaoRepository repository;

	@Override
	public BigDecimal execute(ObterSaldoCartaoCommand command) {
		return repository.findById(command.getNumeroCartao())
				.map(Cartao::limite)
				.orElseThrow(() -> new CartaoNaoExisteException(command.getNumeroCartao()));
	}

}