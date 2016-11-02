package org.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TicketNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public TicketNotFoundException(Integer id) {
		super(String.format("Ticket doesn't exist, ID: %d", id));
	}

	public TicketNotFoundException(String message, Integer id) {
		super(String.format(message + ", ID: %d", id));
	}
}