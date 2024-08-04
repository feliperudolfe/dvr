package br.com.vr.autorizador.domain.sk.exception;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.context.i18n.LocaleContextHolder;

public class ApplicationException extends RuntimeException {

	private static final long serialVersionUID = -8801966228389990582L;

	private final List<BusinessMessage> messages;

	public ApplicationException(ResourceBundle bundle, String type, String text, Object... params) {
		var msg = builderMessage(bundle, type, text, params);
		this.messages = Arrays.asList(msg);
	}

	public ApplicationException(List<BusinessMessage> messages) {
		this.messages = messages;
	}

	public List<BusinessMessage> getMessages() {
		return messages;
	}
	
	protected static ResourceBundle getResourceBundle(String fileMessage) {
		return ResourceBundle.getBundle(fileMessage, LocaleContextHolder.getLocale());
	}

	private String getTextMessage(ResourceBundle bundle, String key, Object... params) {
		var value = bundle.getString(key);
		return MessageFormat.format(value, params);
	}

	private BusinessMessage builderMessage(ResourceBundle bundle, String type, String text, Object... params) {
		return BusinessMessage.builder().type(type).text(getTextMessage(bundle, text, params)).build();
	}

}