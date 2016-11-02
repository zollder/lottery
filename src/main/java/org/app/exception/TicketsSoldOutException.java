package org.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_MODIFIED)
public class TicketsSoldOutException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public TicketsSoldOutException() {
		super("Cannot create ticket. All tickets are sold.");
	}

	public TicketsSoldOutException(String message) {
		super(message);
	}
}