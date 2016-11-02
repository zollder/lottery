package org.app.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.app.TestBase;
import org.app.model.Ticket;
import org.app.repository.impl.TicketRepositoryImpl;
import org.app.service.impl.TicketServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

/**
 * TicketService unit test.
 */
public class TicketServiceTest extends TestBase {

	@Mock
	private TicketRepositoryImpl ticketRepository;

	@InjectMocks
	private TicketServiceImpl ticketService;

	private Optional<Ticket> ticket;

	@Before
	public void setup() {
		ticket = Optional.ofNullable(new Ticket(1, "name"));
	}

	@Test
	public void testPurchase() {
		when(ticketRepository.createTicket("name", 0.0)).thenReturn(ticket);
		Optional<Ticket> result = ticketService.purchase("name");

		assertThat(result).isEqualTo(ticket);
		assertThat(result.get().getPrice()).isEqualTo(0.0);
	}

	@Test
	public void testGetById() {
		when(ticketRepository.loadTicketById(1)).thenReturn(ticket);

		Optional<Ticket> result = ticketService.getById(1);

		assertThat(result).isEqualTo(ticket);
		assertThat(result.get().getPrice()).isEqualTo(0.0);
	}
}