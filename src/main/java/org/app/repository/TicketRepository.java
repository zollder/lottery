package org.app.repository;

import java.util.List;
import java.util.Optional;

import org.app.model.Ticket;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Ticket repository interface.
 */
public interface TicketRepository {

	/**
	 * Resets application state for the next draw:
	 * - re-initializes application properties with default values
	 * - cleans and re-initializes in-memory data holders
	 */
	public void reset();

	/**
	 * Resets application state for the next draw:
	 * - re-initializes application properties with custom values
	 * - cleans and re-initializes in-memory data holders
	 *
	 * @param size - data-holder size (number of balls & tickets)
	 * @param price - lottery bank
	 */
	public void reset(int size, double bank);

	/**
	 * Creates and saves a new {@link Ticket} for specified buyer name and ticket price.
	 * Adds/saves created {@link Ticket} to in-memory data-holder.
	 *
	 * @param name - buyer name
	 * @param price - ticket price
	 * @return {@link Optional<Ticket>} ticket or Optional.empty
	 */
	public Optional<Ticket> createTicket(@NotEmpty String name, double price);

	/**
	 * Loads and returns {@link Ticket} for specified ticket ID.
	 *
	 * @param id - ticket ID
	 * @return - {@link Optional<Ticket>} ticket or Optional.empty
	 */
	public Optional<Ticket> loadTicketById(int id);

	/**
	 * Loads and returns all purchased tickets.
	 *
	 * @return - a collection of all purchased {@link Ticket}s
	 */
	public List<Ticket> loadPurchasedTickets();

	/**
	 * Returns a collection of {@link Ticket}s for specified list of IDs.
	 *
	 * @param id - a list of ticket IDs
	 * @return - {@link List<Optional<Ticket>>}
	 */
	public List<Optional<Ticket>> loadTicketsByIds(List<Integer> ids);

	/**
	 * Saves winner tickets in the in-memory data structure.
	 *
	 * @param tickets - a collection of winner tickets
	 */
	public void saveWinners(List<Ticket> tickets);

	/**
	 * Loads and returns selected winner tickets from the last draw.
	 *
	 * @return a collection of winner tickets
	 */
	public List<Ticket> getWinners();

	/**
	 * Returns the current draw size (a total number of balls in the draw)
	 *
	 * @return - draw size
	 */
	public int getSize();

	/**
	 * Returns the current lottery bank size
	 *
	 * @return bank size
	 */
	public Double getBank();
}
