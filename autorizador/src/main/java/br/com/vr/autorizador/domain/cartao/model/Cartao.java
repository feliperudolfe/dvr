package br.com.vr.autorizador.domain.cartao.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.util.Assert;

import br.com.vr.autorizador.domain.cartao.exception.TransacaoSaldoInsuficienteException;
import br.com.vr.autorizador.domain.cartao.exception.TransacaoSenhaInvalidaException;
import br.com.vr.autorizador.domain.sk.domain.Aggregate;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "cartoes")
@Accessors(fluent = true)
@Getter
@Setter(value = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Cartao extends Aggregate<String> {

	@Id
	@Column(name = "numero_cartao")
	private String id;

	private String senha;
	private BigDecimal limite;
	
	@OneToMany(mappedBy = "cartao",  cascade = CascadeType.ALL)
	private List<Transacao> transacoes;
	
	public static Cartao criar(String numeroCartao, String senha) {
		return new Cartao(numeroCartao, senha, BigDecimal.valueOf(500.00), new ArrayList<Transacao>());
	}

	public UUID registrarTransacao(String senhaCartao, BigDecimal valor) {

		var transacao = Transacao.criar(this, valor);
		transacoes.add(transacao);

		validarSenha(senhaCartao);
		validarSaldo();

		return transacao.id();
	}
	
	private void validarSenha(String senhaCartao) {
		Assert.state(senha.equals(senhaCartao), () -> {
			throw new TransacaoSenhaInvalidaException();
		});
	}

	private void validarSaldo() {
		
		var montante = this.transacoes.stream()
				.map(Transacao::valor)
				.reduce(BigDecimal::add)
				.orElse(BigDecimal.ZERO);
		
		Assert.state(limite.compareTo(montante) >= 0, () -> {
			throw new TransacaoSaldoInsuficienteException(id);
		});
	}
	
	public BigDecimal obterSaldo() {
		var montante = this.transacoes.stream()
				.map(Transacao::valor)
				.reduce(BigDecimal::add)
				.orElse(BigDecimal.ZERO);
		return limite.subtract(montante);
	}

}