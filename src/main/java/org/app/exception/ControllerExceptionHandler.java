package org.app.exception;

import org.app.model.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerExceptionHandler {

	@ResponseBody
	@ExceptionHandler(TicketNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Response handleTicketNotFoundException(TicketNotFoundException exception) {
		return new Response(false, exception.getMessage());
	}

	@ResponseBody
	@ExceptionHandler(TicketsSoldOutException.class)
	@ResponseStatus(HttpStatus.NOT_MODIFIED)
	public Response handleTicketsSoldOutException(TicketsSoldOutException exception) {
		return new Response(false, exception.getMessage());
	}

	@ResponseBody
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Response handleIllegalArgumentException(IllegalArgumentException exception) {
		return new Response(false, exception.getMessage());
	}
}