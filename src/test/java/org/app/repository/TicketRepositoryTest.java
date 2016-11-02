package org.app.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.app.TestBase;
import org.app.model.Ticket;
import org.app.repository.impl.TicketRepositoryImpl;
import org.app.utils.CollectionUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

/**
 * TicketRepository unit test.
 */
public class TicketRepositoryTest extends TestBase {

	@Mock
	private CollectionUtils collectionUtils;

	@InjectMocks
	private TicketRepositoryImpl ticketRepository;

	private int size;
	private double bank;

	private List<Integer> shuffledIds;
	private List<Integer> selectedIds;
	private List<String> names;

	@Before
	public void setup() {
		size = 5;
		bank = 200.0;
		shuffledIds = Arrays.asList(2, 1, 3, 7, 4);
		selectedIds = shuffledIds.subList(0, 3);
		names = Arrays.asList("name1", "name2", "name3");

		when(collectionUtils.generateShuffledIds(size)).thenReturn(shuffledIds);
	}

	@Test
	public void testParametricReset() {
		ticketRepository.reset(size, bank);

		assertThat(ticketRepository.getSize()).isEqualTo(size);
		assertThat(ticketRepository.getBank()).isEqualTo(bank);
		verify(collectionUtils, times(1)).generateShuffledIds(size);
	}

	@Test
	public void testCreateTicket_emptyIds() {
		assertThat(ticketRepository.createTicket("name", 10.0).isPresent()).isFalse();
	}

	@Test
	public void testCreateTicket_nonEmptyIds() {
		ticketRepository.reset(size, bank);
		List<Optional<Ticket>> tickets = createTickets(names);

		verify(collectionUtils, times(1)).generateShuffledIds(size);
		assertThat(tickets.size()).isEqualTo(names.size());
		assertThat(filterIds(tickets)).containsAll(selectedIds);
	}

	@Test
	public void testGetTicketById() {
		ticketRepository.reset(size, bank);
		names.forEach(name -> ticketRepository.createTicket(name, 10.0));

		// existing ticket
		Ticket ticket = ticketRepository.loadTicketById(shuffledIds.get(0)).get();
		assertThat(ticket.getId()).isEqualTo(shuffledIds.get(0));
		assertThat(ticket.getName()).isEqualTo(names.get(0));

		// non-existing ticket
		Optional<Ticket> result = ticketRepository.loadTicketById(shuffledIds.get(4));
		assertThat(result.isPresent()).isFalse();
	}

	@Test
	public void testGetTicketsByIds() {
		ticketRepository.reset(size, bank);
		names.forEach(name -> ticketRepository.createTicket(name, 10.0));

		List<Optional<Ticket>> result = ticketRepository.loadTicketsByIds(shuffledIds);
		assertThat(result.size()).isEqualTo(shuffledIds.size());
		assertThat(filterIds(result)).containsAll(selectedIds);
	}

	@Test
	public void testSaveGetWinners() {
		ticketRepository.reset(size, bank);
		List<Ticket> tickets = createTickets(names).stream()
			.map(Optional::get)
			.collect(Collectors.toList());
		ticketRepository.saveWinners(tickets);

		assertThat(ticketRepository.getWinners()).isEqualTo(tickets);
	}

	/*
	 * Creates a collection of Optional<Ticket> for specified buyer names.
	 */
	private List<Optional<Ticket>> createTickets(List<String> names) {
		return names.stream()
			.map(name -> ticketRepository.createTicket(name, 10.0))
			.collect(Collectors.toList());
	}

	/*
	 * Retrieves a collection of ticket IDs from specified collection of Optional<Ticket>.
	 */
	private List<Integer> filterIds(List<Optional<Ticket>> tickets) {
		return tickets.stream()
			.filter(Optional::isPresent)
			.map(Optional::get)
			.map(Ticket::getId)
			.collect(Collectors.toList());
	}
}
