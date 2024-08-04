package br.com.vr.autorizador.domain.cartao.service;

import org.springframework.transaction.annotation.Transactional;

import br.com.vr.autorizador.domain.cartao.CriarCartaoUseCase;
import br.com.vr.autorizador.domain.cartao.exception.CartaoDuplicadoException;
import br.com.vr.autorizador.domain.cartao.model.Cartao;
import br.com.vr.autorizador.domain.cartao.repository.CartaoRepository;
import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
public class CriarCartaoService implements CriarCartaoUseCase {

	private final CartaoRepository repository;

	@Override
	public CriarCartaoCommand execute(CriarCartaoCommand command) {
		validate(command);
		validarNumeroDuplicado(command.getNumeroCartao());
		repository.save(toDomain(command));
		return command;
	}

	private void validarNumeroDuplicado(String numeroCartao) {
		
		// Implementação seguindo desafio de `sem utilizar nenhum if`
		repository.findById(numeroCartao).ifPresent(cartao -> {
			throw new CartaoDuplicadoException(numeroCartao);
		});
		
		/*
		Para a abordagem acima acaba sendo feito o carregamento do objeto Cartao de forma desnecessária, 
		mas seguindo o desafio será o padrão aplicado nessa e demais implementações 
		if (repository.existsById(numeroCartao)) {
			throw new CartaoDuplicadoException(numeroCartao);
		}
		*/
	}

	private Cartao toDomain(CriarCartaoCommand command) {
		return Cartao.criar(
				command.getNumeroCartao(), 
				command.getSenha());
	}

}