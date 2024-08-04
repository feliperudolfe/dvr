package br.com.vr.autorizador.domain.sk.exception;

import java.util.List;

public class BusinessException extends ApplicationException {

	private static final long serialVersionUID = -299958424728518122L;

	public static final String PROP_FILE_MESSAGE = "locale/business";

	public BusinessException(List<BusinessMessage> messages) {
		super(messages);
	}

	public BusinessException(String text, Object... params) {
		super(getResourceBundle(PROP_FILE_MESSAGE), BusinessMessage.WARN, text, params);
	}

}