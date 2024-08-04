package br.com.vr.autorizador.domain.sk.usecase;

import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;
import org.springframework.context.i18n.LocaleContextHolder;

import br.com.vr.autorizador.domain.sk.domain.DomainObject;
import br.com.vr.autorizador.domain.sk.exception.BusinessException;
import br.com.vr.autorizador.domain.sk.exception.BusinessMessage;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

public interface UseCase<S, E> extends DomainObject {
	
	S execute(E command);

	default void validate(E command) {
		
		var messges = getValidator().validate(command)
				.stream()
				.map(this::getMessage)
				.toList();
		
		if (!messges.isEmpty()) {
			throw new BusinessException(messges);
		}
	}
	
	private BusinessMessage getMessage(ConstraintViolation<E> violation) {
		return BusinessMessage.builder()
				.type(BusinessMessage.WARN)
				.field(getPropertyPath(violation))
				.text(getPropertyText(violation))
				.build();
	}

	private String getPropertyPath(ConstraintViolation<E> violation) {
		String field = null;
		for (var node : violation.getPropertyPath()) {
			field = node.getName();
		}
		return field;
	}
	
	private String getPropertyText(ConstraintViolation<E> violation) {
		return StringUtils.capitalize(violation.getMessage().replaceAll("[{}]", ""));
	}
	
	private Validator getValidator() {
		return Validation
				.byDefaultProvider()
				.configure()
				.messageInterpolator(new ContextualMessageInterpolator())
				.buildValidatorFactory()
				.getValidator();
	}
	
	public class ContextualMessageInterpolator extends ResourceBundleMessageInterpolator {

		private ContextualMessageInterpolator() {
			super(new PlatformResourceBundleLocator("locale/validation"));
		}

		@Override
		public String interpolate(String template, Context context) {
			return super.interpolate(template, context, LocaleContextHolder.getLocale());
		}

		@Override
		public String interpolate(Context context, Locale locale, String term) {
			return super.interpolate(context, LocaleContextHolder.getLocale(), term);
		}
	}
	
}