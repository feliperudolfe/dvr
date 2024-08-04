package br.com.vr.autorizador.domain.cartao.service;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import br.com.vr.autorizador.domain.cartao.RealizarTransacaoUseCase;
import br.com.vr.autorizador.domain.cartao.exception.CartaoNaoExisteException;
import br.com.vr.autorizador.domain.cartao.repository.CartaoRepository;
import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
public class RealizarTransacaoService implements RealizarTransacaoUseCase {

	private final CartaoRepository repository;

	@Override
	public UUID execute(RealizarTransacaoCommand command) {
		
		validate(command);
		
		var cartao = repository.findById(command.getNumeroCartao())
				.orElseThrow(() -> new CartaoNaoExisteException(command.getNumeroCartao()));
		
		var id = cartao.registrarTransacao(command.getSenhaCartao(), command.getValor());
		repository.save(cartao);
		
		return id;
	}

}