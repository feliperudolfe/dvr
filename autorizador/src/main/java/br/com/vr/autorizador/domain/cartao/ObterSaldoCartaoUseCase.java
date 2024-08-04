package br.com.vr.autorizador.domain.cartao;

import java.math.BigDecimal;

import br.com.vr.autorizador.domain.cartao.ObterSaldoCartaoUseCase.ObterSaldoCartaoCommand;
import br.com.vr.autorizador.domain.sk.usecase.Command;
import br.com.vr.autorizador.domain.sk.usecase.UseCase;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;

public interface ObterSaldoCartaoUseCase extends UseCase<BigDecimal, ObterSaldoCartaoCommand> {

	@Getter
	@Builder
	class ObterSaldoCartaoCommand implements Command {

		@Pattern(regexp = "^\\d{16}$", message = "{cartao.numeroCartao.Pattern}")
		@NotBlank(message = "{cartao.numeroCartao.NotBlank}")
		private String numeroCartao;
		
		public static ObterSaldoCartaoCommand withNumeroCartao(String numeroCartao) {
			return ObterSaldoCartaoCommand.builder().numeroCartao(numeroCartao).build();
		}

	}

}