package br.com.vr.autorizador.domain.sk.exception;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.vr.autorizador.domain.sk.domain.DomainObject;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(Include.NON_NULL)
public class Response<T extends Serializable> implements DomainObject, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8299371610428830836L;

	private T data;
	private List<BusinessMessage> messages;

}