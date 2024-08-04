package br.com.vr.autorizador.domain.cartao.model;

import java.math.BigDecimal;

import br.com.vr.autorizador.domain.sk.domain.Aggregate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
	
	public static Cartao criar(String numeroCartao, String senha) {
		return new Cartao(numeroCartao, senha, BigDecimal.valueOf(500.00));
	}

}