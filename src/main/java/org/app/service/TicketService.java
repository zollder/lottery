package org.app.service;

import java.util.List;
import java.util.Optional;

import org.app.model.Ticket;

/**
 * Ticket service interface definition.
 */
public interface TicketService {

	/**
	 * Reserves a random ticket from the pool of tickets under specified buyer name.
	 * Returns an optional of purchased {@link Ticket} or empty.
	 *
	 * @param name - buyer name
	 * @return - purchased ticket
	 */
	public Optional<Ticket> purchase(String name);

	/**
	 * Returns an optional of purchased ticket by specified ticket ID,
	 * or empty, if the ticket doesn't exist.
	 *
	 * @param id - ticket ID
	 * @return Optional of {@link Ticket}
	 */
	public Optional<Ticket> getById(int id);

	/**
	 * Retrieves and returns all tickets purchased since the last draw.
	 *
	 * @return - a collection of all purchased tickets
	 */
	public List<Ticket> getPurchasedTickets();
}
