package br.com.vr.autorizador.domain.sk.exception;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.vr.autorizador.domain.sk.domain.DomainObject;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(Include.NON_NULL)
public class BusinessMessage implements DomainObject, Serializable {

	private static final long serialVersionUID = -6124080264168142169L;
	
	public static final String WARN = "warning";
	public static final String ERROR = "error";
	public static final String INFO = "info";
	public static final String SUCCESS = "success";
	public static final String QUESTION = "question";

	private String field;
	private String type;
	private String text;

}