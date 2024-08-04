package br.com.vr.autorizador.domain.cartao;

import java.math.BigDecimal;
import java.util.UUID;

import br.com.vr.autorizador.domain.cartao.RealizarTransacaoUseCase.RealizarTransacaoCommand;
import br.com.vr.autorizador.domain.sk.usecase.Command;
import br.com.vr.autorizador.domain.sk.usecase.UseCase;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;

public interface RealizarTransacaoUseCase extends UseCase<UUID, RealizarTransacaoCommand> {
	
	@Getter
	@Builder
	class RealizarTransacaoCommand implements Command {

		@Pattern(regexp = "^\\d{16}$", message = "{transacao.numeroCartao.Pattern}")
		@NotBlank(message = "{transacao.numeroCartao.NotBlank}")
		private String numeroCartao;

		@Pattern(regexp = "^\\d{4}$", message = "{transacao.senhaCartao.Pattern}")
		@NotBlank(message = "{transacao.senhaCartao.NotBlank}")
		private String senhaCartao;
		
		@Digits(integer = 18, fraction = 2, message = "{transacao.valor.Digits}")
		@NotNull(message = "{transacao.valor.NotNull}")
		private BigDecimal valor;

	}

}
