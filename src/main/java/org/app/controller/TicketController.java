package org.app.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.app.exception.TicketNotFoundException;
import org.app.exception.TicketsSoldOutException;
import org.app.model.Ticket;
import org.app.model.TicketDto;
import org.app.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tickets")
public class TicketController {

	@Autowired
	private TicketService ticketService;

	/**
	 * Creates and saves a new {@link Ticket} for specified buyer name.
	 *
	 * @param ticket - {@link TicketDto} containing buyer name
	 * @return purchased {@link Ticket}
	 * @throws {@link TicketsSoldOutException} if all tickets were sold out
	 */
	@RequestMapping(method = RequestMethod.POST)
	public TicketDto purchaseTicket(@RequestBody TicketDto ticket) {
		if (ticket == null || StringUtils.isBlank(ticket.getFirstName())) {
			throw new IllegalArgumentException("Name cannot be empty");
		}
		return ticketService.purchase(ticket.getFirstName())
			.map(t -> new TicketDto(t.getId(), t.getName()))
			.orElseThrow(() -> new TicketsSoldOutException());
	}

	/**
	 * Loads and returns a {@link Ticket} by specified ticket ID.
	 *
	 * @param ticketId - ticket ID
	 * @return {@link Ticket} object
	 * @throws {@link TicketNotFoundException} if the ticket is not found
	 */
	@RequestMapping(value = "/{ticketId}", method = RequestMethod.GET)
	public Ticket getById(@PathVariable Integer ticketId) {
		if (ticketId == null) {
			throw new IllegalArgumentException("Missing ticket ID");
		}
		return ticketService.getById(ticketId)
			.orElseThrow(() -> new TicketNotFoundException(ticketId));
	}

	/**
	 * Loads all purchased tickets.
	 *
	 * @return {@link Ticket} object
	 * @throws {@link TicketNotFoundException} if the ticket is not found
	 */
	@RequestMapping(method = RequestMethod.GET)
	public List<TicketDto> getAll() {
		return ticketService.getPurchasedTickets()
			.stream()
			.map(t -> new TicketDto(t.getId(), t.getName()))
			.collect(Collectors.toList());
	}
}