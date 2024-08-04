package br.com.vr.autorizador.application.config.handler;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.vr.autorizador.domain.sk.exception.ApiResponse;
import br.com.vr.autorizador.domain.sk.exception.ApplicationException;
import br.com.vr.autorizador.domain.sk.exception.BusinessException;
import br.com.vr.autorizador.domain.sk.exception.BusinessMessage;
import br.com.vr.autorizador.domain.sk.exception.Response;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.Generated;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Generated
@RestControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ Exception.class, ApplicationException.class })
	ResponseEntity<Object> handleException(Exception ex) {
		log.error(ex.getMessage(), ex);
		return createResponse(HttpStatus.INTERNAL_SERVER_ERROR, BusinessMessage.ERROR, "An internal error occurred");
	}
	
	@ExceptionHandler({ BusinessException.class })
	ResponseEntity<Object> handleException(BusinessException ex) {
		return createResponse(getStatus(ex), ex.getMessages());
	}
	
	private HttpStatus getStatus(BusinessException ex) {
		return Optional
				.ofNullable(ex.getClass().getAnnotation(ApiResponse.class))
				.map(ApiResponse::status)
				.orElse(HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler({ ConstraintViolationException.class })
	ResponseEntity<Object> handleException(ConstraintViolationException ex) {
		var messages = getMessages(ex.getConstraintViolations());
		return createResponse(HttpStatus.BAD_REQUEST, messages);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(
			MissingServletRequestParameterException ex,
			HttpHeaders headers, 
			HttpStatusCode status, 
			WebRequest request) {
		
		var field =	getFieldFormatter(ex.getParameterName());
		return createResponse(status, field, BusinessMessage.WARN, ex.getMessage());
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex,
			HttpHeaders headers,
			HttpStatusCode status,
			WebRequest request) {

		var messages = getMessages(ex.getFieldErrors());
		return createResponse(status, messages);
	}

	@Override
	protected ResponseEntity<Object> handleServletRequestBindingException(
			ServletRequestBindingException ex,
			HttpHeaders headers,
			HttpStatusCode status,
			WebRequest request) {

		return createResponse(status, BusinessMessage.WARN, ex.getMessage());
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(
			HttpMessageNotReadableException ex,
			HttpHeaders headers, 
			HttpStatusCode status, 
			WebRequest request) {

		return createResponse(status, BusinessMessage.WARN, ex.getMessage());
	}
	
	private List<BusinessMessage> getMessages(Set<ConstraintViolation<?>> erros) {
		return erros
				.stream()
				.map(item -> BusinessMessage.builder()
						.type(BusinessMessage.WARN)
						.text(StringUtils.capitalize(item.getMessage()))
						.build())
				.sorted(Comparator.comparing(BusinessMessage::getField))
				.toList();
	}

	private List<BusinessMessage> getMessages(List<FieldError> erros) {
		return erros
				.stream()
				.map(item -> BusinessMessage.builder()
						.field(getFieldFormatter(item.getField()))
						.type(BusinessMessage.WARN)
						.text(StringUtils.capitalize(item.getDefaultMessage()))
						.build())
				.sorted(Comparator.comparing(BusinessMessage::getField))
				.toList();
	}

	private ResponseEntity<Object> createResponse(HttpStatusCode status, String type, String text) {
		return createResponse(status, null, type, text);
	}

	private ResponseEntity<Object> createResponse(HttpStatusCode status, String field, String type, String text) {
		return createResponse(status, Arrays.asList(BusinessMessage.builder()
				.field(field)
				.type(type)
				.text(text)
				.build()));
	}

	private ResponseEntity<Object> createResponse(HttpStatusCode status, List<BusinessMessage> messages) {
		return ResponseEntity
				.status(status)
				.contentType(MediaType.APPLICATION_JSON)
				.body(Response
						.builder()
						.messages(messages)
						.build());
	}

	private String getFieldFormatter(String field) {
		return Arrays
				.asList(StringUtils.capitalize(field).split("(?=[A-Z])"))
				.stream()
				.collect(Collectors.joining(" "));
	}

}
