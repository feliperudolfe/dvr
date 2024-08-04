package br.com.vr.autorizador.domain.cartao.model;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import br.com.vr.autorizador.domain.sk.domain.EntityDomain;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "transacoes")
@Accessors(fluent = true)
@Getter
@Setter(value = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Transacao extends EntityDomain<UUID> {

	@Id
	@Column(name = "transacao_id")
	@JdbcTypeCode(SqlTypes.VARCHAR)
	private UUID id;

	@NotNull
	private BigDecimal valor;
	
	@NotNull
	private ZonedDateTime datahora;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "numero_cartao")
	private Cartao cartao;
	
	public static Transacao criar(Cartao cartao, BigDecimal valor) {
		return new Transacao(UUID.randomUUID(), valor, ZonedDateTime.now(), cartao);
	}

}