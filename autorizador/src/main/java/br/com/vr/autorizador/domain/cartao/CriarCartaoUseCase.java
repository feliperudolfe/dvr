package br.com.vr.autorizador.domain.cartao;

import br.com.vr.autorizador.domain.cartao.CriarCartaoUseCase.CriarCartaoCommand;
import br.com.vr.autorizador.domain.sk.usecase.Command;
import br.com.vr.autorizador.domain.sk.usecase.UseCase;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;

public interface CriarCartaoUseCase extends UseCase<CriarCartaoCommand, CriarCartaoCommand> {

	@Getter
	@Builder
	class CriarCartaoCommand implements Command {

		@Pattern(regexp = "^\\d{16}$", message = "{cartao.numeroCartao.Pattern}")
		@NotBlank(message = "{cartao.numeroCartao.NotBlank}")
		private String numeroCartao;

		@Pattern(regexp = "^\\d{4}$", message = "{cartao.senha.Pattern}")
		@NotBlank(message = "{cartao.senha.NotBlank}")
		private String senha;

	}

}